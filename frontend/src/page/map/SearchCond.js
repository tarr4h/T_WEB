import {useEffect, useRef, useState} from "react";
import axios from "axios";

function SearchCond({searchParam, setParam, setLatlng}){

    const [radius, setRadius] = useState(3);
    const [mcid, setMcid] = useState('');
    const [placeName, setPlaceName] = useState('');

    const [mcidList, setMcidList] = useState([]);

    const selectedMcid = useRef(null);

    useEffect(() => {
        if(radius){
            applyParam();
        }
    }, [radius, mcid]);

    useEffect(() => {
        if(searchParam != null && searchParam.lat && searchParam.lng){
            void getMcid();
        }
    }, [searchParam]);

    const getMcid = async () => {
        const list = await(await axios.get('/comn/getMcid', {params : searchParam})).data;
        setMcidList(list);
    }

    const radiusOnchange = (event) => {
        const v = event.target.value;
        if(v === 0) return;
        if(v >= 10) {
            alert('10km 미만으로만 설정 가능합니다.');
            return;
        }
        setRadius(v);
    }

    const mcidOnchange = (event) => {
        const v = event.target.value;
        setMcid(v);
    }

    const placeNameOnchange = (event) => {
        const v = event.target.value;
        setPlaceName(v);
    }

    const applyParam = () => {
        let p = {};
        if(searchParam){
            Object.assign(p, searchParam);
        }
        p.radius = radius;
        p.mcid = mcid;
        p.placeName = placeName;
        setParam(p);
    }

    const setCurrentLoc = () => {
        setLatlng(null);
    }

    return (
        <div>
            <div onClick={setCurrentLoc}>현위치
            </div>
            <div>
                <span>반경(km)</span>
                <input type="number"
                       value={radius}
                       onChange={radiusOnchange}
                />
            </div>
            <div>
                <span>분류</span>
                <select onChange={mcidOnchange}
                        defaultValue={''}
                        ref={selectedMcid}
                >
                    <option value="">전체</option>
                    {mcidList.map((mcid, index) => (
                        <option value={mcid.mcid}
                                key={index}
                        >{mcid.mcidName}</option>
                    ))}
                </select>
            </div>
            <div>
                <span>가게명</span>
                <input type="text"
                       value={placeName}
                       onChange={placeNameOnchange}
                />
                <div onClick={() => {applyParam()}}>반영</div>
            </div>
        </div>
    )
}

export default SearchCond;