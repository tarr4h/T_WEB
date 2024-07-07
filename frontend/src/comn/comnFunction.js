export function isMobile(){
    let userAgent = navigator.userAgent;
    return userAgent.match(/iPhone/i) || userAgent.match(/Android/i);
}

export function getSuitableRadius(){
    const saveRadius = window.localStorage.getItem('radius');
    if(saveRadius){
        return Number(saveRadius);
    }

    if(isMobile()){
        return 1;
    } else {
        return 3;
    }
}

export function imgGend(type){
    let img = '';

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

    return require(`../images/${img}`);
}
