<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        $(".addFormSingle").submit(function () {
            if (checkEmpty("filepathSingle", "图片文件")) {
                // $("#filepathSingle").value("");
                return true;
            }
            return false;
        });
        $(".addFormDetail").submit(function () {
            if (checkEmpty("filepathDetail", "图片文件")){
                return true;
            }

            return false;
        });

        initEvent();
    });

    function initEvent() {
        $(document).on("click", "span.up", function () {
            var trParent = $(this).parent().parent();
            if (trParent.prev().length > 0) {
                trParent.prev().before(trParent);
                var pid = $(this).attr("pid");
                var id = $(this).attr("id");
                var type = $(this).attr("type");
                $.get("/admin_productImage_move?direction=up&id=" + id + "&pid=" + pid + "&type=" + type, null)
            }
        });

        $(document).on("click", "span.down", function () {
            var trParent = $(this).parent().parent();
            if (trParent.next().length > 0) {
                trParent.next().after(trParent);
                var pid = $(this).attr("pid");
                var id = $(this).attr("id");
                var type = $(this).attr("type");

                $.get("/admin_productImage_move?direction=down&id=" + id + "&pid=" + pid + "&type=" + type, null)
            }
        });

        $(document).on("click", "a", function () {
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
    }
</script>

<title>产品图片管理</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="/admin_category_list">所有分类</a></li>
        <li><a href="/admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">产品图片管理</li>
    </ol>

    <table class="addPictureTable" align="center">
        <tr>
            <td class="addPictureTableTD">
                <div>
                    <div class="panel panel-warning addPictureDiv">
                        <div class="panel-heading">新增产品<b class="text-primary"> 单个 </b>图片</div>
                        <div class="panel-body">
                            <form method="post" class="addFormSingle" action="/admin_productImage_add"
                                  enctype="multipart/form-data">
                                <table class="addTable">
                                    <tr>
                                        <td>请选择本地图片 尺寸400X400 为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathSingle" type="file" name="image"/>
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_single"/>
                                            <input type="hidden" name="pid" value="${p.id}"/>
                                            <button type="submit" class="btn btn-success">提 交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                    <table class="table table-striped table-bordered table-hover  table-condensed">
                        <thead>
                        <tr class="success">
                            <th width="10%">ID</th>
                            <th width="50%" style="text-align: center;">产品单个图片缩略图</th>
                            <th width="30%" style="text-align: center;">排序</th>
                            <th width="10%">删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pisSingle}" var="pi">
                            <tr>
                                <td>${pi.id}</td>
                                <td>
                                        <%--  <a title="点击查看原图" href="img/productSingle/${pi.id}.jpg"></a>--%>
                                    <img height="50" src="img/productSingle/${pi.id}.jpg" data-action="zoom">
                                </td>
                                <td>
                                    <span pid="${pi.pid}" id="${pi.id}" type="type_single" class="down"></span>
                                    <span pid="${pi.pid}" id="${pi.id}" type="type_single" class="up"></span>
                                </td>
                                <td>
                                    <a deleteLink="true" _href="/admin_productImage_delete?id=${pi.id}">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
            <td class="addPictureTableTD">
                <div>
                    <div class="panel panel-warning addPictureDiv">
                        <div class="panel-heading">新增产品<b class="text-primary"> 详情 </b>图片</div>
                        <div class="panel-body">
                            <form method="post" class="addFormDetail" action="/admin_productImage_add"
                                  enctype="multipart/form-data">
                                <table class="addTable">
                                    <tr>
                                        <td>请选择本地图片 宽度790 为佳</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input id="filepathDetail" type="file" name="image"/>
                                        </td>
                                    </tr>
                                    <tr class="submitTR">
                                        <td align="center">
                                            <input type="hidden" name="type" value="type_detail"/>
                                            <input type="hidden" name="pid" value="${p.id}"/>
                                            <button type="submit" class="btn btn-success">提 交</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                    <table class="table table-striped table-bordered table-hover  table-condensed">
                        <thead>
                        <tr class="success">
                            <th width="10%">ID</th>
                            <th width="50%" style="text-align: center;">产品详情图片缩略图</th>
                            <th width="30%" style="text-align: center;">排序</th>
                            <th width="10%">删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pisDetail}" var="pi">
                            <tr>
                                <td>${pi.id}</td>
                                <td>
                                        <%-- <a title="点击查看原图" href="img/productDetail/${pi.id}.jpg">   </a>--%>
                                    <img height="50" src="img/productDetail/${pi.id}.jpg" data-action="zoom">
                                </td>
                                <td>
                                    <span pid="${pi.pid}" id="${pi.id}" type="type_detail" class="down"></span>
                                    <span pid="${pi.pid}" id="${pi.id}" type="type_detail" class="up"></span>
                                </td>
                                <td>
                                    <a deleteLink="true" _href="/admin_productImage_delete?id=${pi.id}">
                                        <span class="glyphicon glyphicon-trash"></span></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>
