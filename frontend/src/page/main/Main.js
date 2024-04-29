import {useEffect, useState} from "react";
import axios from "axios";
const {naver} = window;

function Main(){

    const [map, setMap] = useState(null);

    useEffect(() => {
        void showMap();
    }, []);

    const showMap = async (lat, lng) => {
        console.log('set map start ---');
        if(!lat && !lng){
            const geo = await getGeolocation();
            lat = geo.lat;
            lng = geo.lng;
        }

        // map
        const mapOpts = {
            center: new naver.maps.LatLng(lat, lng),
            zoom: 15
        };

        let map = new naver.maps.Map('map', mapOpts);
        setMap(map);

        // marker
        const marker = new naver.maps.Marker({
            position : new naver.maps.LatLng(lat, lng),
            map : map
        });

        // infowindow
        setInfoWindow(map, marker, '현재위치');
        naver.maps.Event.addListener(map, 'click', function(e){
            showMap(e.latlng._lat, e.latlng._lng);
        });

        // getdata
        const dataList = await getData({distance : 3, lat, lng});
        dataList.forEach((data) => {
            const mkr = new naver.maps.Marker({
                position : new naver.maps.LatLng(data.py, data.px),
                map
            });

            setInfoWindow(map, mkr, data.name);
        });
    }

    const setInfoWindow = (map, marker, text) => {
        const ifw = [
            `<div class="iw_inner">${text}</div>`
        ].join('');
        const infoWindow = new naver.maps.InfoWindow({
            content : ifw
        });

        naver.maps.Event.addListener(marker, 'click', function(e){
            if(infoWindow.getMap()){
                infoWindow.close();
            } else {
                infoWindow.open(map, marker);
            }
        });
    }

    const getData = async (param) => {
        console.log('param = ', param);
        return await(await axios.get('/comn/getData', {params : param})).data;
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

    const dataImport = async (e) => {
        const files = e.target.files;
        let formData = new FormData();
        Array.from(files).forEach(file => {
            formData.append('file', file);
        });

        const res = await(await insertFile(formData)).json();
        console.log('data import result : ', res);
    }

    const insertFile = (formData) => {
        return new Promise((resolve) => {
            const res = fetch('/comn/importFiles', {
                method : 'POST',
                headers : {
                    enctype : 'multipart/form-data'
                },
                body : formData
            });

            resolve(res);
        })
    }

    return (
        <div>
            <h3>HOME</h3>
            <div>
                {/*<label htmlFor="importFile">DATA_IMPORT</label>*/}
                {/*<input type="file" id="importFile"*/}
                {/*       onChange={dataImport}*/}
                {/*       multiple={true} hidden={true}/>*/}
                <div id="map" style={{width:'100%', height:'70em'}}></div>
            </div>
        </div>
    )
}


export default Main;