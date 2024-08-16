export function isMobile(){
    let userAgent = navigator.userAgent;
    return userAgent.match(/iPhone/i) || userAgent.match(/Android/i);
}

export function getSuitableRadius(){
    const saveRadius = getRadius();
    if(saveRadius){
        return Number(saveRadius);
    }

    if(isMobile()){
        return 1;
    } else {
        return 3;
    }
}

export function imgGend(type, path){
    let img = '';

    if(!path){
        switch (type) {
            case 'LOCATION' :
                img = 'location-pin.png';
                break;
            case 'DINING' :
                img = 'food.png';
                break;
            case 'BAR' :
                img = 'beer_pub.png';
                break;
            case 'CAFE' :
                img = 'cafe.png';
                break;
            case 'ACCOMMODATION':
                img = 'dog_accomodation.png';
                break;
            default :
                img = 'question.png';
        }
    } else {
        img = path;
    }

    return require(`../images/${img}`);
}

export function blockUI(){
    let bodyHeight = document.body.scrollHeight;
    const block = window.document.getElementById('blockUI');
    block.style.height = bodyHeight * 1.05 + 'px';
    block.style.display = 'block';
}

export function unBlockUI(){
    const block = window.document.getElementById('blockUI');
    block.style.display = 'none';
}

export function setLocalStorage(key, value){
    window.localStorage.setItem(key, value);
}

export function removeLocalStorage(key){
    window.localStorage.removeItem(key);
}

export function getLocalStorage(key){
    return window.localStorage.getItem(key);
}

export function setZoom(zoom){
    setLocalStorage('zoom', zoom.toString());
}

export function removeZoom(){
    removeLocalStorage('zoom');
}

export function getZoom(){
    return getLocalStorage('zoom');
}

export function setRadius(radius){
    setLocalStorage('radius', radius.toString());
}

export function removeRadius(){
    removeLocalStorage('radius')
}

export function getRadius(){
    return getLocalStorage('radius');
}

