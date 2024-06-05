import '../../css/Comn.css';
import Nav from "./Nav";
import View from "./View";
import {useEffect, useState} from "react";
import instance from "../../comn/AxiosInterceptor";

function Main(){

    const [pg, setPg] = useState('map');

    useEffect(() => {
        console.log('first on');
        void visitLog();
    }, []);

    const visitLog = async() => {
        await instance.post('/comn/visitLog');
    }

    return (
        <div>
            <Nav setPage={setPg}/>
            <View page={pg}/>
        </div>
    )
}


export default Main;