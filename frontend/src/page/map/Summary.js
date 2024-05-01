import '../../css/Search.css';
import {useEffect, useState} from "react";
import axios from "axios";

function Summary({data}){

    const [driving, setDriving] = useState(null);

    useEffect(() => {

    }, [data]);

    const typeColor = (type) => {
        let className = '';
        switch (type){
            case 'DINING' :
                className = 'diningColor';
                break;
            case 'BAR' :
                className = 'barColor';
                break;
            case 'ACCOMMODATION':
                className = 'accommodationColor';
                break;
        }
        return className;
    }

    const getDistanceMark = (num) => {
        let distance = Math.round((Number(num) * 1000));
        let mark = 'm';
        if(distance > 999){
            distance = Math.round(distance / 100)/10;
            mark = 'km';
        }
        return distance + mark;
    }

    const openIfw = async () => {
        const ifw = data.ifw,
              map = data.map,
              marker = data.marker;
        ifw.open(map, marker);
        setTimeout(() => {
            ifw.close();
        }, 5000);

        const center = map.getCenter();
        const param = {
            lat : data.py,
            lng : data.px,
            centerLat : center.y,
            centerLng : center.x
        }
        const driving = await (await axios.get('/comn/getDriving', {params : param})).data;
        console.log('driving : ', driving);
        if(driving){
            setDriving(driving);
        }
    }

    return (
        <div className={'summary'}
             onClick={openIfw}
        >
            <div className={'summaryHeader'}>
                <div>{data.name}</div>
                <div className={typeColor(data.mcid)}
                >{data.mcidName}</div>
            </div>
            <div className={'summaryFooter'}>
                <div>{data.address}</div>
                <div>
                    <div>{getDistanceMark(data.centerDistance)}</div>
                    <div className={'durationMin'}>{driving != null ? driving.durationMin + '분' : ''}</div>
                </div>
            </div>
        </div>
    )
}

export default Summary;