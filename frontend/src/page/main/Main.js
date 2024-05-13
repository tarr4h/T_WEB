import '../../css/Comn.css';
import Nav from "./Nav";
import View from "./View";
import {useState} from "react";

function Main(){

    const [pg, setPg] = useState('map');

    return (
        <div>
            <Nav setPage={setPg}/>
            <View page={pg}/>
        </div>
    )
}


export default Main;