import {imgGend, isMobile} from "../../comn/comnFunction";
import {useState} from "react";
import AppViewGuide from "../guide/AppViewGuide";
import {useModal} from "../modal/ModalContext";
import instance from "../../comn/AxiosInterceptor";


function Hamburger(){

    const [showMenu, setShowMenu] = useState(false);
    const {openModalCenter, openSmallModalCenter} = useModal();

    const openGuideModal = () => {
        openModalCenter(<AppViewGuide/>, '편리하게 사용하는 법');
    }

    const toggleMenu = () => {
        setShowMenu(!showMenu);
    }

    const loginTest = async () => {
        const userTest = {
            id : 'test1',
            password : 'pppppzzzz222',
            name : 'harry'
        }

        const ret = (await instance.post('/login/login', userTest)).data;
        if(ret.errorType){
            openSmallModalCenter(ret.msg, '에러');
        }
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
                    <div onClick={loginTest}>로그인하실래요?</div>
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