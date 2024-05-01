import {useEffect, useState} from "react";
import SearchList from "./SearchList";
import SearchCond from "./SearchCond";

function Search({data, mcidList, searchParam, setParam, setLatlng}){

    return (
        <div className={'searchWrapper'}>
            <SearchCond setParam={setParam}
                        searchParam={searchParam}
                        mcidList={mcidList}
                        setLatlng={setLatlng}
            />
            <div className={'searchListHd'}>검색결과 : {data.length}건</div>
            <SearchList dataList={data}/>
        </div>
    )
}


export default Search;