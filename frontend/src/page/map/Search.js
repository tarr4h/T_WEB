import {useEffect} from "react";
import SearchList from "./SearchList";
import SearchCond from "./SearchCond";

function Search({data, searchParam, setParam, setLatlng}){

    useEffect(() => {

    }, [data]);

    return (
        <div className={'searchWrapper'}>
            <SearchCond setParam={setParam}
                        searchParam={searchParam}
                        setLatlng={setLatlng}
            />
            <SearchList data={data}/>
        </div>
    )
}


export default Search;