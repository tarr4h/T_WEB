import {useEffect, useState} from "react";
import '../../css/InsertData.css';
import instance from "../../comn/AxiosInterceptor";
import InsertDataDetail from "./InsertDataDetail";

function InsertDataModal({isOpen, setIsOpen}){

    const [keyword, setKeyword] = useState('');
    const [searchList, setSearchList] = useState([]);
    const [isClear, setIsClear] = useState(true);
    let lastTouch = 0;

    useEffect(() => {
        if(!isOpen){
            setSearchList([]);
            setKeyword('');
            setIsClear(true);
        }
    }, [isOpen]);

    const keywordOnchange = (evt) => {
        setKeyword(evt.target.value);
    }

    const keywordKeyUp = (evt) => {
        if(evt.keyCode === 13){
            void searchNv()
        }
    }

    const keywordDoubleClick = (evt) => {
        setKeyword('');
    }

    const keywordDoubleTouch = evt => {
        const now = new Date().getTime();
        const tapDelay = 300;

        if(now - lastTouch <= tapDelay){
            setKeyword('');
            evt.preventDefault();
        }

        lastTouch = now;
    }

    const searchNv = async() => {
        if(keyword === ''){
            alert('검색어를 입력해주세요');
            return;
        }
        const result = (await instance.get('/comn/nvSearch', {params : {searchTxt : keyword}})).data;
        const replaceList = Array.from(result.items).map(v => {
            return {
                ...v,
                title : v.title.replace(/<[^>]*>/g, '')
            }
        });
        setSearchList(replaceList);
        setIsClear(false);
    }

    return (
        <div>
            <span style={{paddingLeft: '1vw', fontSize: '0.8rem'}}>찾으시는 가게를 입력해주세요</span>
            <div>
                <div className={'searchInput wd_100'}>
                    <input type="text"
                           value={keyword}
                           onChange={keywordOnchange}
                           onKeyUp={keywordKeyUp}
                           onDoubleClick={keywordDoubleClick}
                           onTouchEnd={keywordDoubleTouch}
                    />
                    <span onClick={searchNv}>검색</span>
                </div>
            </div>
            <div>
                <div style={{maxHeight: '50vh', overflow: 'auto'}}>
                    {
                        searchList.length > 0 ?
                            searchList.map((item, i) => (
                                <InsertDataDetail data={item} key={i} setIsOpen={setIsOpen}/>
                            ))
                            :
                            isClear ?
                                null
                                :
                                <div className="nrAskRequest">
                                    <span>검색결과가 없습니다.</span>
                                </div>
                    }
                </div>
            </div>
        </div>
    )
}

export default InsertDataModal;