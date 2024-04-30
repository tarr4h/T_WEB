import '../../css/Search.css';
import {useEffect, useState} from "react";
import Summary from "./Summary";

function SearchList({data}) {

    useEffect(() => {
    }, [data]);

    return (
        <div className={'searchList'}>
            {data.map((item, index) => (
                <Summary key={index}
                    data={item}/>
            ))}
        </div>
    )
}


export default SearchList;