

function AppViewGuide() {

    const getGuideImg = (filename) => {
        return require(`../../images/guide/${filename}.jpg`);
    }

    const openEachBrowser = (browser) => {
        const div = document.getElementsByClassName(browser);
        if(div[0].style.display === 'none'){
            div[0].style.display = 'block';
        } else {
            div[0].style.display = 'none';
        }
    }

    return (
        <div className={'guide'}>
            <div style={{fontStyle:'italic', marginTop:'2vh', textAlign:'center', fontWeight:'500'}}>바탕화면에 추가해서 앱처럼 사용하세요</div>
            <div style={{fontStyle:'italic', textAlign:'center'}}>p.s 가끔 앱을 완전종료해주세요(삭제말고)
                <br/>완전종료 후 다시 들어오면 업데이트가 반영돼요
                <br/>완전종료 : 멀티태스킹 바에서 삭제
            </div>
            <br/>
            <div className={'browser_selector'}
                 onClick={() => openEachBrowser('safari')}
            >사파리를 사용중인 경우 (click)</div>
            <div className={'user_browser safari'}>
                <div>1. 하단 공유버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_1_safari')} alt=""/>
                <div>2. 홈 화면에 추가를 선택해주세요</div>
                <img src={getGuideImg('app_view_guide_2')} alt=""/>
                <div>3. 추가 버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_3')} alt=""/>
                <div>4. 앱처럼 사용할 수 있어요</div>
                <img src={getGuideImg('app_view_guide_4')} alt=""/>
            </div>
            <div className={'browser_selector'}
                 onClick={() => openEachBrowser('chrome')}
            >크롬을 사용중인 경우 (click)
            </div>
            <div className={'user_browser chrome'}>
                <div>1. 우측 상단 공유버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_1_chrome')} alt=""/>
                <div>2. 홈 화면에 추가를 선택해주세요</div>
                <img src={getGuideImg('app_view_guide_2')} alt=""/>
                <div>3. 추가 버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_3')} alt=""/>
                <div>4. 앱처럼 사용할 수 있어요</div>
                <img src={getGuideImg('app_view_guide_4')} alt=""/>
            </div>
            <div className={'browser_selector'}
                 onClick={() => openEachBrowser('kakao')}
            >카카오톡을 사용중인 경우 (click)
            </div>
            <div className={'user_browser kakao'}>
                <div>1. 우측 하단 공유버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_1_kakao')} alt=""/>
                <div>2. 홈 화면에 추가를 선택해주세요</div>
                <img src={getGuideImg('app_view_guide_2')} alt=""/>
                <div>3. 추가 버튼을 눌러주세요</div>
                <img src={getGuideImg('app_view_guide_3')} alt=""/>
                <div>4. 앱처럼 사용할 수 있어요</div>
                <img src={getGuideImg('app_view_guide_4')} alt=""/>
            </div>
        </div>
    )
}

export default AppViewGuide;