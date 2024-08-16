import logoImg from '../../images/adp_logo.png';
import {imgGend, isMobile} from "../../comn/comnFunction";
import Modal from "../modal/Modal";
import {useState} from "react";
import AppViewGuide from "../guide/AppViewGuide";
import Hamburger from "./Hamburger";

function Nav({setPage}){

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
                <div onClick={(page) => {movePage('setting')}}
                     className={'dpn'}
                >
                    SETTING
                </div>
            </div>
            <Hamburger/>
        </div>
    )
}


export default Nav;