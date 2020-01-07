//消息提示全局配置
toastr.options = {
    "closeButton": false,//是否配置关闭按钮
    "debug": false,//是否开启debug模式
    "newestOnTop": false,//新消息是否排在最上层
    "progressBar": false,//是否显示进度条
    "positionClass": "toast-center-center",//消息框的显示位置 toast-center-center、toast-top-center
    "preventDuplicates": false,//是否阻止弹出多个消息框
    "onclick": null,//点击回调函数
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "2000",//2s后关闭消息框
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
};

//序列化表单字段为json对象
$.fn.serializeFormToJson = function () {
    var arr = $(this).serializeArray();//form表单数据 name：value
    var param = {};
    $.each(arr, function (i, obj) { //将form表单数据封装成json对象
        param[obj.name] = obj.value;
    });
    return param;
};

/**
 *  检测表单form里的所有输入框是否为空
 * @returns {boolean}
 * @constructor
 */
$.fn.CheckFormIsEmpty = function () {
    var arr = $(this).serializeArray();//form表单数据 name：value
    var emptyTag = true;
    $.each(arr, function (i, obj) {
        if (obj.value != '') {
            emptyTag = false;
        }
    });
    return emptyTag;
};

jQuery.validator.addMethod("isMobile", function (value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写手机号码");

jQuery.validator.addMethod("isUserName", function (value, element) {
    var userblank = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{3,12}$/;
    return this.optional(element) || (userblank.test(value));
}, "需包含数字和大小写字母中至少两种字符的3-12位字符");


/**
 * 判断页面中第一个form(通常为搜索form)的所有输入框是否为空
 * @returns {boolean}
 * @constructor
 */
function CheckSearchFormIsEmpty() {
    if (!$($("form")[0]).CheckFormIsEmpty()) {
        toastr.warning("请在清空查询条件的列表里进行排序!");
        return false;
    }
    return true;
}

/**
 *  输入框限制
 * @param event 输入框
 * @param pointSize 允许的小数点位数
 * @param intSize 整数位数
 */
function inputSlice(event, pointLen, intLen) {
    var reg = new RegExp('^[1-9]\\d{0,' + (parseInt(intLen, 10) - 1) + '}(?:\\.\\d{0,' + parseInt(pointLen, 10) + '})?')
    event.value = event.value.toString().match(reg);
}


var CryptoJsConfig = {
    key: "asdfghjkl1234567", // 密钥 长度16
    iv: "asdfghjkl1234567"// 密钥 长度16
};

/**
 *  AES加密
 * @param data 数据
 * @returns base64格式的密文
 */
function encodeAesString(data) {
    var key = CryptoJS.enc.Utf8.parse(CryptoJsConfig.key);
    var iv = CryptoJS.enc.Utf8.parse(CryptoJsConfig.iv);
    var encrypted = CryptoJS.AES.encrypt(data, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted + "";
}

/**
 * AES解密
 * @param encrypted base64格式的密文
 * @returns 数据明文
 */
function decodeAesString(encrypted) {
    var key = CryptoJS.enc.Utf8.parse(CryptoJsConfig.key);
    var iv = CryptoJS.enc.Utf8.parse(CryptoJsConfig.iv);
    var decrypted = CryptoJS.AES.decrypt(encrypted, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
}

/**
 * 登录form
 * @returns {*|jQuery}
 */
function validLoginForm() {
    return $("#loginForm").validate({
        rules: {
            "userName": {
                "required": true,
                "isUserName": true,
                "rangelength": [3, 12]
            },
            "password": {
                "required": true,
                "rangelength": [6, 12]
            }
        },
        messages: {
            "userName": {
                "required": "用户名不能为空",
                "isUserName": "需包含数字和大小写字母中至少两种字符的3-12位字符",
                "rangelength": "用户名长度3-12位"
            },
            "password": {
                "required": "密码不能为空",
                "rangelength": "密码长度6-12位"
            }
        },
        success: "valid"
    });
}

/**
 * 注册form
 * @returns {*|jQuery}
 */
function validRegisterForm() {
    return $("#registerForm").validate({
        rules: {
            "userName": {
                "required": true,
                "isUserName": true,
                "rangelength": [3, 12]
            },
            "password": {
                "required": true,
                "rangelength": [6, 12]
            },
            "repassword": {
                "required": true,
                "rangelength": [6, 12],
                "equalTo": "#password"
            },
            "email": {
                "required": true,
                "email": true
            },
            "mobile": {
                "required": true,
                "rangelength": [11, 11],
                "isMobile": true
            },
            "sex": {
                "required": true
            }
        },
        messages: {
            "userName": {
                "required": "用户名不能为空",
                "isUserName": "需包含数字和大小写字母中至少两种字符的3-12位字符",
                "rangelength": "用户名长度3-12位"
            },
            "password": {
                "required": "密码不能为空",
                "rangelength": "密码长度6-12位"
            },
            "repassword": {
                "required": "密码不能为空",
                "rangelength": "密码长度6-12位",
                "equalTo": "两次密码不一致"
            },
            "email": {
                "required": "邮箱不能为空",
                "email": "邮箱格式不正确"
            },
            "mobile": {
                "required": "手机号码不能为空",
                "rangelength": "号码长度必须为11位",
                "isMobile": "请正确填写手机号码"
            }
        },
        success: "valid"
    });
}

/**
 * 结算form
 * @returns {*|jQuery}
 */
function validOrderForm() {
    return $("#orderForm").validate({
        rules: {
            "address": {
                "required": true,
                "rangelength": [10, 300]
            },
            "receiver": {
                "required": true,
                "rangelength": [2, 25]
            },
            "post": {
                "rangelength": [6, 6]
            },
            "mobile": {
                "required": true,
                "rangelength": [11, 11],
                "isMobile": true
            },
            "userMessage": {
                "rangelength": [0, 200]
            }
        },
        messages: {
            "address": {
                "required": "详细地址不能为空",
                "rangelength": "详细地址长度10-300位"
            },
            "receiver": {
                "required": "收货人姓名不能为空",
                "rangelength": "收货人姓名长度2-25位"
            },
            "post": {
                "rangelength": "邮政编码最多6位"
            },
            "mobile": {
                "required": "手机号码不能为空",
                "rangelength": "号码长度必须为11位",
                "isMobile": "请正确填写手机号码"
            },
            "userMessage": {
                "rangelength": "留言最多200个字符"
            }
        },
        success: "valid"
    });
}