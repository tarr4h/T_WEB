import '../../css/Search.css';
import * as comn from '../../comn/comnFunction';
import {useEffect, useState} from "react";
import axios from "axios";
import instance from "../../comn/AxiosInterceptor";

function Summary({data}){

    const [driving, setDriving] = useState(null);

    const [showDetail, setShowDetail] = useState(false);
    const [detail, setDetail] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        setShowDetail(false);
        setDetail(null);
        setSearchQuery('');
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
        const driving = await (await instance.get('/comn/getDriving', {params : param})).data;
        if(driving){
            setDriving(driving);
        }
        console.log('driving : ', driving);

        let cont = data.addr1 + ' ' + data.addr2 + ' ' + data.addr3 + ' ' + data.name;
        let placeInfo = await searchPlace(cont, center.y, center.x);


        let avpi = false;
        if(placeInfo){
            avpi = true;
        } else {
            cont = data.addr1 + ' ' + data.addr2 + ' ' + data.name;
            placeInfo = await searchPlace(cont, center.y, center.x);
            if(placeInfo){
                avpi = true;
            }
        }

        console.log('data : ', data);
        console.log('cont : ', cont);
        console.log('placeInfo : ', placeInfo);
        if(avpi){
            setShowDetail(true);
            setDetail(placeInfo.category);
            setSearchQuery(cont);
        }
    }

    const searchPlace = async (searchTxt, lat, lng) => {
        return await(await instance.get('/comn/getNvSearch', {params : {searchTxt, lat, lng}})).data;
    }

    const openDetail = () => {
        const query = encodeURIComponent(searchQuery);
        if(comn.isMobile()){
            window.location.href = `nmap://search?query=${query}`;
            // window.open(`nmap://search?query=${query}`, '_blank');
        } else {
            window.open(`https://map.naver.com/v5/search/${query}?c=14126698.6929185,4512085.1378358,15,0,0,0,dh`, '_blank');
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
            {
                showDetail ? (
                    <div className={'summaryDetail'}>
                        <div>{detail}</div>
                        <div onClick={() => {openDetail()}}>상세보기</div>
                    </div>
                ) : null
            }
        </div>
    )
}

export default Summary;