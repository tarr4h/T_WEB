import {useEffect, useRef, useState} from "react";
import * as comn from '../../comn/comnFunction';
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

    const [foldFilter, setFoldFilter] = useState(false);

    useEffect(() => {
        comn.removeRadius();
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
            if(radius <= 15){
                comn.removeZoom();
                void applyParam();
            }
        }
        setFoldFilter(false);
    }, [radius, mcid]);

    useEffect(() => {
        if(radius){
            comn.setRadius(radius);
        }
    }, [radius]);

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

    const applyParam = async (force) => {
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
        if(force){
            comn.removeZoom();
        }
        setFoldFilter(true);
    }

    const clearPlaceName = async () => {
        setPlaceName('');
        setRunSearch(true);
    }

    const applyPlaceName = () => {
        setCurrentLoc();
    }

    const setCurrentLoc = () => {
        setLatlng(null);
        setMcid('');
        setPlaceName('');
        comn.removeRadius();
        setRadius(comn.getSuitableRadius());
        selectedRegion1.current.value = '';
        selectedRegion2.current.value = '';
        setRegion2([]);
        setRunSearch(true);
        comn.removeZoom();
    }

    const getRegion1 = async () => {
        const region1 = (await instance.get('/comn/getRegion1')).data;
        setRegion1(region1);
    }

    const region1Onchange = async (e) => {
        const v = e.target.value;
        selectedRegion1.current.value = v;
        void getRegion2(v);
        selectedRegion2.current.value = '';

        const addr1GeoLoc = await getRegionGeoLoc({addr1 : v});
        setLatlng({lat : addr1GeoLoc.latitude, lng : addr1GeoLoc.longitude});
        setRadius(addr1GeoLoc.radius);
        setPlaceName('');
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
        setPlaceName('');
    }

    const getRegionGeoLoc = async(param) => {
        return (await instance.get('/comn/getRegionGeoLoc', {params : param})).data;
    }

    const updownRadius = (b) => {
        comn.removeZoom();
        if(b){
            setRadius(v => v + 1);
        } else {
            if(radius === 1){
                return;
            }
            setRadius(v => v - 1);
        }
    }

    const placeNameOnEnter = (e) => {
        if(e.keyCode === 13){
            void applyParam();
        }
    }

    const openSearchFilter = () => {
        setFoldFilter(v => !v);
    }


    return (
        <div>
            <div className={`hideSearchFilter ${comn.isMobile() && foldFilter ? '' : 'dpn'}`}
                 onClick={openSearchFilter}
            >
                검색하기
            </div>
            <div className={`searchFilter ${comn.isMobile() && foldFilter ? 'dpn' : ''}`}>
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
                            <option value="">선택</option>
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
                            <option value="">선택</option>
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
                             onClick={() => {
                                 updownRadius(true)
                             }}
                        >+
                        </div>
                        <div className={'pmBtn'}
                             onClick={() => {
                                 updownRadius(false)
                             }}
                        >-
                        </div>
                    </div>
                    <div style={{fontSize: '0.6rem',paddingLeft:'0.2svh', paddingTop:'0.3svh', color: '#9f9f9fe8', fontStyle: 'italic'}}>
                        반경 15km 초과인 경우 검색버튼을 눌러주세요.
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
                                   onKeyUp={placeNameOnEnter}
                                   className={'wd_100'}
                                   placeholder={', 을 사용하여 여러건 단어이 가능합니다.'}
                            />
                        </div>
                    </div>
                </div>
                <div>
                    <div onClick={applyPlaceName} className={'btn'}>
                        <span>현위치</span>
                    </div>
                    <div onClick={() => {
                        void applyParam(true)
                    }}
                         className={'btn'}
                    >검색
                    </div>
                    {
                        placeName && placeName !== '' ? (<div className={'btn'} onClick={clearPlaceName}>초기화</div>) : null
                    }
                </div>
            </div>
        </div>
    )
}

export default SearchCond;