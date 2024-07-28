import logoImg from '../../images/adp_logo.png';
import {imgGend, isMobile} from "../../comn/comnFunction";
import Modal from "../modal/Modal";
import {useState} from "react";
import AppViewGuide from "../guide/AppViewGuide";

function Nav({setPage}){

    const [guideModalOpened, setGuideModalOpened] = useState(false);

    const openGuideModal = () => {
        setGuideModalOpened(true);
    }

    const movePage = (page) => {
        setPage(page);
    }

    return (
        <div className={'nav'}>
            <div>
                <img src={logoImg} alt="adp"/>
            </div>
            <div>ADP</div>
            <div>
                <div onClick={(page) => {
                    movePage('map')
                }}>
                    MAP
                </div>
                {/*<div onClick={(page) => {movePage('setting')}}>*/}
                {/*    SETTING*/}
                {/*</div>*/}
            </div>
            {
                isMobile() ? (
                    <div style={{position:'absolute', right:'0'}}>
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
    )
}


export default Nav;