$(function(){
    var username = getCookie("Cookie_username");
    $("#username").val(username);
    
    if($("#username").val() != "")
        $("#save-id").attr("checked", true);
});
 
function loginProcess(){
    var username = document.getElementById('username');
    var password = document.getElementById('password');
    var btnlogin = document.getElementById('btn-login');
    
    if(username.value==""){
        alert("Enter username");
        username.focus();
        return false;
    }else if(password.value==""){
        alert("Enter password");
        password.focus();
        return false;
    }else if($("#save-id").is(":checked")){
        var username = $("#username").val();
        setCookie("Cookie_username", username, 30);
        btnlogin.submit();
    }else{
        deleteCookie("Cookie_username");
        btnlogin.submit();
    }
}

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}

function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}