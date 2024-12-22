import logoImg from '../../images/adp_logo.png';
import Hamburger from "../hamburger/Hamburger";
import instance from "../../comn/AxiosInterceptor";
import {useEffect, useState} from "react";

function Nav({setPage}){

    const [menuList, setMenuList] = useState([]);

    useEffect(() => {
        const makeMenu = async () => {
            const list = (await instance.get('/menu/selectMenuList')).data;
            setMenuList(list);
        }

        makeMenu();
    }, []);

    const movePage = async (page) => {
        setPage(page);
    }

    return (
        <div className={'nav'}>
            <div>
                <img src={logoImg} alt="adp"/>
            </div>
            <div>ADP</div>
            <div>
                {
                    menuList.map(menu => (
                        <div onClick={(page) => {movePage(menu.menuNm.toLowerCase())}}
                             key={menu.id}
                        >
                            {menu.menuNm}
                        </div>
                    ))
                }
            </div>
            <Hamburger/>
        </div>
    )
}


export default Nav;