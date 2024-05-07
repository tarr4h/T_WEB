import {useEffect, useState} from "react";
import axios from "axios";
import Search from "./Search";
import * as comn from '../../comn/comnFunction';
const {naver} = window;

function Map(){

    const [map, setMap] = useState(null);
    const [searchParam, setSearchParam] = useState(null);
    const [data, setData] = useState([]);
    const [mcidList, setMcidList] = useState([]);

    const [ifwList, setIfwList]= useState([]);

    const [latlng, setLatlng] = useState(null);

    useEffect(() => {
        void showMap();
    }, [searchParam]);

    const showMap = async (lat, lng, force) => {
        if(!lat && !lng){
            const geo = await getGeolocation();
            lat = geo.lat;
            lng = geo.lng;
        }

        if(!force && latlng != null){
            lat = latlng.lat;
            lng = latlng.lng;
        }

        // map
        const mapOpts = {
            center: new naver.maps.LatLng(lat, lng),
            // zoom: 15
        };

        let map = new naver.maps.Map('map', mapOpts);
        setMap(map);

        // marker
        const marker = setMarker(map, lat, lng, 'LOCATION');

        // infowindow
        setInfoWindow(map, marker, '현재위치');
        naver.maps.Event.addListener(map, 'click', function(e){
            showMap(e.latlng._lat, e.latlng._lng, true);
            setLatlng({
                lat : e.latlng._lat,
                lng : e.latlng._lng
            });
        });

        // getdata
        let param;
        if(searchParam == null){
            param = {radius : comn.getSuitableRadius()};
        } else {
            param = searchParam;
        }
        param.lat = lat;
        param.lng = lng;

        if(force){
            param.addr1 = '';
            param.addr2 = '';
            param.radius = comn.getSuitableRadius();
        }
        setSearchParam(param);
        const retData = await getData(param);
        const dataList = retData.dataList;
        const aDataList = [];

        const aifwList = [];
        dataList.forEach((dt) => {
            const mkr = setMarker(map, dt.py, dt.px, dt.mcid);
            const infoWindow = setInfoWindow(map, mkr, dt);
            aifwList.push(infoWindow);
            dt.ifw = infoWindow;
            dt.map = map;
            dt.marker = mkr;
            aDataList.push(dt);
        });

        setData(aDataList);
        setIfwList(aifwList);

        // circle
        const circle = new naver.maps.Circle({
            map,
            center : new naver.maps.LatLng(lat, lng),
            radius : param.radius * 1000,
            fillColor : '#071d1d',
            fillOpacity : 0.1
        });

        // fit
        const maxLatLng = retData.maxLatLng;
        const fitBound = new naver.maps.LatLngBounds(
            new naver.maps.LatLng(maxLatLng.minLat, maxLatLng.minLng),
            new naver.maps.LatLng(maxLatLng.maxLat, maxLatLng.maxLng)
        )
        map.fitBounds(fitBound);
    }

    const imgGend = (type) => {
        let img = '';

        switch (type) {
            case 'LOCATION' :
                img = 'location-pin.png';
                break;
            case 'DINING' :
                img = 'food.png';
                break;
            case 'BAR' :
                img = 'food.png';
                break;
            case 'CAFE' :
                img = 'cafe.png';
                break;
            case 'ACCOMMODATION':
                img = 'accomodation.png';
                break;
            default :
                img = 'question.png';
        }

        return require(`../../images/${img}`);
    }

    const setMarker = (map, lat, lng, type) => {
        const opt = {
            position : new naver.maps.LatLng(lat, lng),
            map
        };

        if(type){
            const imgSrc = imgGend(type);
            opt.icon = {
                url : imgSrc,
                size : new naver.maps.Size(25, 25),
                scaledSize : new naver.maps.Size(25, 25),
                origin : new naver.maps.Point(0, 0),
                anchor : new naver.maps.Point(10, 7)
            };
        }

        return new naver.maps.Marker(opt);
    }

    const ifwClick = (data) => {
        let actSearchParam = {};
        Object.assign(actSearchParam, searchParam);
        actSearchParam.placeName = data.name;
        setSearchParam(actSearchParam);
    }

    const setInfoWindow = (map, marker, data) => {
        const ifwCont = document.createElement('div');
        ifwCont.innerText = data.name;
        const ifw = document.createElement('div');
        ifw.className = 'iw_inner';
        ifw.appendChild(ifwCont);
        const infoWindow = new naver.maps.InfoWindow({
            content : ifw,
            borderWidth: 0,
            disableAnchor : true,
            backgroundColor : 'transparent',
            pixelOffset : new naver.maps.Point(0, -3)
        });

        ifwCont.addEventListener('click', () => {
            ifwClick(data);
        })
        naver.maps.Event.addListener(marker, 'click', (e) => {
            if(infoWindow.getMap()){
                infoWindow.close();
            } else {
                infoWindow.open(map, marker);
                setTimeout(() => {
                    infoWindow.close();
                }, 5000);
            }
        });

        return infoWindow;
    }

    const getData = async (param) => {
        console.log('param : ', param);
        const ret = await(await axios.get('/comn/getData', {params : param})).data;
        setData(ret.dataList);
        setMcidList(ret.mcidList);
        return ret;
    }

    const getGeolocation = () => {
        return new Promise((resolve, reject) => {
            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition((position) => {
                    let lat = position.coords.latitude;
                    let lng = position.coords.longitude;

                    resolve({lat, lng});
                })
            }
        })
    }

    return (
        <div className={'mapContainer'}>
            <div id="map"></div>
            <Search data={data}
                    mcidList={mcidList}
                    searchParam={searchParam}
                    setParam={setSearchParam}
                    setLatlng={setLatlng}
            />
        </div>
    )
}


export default Map;