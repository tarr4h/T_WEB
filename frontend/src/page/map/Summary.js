import '../../css/Search.css';

function Summary({data}){

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

    return (
        <div className={'summary'}>
            <div className={'summaryHeader'}>
                <div>{data.name}</div>
                <div className={typeColor(data.mcid)}
                >{data.mcidName}</div>
            </div>
            <div className={'summaryFooter'}>
                <div>{data.address}</div>
                <div>{getDistanceMark(data.centerDistance)}</div>
            </div>
        </div>
    )
}

export default Summary;