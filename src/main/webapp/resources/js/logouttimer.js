
var iSecond; //초단위로 환산
var timerchecker = null;
window.onload = function() {
    fncClearTime();
    initTimer();
}
 
function fncClearTime() {
    iSecond = 5400; //1시간 30분
}
 
Lpad = function(str, len) {
    str = str + "";
    while (str.length < len) {
        str = "0" + str;
    }
    return str;
}
 
initTimer = function() {
    var timer = document.getElementById("timer");
    rHour = parseInt(iSecond / 3600);
    rHour = rHour % 60;
 
    rMinute = parseInt(iSecond / 60);
    rMinute = rMinute % 60;
 
    rSecond = iSecond % 60;
 
    if (iSecond > 0) {
        timer.innerHTML = "&nbsp;" + Lpad(rHour, 2) + "시간 " + Lpad(rMinute, 2)
                + "분 " + Lpad(rSecond, 2) + "초 ";
        iSecond--;
        timerchecker = setTimeout("initTimer()", 1000); // 1초 간격으로 체크
    } else {
        logoutUser();
    }
}
 
function refreshTimer() {
    var xhr = initAjax();
    xhr.open("POST", "/mylist", false); //시간연장 버튼 클릭시 Ajax 로 특정 dummy page 에 비동기 호출을 하여 세션을 유지시킨뒤 시간값 초기화
    xhr.send();
    fncClearTime();
}
 
function logoutUser() {
    clearTimeout(timerchecker);
    var xhr = initAjax();
    xhr.open("GET", "/logout", false); //로그아웃시 Ajax 로 서버측 로그아웃 컨트롤러로 요청후 페이지 새로고침
    xhr.send();
    location.reload();
}
 
function initAjax() { // 브라우저에 따른 AjaxObject 인스턴스 분기 처리
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

