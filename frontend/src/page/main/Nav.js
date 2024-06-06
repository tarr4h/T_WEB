import logoImg from '../../images/adp_logo.png';

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
                <div onClick={(page) => {movePage('map')}}>
                    MAP
                </div>
                {/*<div onClick={(page) => {movePage('setting')}}>*/}
                {/*    SETTING*/}
                {/*</div>*/}
            </div>
        </div>
    )
}


export default Nav;