import {imgGend, isMobile} from "../../comn/comnFunction";
import {useEffect, useState} from "react";
import AppViewGuide from "../guide/AppViewGuide";
import {useModal} from "../modal/ModalContext";
import instance from "../../comn/AxiosInterceptor";


function Hamburger(){

    const [showMenu, setShowMenu] = useState(false);
    const {openModalCenter, openSmallModalCenter} = useModal();

    const [isLogin, setIsLogin] = useState(false);

    useEffect(() => {
        checkLogin();
    }, []);

    const openGuideModal = () => {
        openModalCenter(<AppViewGuide/>, '편리하게 사용하는 법');
    }

    const toggleMenu = () => {
        setShowMenu(!showMenu);
    }

    const checkLogin = async() => {
        const login = (await instance.get('/user/checkLogin')).data;
        setIsLogin(login);
    }

    const loginTest = async () => {
        const userTest = {
            id : 'test1',
            password : 'pppppzzzz222',
            name : 'harry'
        }

        const ret = (await instance.post('/user/login', userTest)).data;
        if(ret.errorType){
            openSmallModalCenter(ret.msg, '에러');
        } else {
            window.location.reload();
        }
    }

    const logoutTest = async() => {
        const ret = (await instance.post('/user/logout')).data;
        console.log('logout ret : ', ret);
        if(ret.errorType){
            openSmallModalCenter(ret.msg, '에러');
        } else {
            window.location.reload();
        }
    }

    const jwtTest = async () => {
        const user = {
            id : 'test1'
        }

        const ret = (await instance.get('/user/jwtTest', {
        }));

        // const ret = (await instance.post('/admin/test', {})).data;

        console.log(ret);
    }

    return (
        <div>
            <div className={'hamburger'}
                 onClick={toggleMenu}
            >
                <img src={imgGend('', 'navigation-button.png')} alt=""/>
            </div>
            <div className={`menu ${showMenu ? '' : 'dpn'}`}
                 onClick={toggleMenu}>
                <div className={`menuList`} onClick={e => e.stopPropagation()}>
                    <div onClick={toggleMenu}>닫기</div>
                    {
                        isLogin ? (
                            <div onClick={logoutTest}>로그아웃</div>
                        ) : (
                            <div onClick={loginTest}>로그인하실래요?</div>
                        )
                    }
                    <div onClick={jwtTest}>login test</div>
                    {/*<div>공지사항</div>*/}
                    {
                        isMobile() ? (
                            <div>
                                <div onClick={openGuideModal}>
                                    <img src={imgGend('', 'guide/question-mark.png')} alt=""/>
                                </div>
                            </div>
                        ) : null
                    }
                </div>
            </div>
        </div>
    )
}

export default Hamburger;