import logoImg from '../../images/adp_logo.png';
import Hamburger from "../hamburger/Hamburger";
import instance from "../../comn/AxiosInterceptor";

function Nav({setPage}){

    const movePage = async (page) => {
        await selectUser();
        setPage(page);
    }

    const selectUser = async () => {
        const user = (await instance.post('/user/selectUser')).data;
        console.log('user : ', user);
    }

    return (
        <div className={'nav'}>
            <div>
                <img src={logoImg} alt="adp"/>
            </div>
            <div>ADP</div>
            <div>
                <div onClick={(page) => {movePage('map')}}
                >
                    MAP
                </div>
                <div onClick={(page) => {movePage('setting')}}
                     // className={'dpn'}
                >
                    SETTING
                </div>
            </div>
            <Hamburger/>
        </div>
    )
}


export default Nav;