import {useEffect, useRef, useState} from "react";
import * as comn from '../../comn/comnFunction';
import axios from "axios";
import instance from '../../comn/AxiosInterceptor';

function SearchCond({mcidList, searchParam, setParam, setLatlng}){

    const [radius, setRadius] = useState(comn.getSuitableRadius());
    const [mcid, setMcid] = useState('');
    const [placeName, setPlaceName] = useState('');
    const [runSearch, setRunSearch] = useState(false);

    const selectedMcid = useRef(null);

    const [region1, setRegion1] = useState([]);
    const [region2, setRegion2] = useState([]);
    const selectedRegion1 = useRef(null);
    const selectedRegion2 = useRef(null);

    useEffect(() => {
        setMcid('');
        setRadius(comn.getSuitableRadius());
        setPlaceName('');
        setRunSearch(false);
        setRegion1([]);
        setRegion2([]);

        selectedMcid.current.value = '';
        selectedRegion1.current.value = '';
        selectedRegion2.current.value = '';
        void getRegion1();
    }, []);

    useEffect(() => {
        if(radius){
            if(radius < 10){
                void applyParam();
            }
        }
    }, [radius, mcid]);

    useEffect(() => {
        if(runSearch){
            void applyParam();
            setRunSearch(false);
        }
    }, [runSearch]);

    useEffect(() => {
        if(mcidList.length === 0){
            if(searchParam && searchParam.placeName !== placeName){
                setMcid('');
                selectedMcid.current.value = '';
                void applyParam();
            }
        } else {
            let bool = true;
            for(let i = 0; i < mcidList.length; i++){
                const item = mcidList[i];
                if(item.mcid === mcid){
                    selectedMcid.current.value = mcid;
                    bool = false;
                    break;
                }
            }

            if(bool){
                selectedMcid.current.value = '';
            }
        }

        if(searchParam){
            if(searchParam.placeName !== ''){
                setPlaceName(searchParam.placeName);
            }

            if(searchParam.radius !== radius){
                setRadius(searchParam.radius);
            }

            if(searchParam.addr1 !== selectedRegion1.current.value){
                selectedRegion1.current.value = '';
                selectedRegion2.current.value = '';
            }
            if(searchParam.addr2 !== selectedRegion2.current.value){
                selectedRegion2.current.value = '';
            }
        }
    }, [mcidList]);

    const radiusOnchange = (event) => {
        const v = event.target.value;
        if(v === 0) return;
        // if(v >= 10) {
        //     alert('10km 미만으로만 설정 가능합니다.');
        //     return;
        // }
        setRadius(v);
    }

    const mcidOnchange = (event) => {
        const v = event.target.value;
        setMcid(v);
        selectedMcid.current.value = v;
    }

    const placeNameOnchange = (event) => {
        const v = event.target.value;
        setPlaceName(v);
    }

    const applyParam = async () => {
        let p = {};
        if(searchParam){
            Object.assign(p, searchParam);
        }
        p.radius = radius;
        p.mcid = mcid;
        p.placeName = placeName;
        p.addr1 = selectedRegion1.current.value;
        p.addr2 = selectedRegion2.current.value;

        setParam(p);
    }

    const applyPlaceName = () => {
        setCurrentLoc();
    }

    const setCurrentLoc = () => {
        setLatlng(null);
        setMcid('');
        setPlaceName('');
        setRunSearch(true);
        setRadius(comn.getSuitableRadius());
        selectedRegion1.current.value = '';
        selectedRegion2.current.value = '';
        setRegion2([]);
    }

    const getRegion1 = async () => {
        // const region1 = await(await instance.get('/comn/getRegion1')).data;
        const region1 = (await instance.get('/comn/getRegion1')).data;
        setRegion1(region1);
    }

    const region1Onchange = async (e) => {
        const v = e.target.value;
        selectedRegion1.current.value = v;
        void getRegion2(v);

        const addr1GeoLoc = await getRegionGeoLoc({addr1 : v});
        setLatlng({lat : addr1GeoLoc.latitude, lng : addr1GeoLoc.longitude});
        setRadius(addr1GeoLoc.radius);
    }

    const getRegion2 = async (upRegion) => {
        const param = {
            addr1 : upRegion
        }
        const region2 = (await instance.get('/comn/getRegion2', {params : param})).data;
        setRegion2(region2);
    }

    const region2Onchange = async (e) => {
        const v2 = e.target.value;
        selectedRegion2.current.value = v2;

        const v1 = selectedRegion1.current.value;

        const addr2GeoLoc = await getRegionGeoLoc({addr1 : v1, addr2 : v2});
        setLatlng({lat : addr2GeoLoc.latitude, lng : addr2GeoLoc.longitude});
        setRadius(addr2GeoLoc.radius);
    }

    const getRegionGeoLoc = async(param) => {
        return (await instance.get('/comn/getRegionGeoLoc', {params : param})).data;
    }

    const updownRadius = (b) => {
        if(b){
            setRadius(v => v + 1);
        } else {
            if(radius === 1){
                return;
            }
            setRadius(v => v - 1);
        }
    }

    return (
        <div className={'searchFilter'}>
            <div>
                <div>
                    <span>지역</span>
                </div>
                <div className={'dualSelect'}>
                    <select onChange={region1Onchange}
                            defaultValue={''}
                            ref={selectedRegion1}
                            className={'select_sm wd_100'}
                    >
                        <option value="">전체</option>
                        {
                            region1 != null ? region1.map((item, index) => (
                                <option value={item.addr1}
                                        key={index}
                                >{item.addr1}</option>
                            )) : null
                        }
                    </select>
                    <select onChange={region2Onchange}
                            defaultValue={''}
                            ref={selectedRegion2}
                            className={'select_sm wd_100'}
                    >
                        <option value="">전체</option>
                        {
                            region2 != null ? region2.map((item, index) => (
                                <option value={item.addr2}
                                        key={index}
                                >{item.addr2}</option>
                            )) : null
                        }
                    </select>
                </div>
            </div>
            <div>
                <div>
                    <span>반경(km)</span>
                </div>
                <div className={'pmIp'}>
                    <input type="number"
                           value={radius}
                           onChange={radiusOnchange}
                           className={'wd_100'}
                    />
                    <div className={'pmBtn'}
                         onClick={() => {updownRadius(true)}}
                    >+</div>
                    <div className={'pmBtn'}
                         onClick={() => {updownRadius(false)}}
                    >-</div>
                </div>
            </div>
            <div>
                <div>
                    <span>분류</span>
                </div>
                <div>
                    <select onChange={mcidOnchange}
                            defaultValue={''}
                            ref={selectedMcid}
                            className={'select_sm wd_100'}
                    >
                        <option value="">전체</option>
                        {
                            mcidList != null ? mcidList.map((mcid, index) => (
                                <option value={mcid.mcid}
                                        key={index}
                                >{mcid.mcidName}</option>
                            )) : null
                        }
                    </select>
                </div>
            </div>
            <div>
                <div>
                    <span>가게명</span>
                </div>
                <div>
                    <div>
                        <input type="text"
                               value={placeName || ''}
                               onChange={placeNameOnchange}
                               className={'wd_100'}
                        />
                    </div>
                </div>
            </div>
            <div>
                <div onClick={applyPlaceName} className={'searchBtn'}>
                    <span>현위치</span>
                </div>
                <div onClick={() => {void applyParam()}}
                     className={'searchBtn'}
                >검색</div>
            </div>
        </div>
    )
}

export default SearchCond;