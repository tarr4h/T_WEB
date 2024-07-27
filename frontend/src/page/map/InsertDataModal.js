import {useState} from "react";
import '../../css/InsertData.css';
import instance from "../../comn/AxiosInterceptor";
import InsertDataDetail from "./InsertDataDetail";

function InsertDataModal({}){

    const [keyword, setKeyword] = useState('');
    const [searchList, setSearchList] = useState([]);

    const keywordOnchange = (evt) => {
        setKeyword(evt.target.value);
    }

    const keywordKeyUp = (evt) => {
        if(evt.keyCode === 13){
            void searchNv()
        }
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
    }

    return (
        <div>
            <div>
                <div className={'searchInput wd_100'}>
                    <input type="text"
                           value={keyword}
                           onChange={keywordOnchange}
                           onKeyUp={keywordKeyUp}
                    />
                    <span onClick={searchNv}>검색</span>
                </div>
            </div>
            <div>
                <div style={{maxHeight: '50vh', overflow: 'auto'}}>
                    {
                        searchList.map((item, i) => (
                            <InsertDataDetail data={item} key={i}/>
                        ))
                    }
                </div>
            </div>
        </div>
    )
}

export default InsertDataModal;