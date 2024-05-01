import '../../css/Search.css';
import {useEffect, useState} from "react";
import Summary from "./Summary";

function SearchList({dataList}) {

    return (
        <div className={'searchList'}>
            {dataList.map((item, index) => (
                <Summary key={index}
                    data={item}/>
            ))}
        </div>
    )
}


export default SearchList;