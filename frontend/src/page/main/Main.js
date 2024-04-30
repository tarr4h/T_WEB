import '../../css/Comn.css';
import Nav from "./Nav";
import View from "./View";
import {useEffect, useState} from "react";


function Main(){

    const [pg, setPg] = useState('map');

    useEffect(() => {
        console.log('page : ', pg);
    }, [pg]);

    return (
        <div>
            <Nav setPage={setPg}/>
            <View page={pg}/>
        </div>
    )
}


export default Main;