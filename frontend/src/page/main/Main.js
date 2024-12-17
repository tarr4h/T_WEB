import '../../css/Comn.css';
import Nav from "./Nav";
import View from "./View";
import {useEffect, useState} from "react";
import instance from "../../comn/AxiosInterceptor";

function Main(){

    const [pg, setPg] = useState('map');
    // const [mainModalComponent, setMainModalComponent] = useState(null);
    // const [mainModalOpened, setMainModalOpened] = useState(false);

    useEffect(() => {
        void visitLog();
    }, []);

    const visitLog = async() => {
        await instance.post('/comn/visitLog');
    }

    return (
        <div>
            <div id="blockUI">
                <div>잠시만 기다려주세요.</div>
            </div>
            {/*<Modal title={'알림'}*/}
            {/*       content={mainModalComponent}*/}
            {/*       isOpen={mainModalOpened}*/}
            {/*       setIsOpen={setMainModalOpened}*/}
            {/*/>*/}
            <Nav setPage={setPg}/>
            <View page={pg}/>
        </div>
    )
}


export default Main;