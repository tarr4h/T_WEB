import {imgGend, isMobile} from "../../comn/comnFunction";
import {useState} from "react";
import Modal from "../modal/Modal";
import AppViewGuide from "../guide/AppViewGuide";


function Hamburger(){

    const [showMenu, setShowMenu] = useState(false);

    const [guideModalOpened, setGuideModalOpened] = useState(false);

    const openGuideModal = () => {
        setGuideModalOpened(true);
    }

    const toggleMenu = () => {
        setShowMenu(!showMenu);
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
                    {/*<div>로그인하실래요?</div>*/}
                    {/*<div>공지사항</div>*/}
                    {
                        isMobile() ? (
                            <div>
                                <Modal title={'편리하게 사용하는법'}
                                       content={<AppViewGuide/>}
                                       isOpen={guideModalOpened}
                                       setIsOpen={setGuideModalOpened}
                                />
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