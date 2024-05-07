export function isMobile(){
    let userAgent = navigator.userAgent;
    return userAgent.match(/iPhone/i) || userAgent.match(/Android/i);
}

export function getSuitableRadius(){
    if(isMobile()){
        return 1;
    } else {
        return 3;
    }
}