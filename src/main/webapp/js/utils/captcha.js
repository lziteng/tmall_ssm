/**
 * 验证码:Completely Automated Public Turing test to tell Computers and Humans Apart，简称CAPTCHA
 *
 *  参考地址:https://blog.csdn.net/qq_42463851/article/details/90755734
 */

var captcha_str = [];

/**
 * 刷新图片验证码
 */
function changeCaptcha() {
    var captcha$img = document.getElementById("captcha$img");//获取到id为captcha$img的canvas的对象，演员
    captcha$img.setAttribute("title", "看不清楚?换一张");
    captcha$img.setAttribute("alt", "看不清楚?换一张");
    var captcha$img_width = captcha$img.clientWidth == 0 ? 100 : captcha$img.clientWidth;
    var captcha$img_height = captcha$img.clientHeight == 0 ? 30 : captcha$img.clientHeight;

    var context = captcha$img.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
    captcha$img.width = captcha$img_width;
    captcha$img.height = captcha$img_height;
    var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0,q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m";
    var aCode = sCode.split(",");
    var aLength = aCode.length;//获取到数组的长度

    for (var i = 0; i <= 3; i++) {
        var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
        var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
        var txt = aCode[j];//得到随机的一个内容
        captcha_str[i] = txt;
        var x = 10 + i * 20;//文字在canvas上的x坐标
        var y = 15 + Math.random() * 8;//文字在canvas上的y坐标
        context.font = "bold 23px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * captcha$img_width, Math.random() * captcha$img_height);
        context.lineTo(Math.random() * captcha$img_width, Math.random() * captcha$img_height);
        context.stroke();
    }
    for (var i = 0; i <= 30; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        var x = Math.random() * captcha$img_width;
        var y = Math.random() * captcha$img_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}

/**
 * 得到随机的颜色值
 * @returns 颜色值
 */
function randomColor() {
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

/**
 * 初始化验证码
 */
function initCaptcha() {
    var captcha$img = document.getElementById("captcha$img");
    if (captcha$img) {
        changeCaptcha();
    }
}

function checkCaptcha() {
    var captcha = $("#captcha").val();
    if (captcha == "") {
        toastr.warning("请输入验证码！");
        return false;
    } else if (captcha.toLowerCase() == captcha_str.join("").toLowerCase()) {
        return true;
    } else {
        toastr.warning("验证码错误，请重新输入！");
        changeCaptcha();
        return false;
    }
}

$(function () {
    initCaptcha();
});
