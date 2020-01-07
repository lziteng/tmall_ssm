<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>

<html>

<head>
    <script src="/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="/css/back/style.css" rel="stylesheet">

    <%--输入校验--%>
    <script src="/js/jquery/jquery.validate.min.js"></script>
    <link href="/css/fore/style.css" rel="stylesheet">

    <%--基于jQuery的非阻塞、简单、漂亮的消息提示插件:toastr.js--%>
    <script src="/js/toastr/toastr.min.js"></script>
    <link href="/css/toastr/toastr.min.css" rel="stylesheet">

    <%--可编程的对话框(非阻塞式)bootbox.js--%>
    <script src="/js/bootbox/bootbox.min.js"></script>

    <%--图片缩放:zoom.js(依赖于transition.js文件,此文件已被bootstrap.min.js包含在内)--%>
    <script src="/js/zoom/zoom.js"></script>
    <link href="/css/zoom/zoom.css" rel="stylesheet">

    <script src="/js/utils/partial.js"></script>
    <script src="/js/utils/utils.js"></script>

    <script>
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
            "timeOut": "1500",//1.5s后关闭消息框
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };

        //语言改为中文(4.x版本)
        bootbox.setDefaults("locale", "zh_CN");

        function checkEmpty(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                $("#" + id)[0].focus();
                toastr.warning(name + "不能为空");
                return false;
            }
            return true;
        }

        function checkNumber(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                $("#" + id)[0].focus();
                toastr.warning(name + "不能为空");
                return false;
            }
            if (isNaN(value)) {
                $("#" + id)[0].focus();
                toastr.warning(name + "不能为空");
                return false;
            }

            return true;
        }

        function checkInt(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                $("#" + id)[0].focus();
                toastr.warning(name + "不能为空");
                return false;
            }
            if (parseInt(value) != value) {
                $("#" + id)[0].focus();
                toastr.warning(name + "不能为空");
                return false;
            }

            return true;
        }

        /**
         * 删除操作提示
         */
    /*    $(function () {
            $("a").click(function () {
                var deleteLink = $(this).attr("deleteLink");
                var href = $(this).attr("_href");
                if ("true" == deleteLink) {
                    bootbox.confirm("确认要删除", function (result) {
                        if (result) {
                            window.location.href = href;
                        }
                    });
                }
            });
        })*/
    </script>
</head>
<body>

