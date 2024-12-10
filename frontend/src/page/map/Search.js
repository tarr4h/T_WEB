import SearchList from "./SearchList";
import SearchCond from "./SearchCond";
import * as comn from "../../comn/comnFunction";
import {useRef} from "react";

function Search({data, mcidList, searchParam, setParam, setLatlng}){

    const scrollRef = useRef(null);

    const scrollTop = () => {
        if(scrollRef.current){
            scrollRef.current.scrollTop = 0;
        }
    }

    return (
        <div className={'searchWrapper'}
             ref={scrollRef}
        >
            <SearchCond setParam={setParam}
                        searchParam={searchParam}
                        mcidList={mcidList}
                        setLatlng={setLatlng}
            />
            <div className={'searchListHd'}>
                <div>
                    검색결과 : {data.length}건
                </div>
            </div>
            <SearchList dataList={data}/>
            {
                comn.isMobile() ? (
                    <div className={'scrollTop'}
                         onClick={() => {scrollTop()}}
                    >
                        맨위로
                    </div>
                ) : null
            }
        </div>
    )
}


export default Search;