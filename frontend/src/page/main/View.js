import Map from '../map/Map';
import Setting from "../setting/Setting";

function View({page}){

    switch (page){
        case 'map' : return <Map/>;
        case 'setting' : return <Setting/>;
    }
}

export default View;