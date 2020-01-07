<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    function search(event) {
        //当前按钮对应的form
        var data = $(event).closest("form").serializeArray();
        var url = "/admin_category_list?start=0";
        Partial.refresh(url, "refresh", data);
    }

    $(function () {
        $("#addForm").submit(function () {
            if (!checkEmpty("name", "分类名称")) {
                return false;
            }
            if (!checkEmpty("categoryPic", "分类图片")) {
                return false;
            }
            return true;
        });

        initEvent();
    });

    /**
     * 初始化局部内容里所有的相关事件(使用on方式基于已存在的document节点赋予事件)
     * 局部刷新后，由于返回的页面中，已在$(function())中进行初始化绑定，所以不需要再次初始化
     */
    function initEvent() {
        $(document).on("click", "span.up", function () {
            if (CheckSearchFormIsEmpty()) {
                var trParent = $(this).parent().parent();
                if (trParent.prev().length > 0) {
                    trParent.prev().before(trParent);
                    var id = $(this).attr("id");
                    $.get("/admin_category_move?direction=up&id=" + id, null)
                }
            }
        });

        $(document).on("click", "span.down", function () {
            if (CheckSearchFormIsEmpty()) {
                var trParent = $(this).parent().parent();
                if (trParent.next().length > 0) {
                    trParent.next().after(trParent);
                    var id = $(this).attr("id");
                    $.get("/admin_category_move?direction=down&id=" + id, null)
                }
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

        /**
         * 每个页面单独实现分页中的逻辑
         */
        $(document).on("click", "ul.pagination li:not([class='disabled']) a", function () {
            var start = $(this).attr("start");
            var param = $(this).attr("param");
            var url = location.pathname + "?start=" + start + param;
            var data = $($("form")[0]).serializeArray();
            Partial.refresh(url, "refresh", data);
        });
    }
</script>

<title>分类管理</title>

<div class="workingArea">
    <div class="panel panel-default">
        <div class="panel-heading">
            分类管理
        </div>
        <div class="panel-body">
            <form role="form" class="form-inline" id="searchForm">
                <div class="form-group">
                    <label for="search_name">分类名称</label>
                    <input type="text" class="form-control" id="search_name" name="name" placeholder="请输入分类名称">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default" onclick="search(this)">开始搜索</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </form>
        </div>

        <refresh id="refresh">
            <div class="listDataTableDiv">
                <table class="table table-striped table-bordered table-hover  table-condensed">
                    <thead>
                    <tr class="success">
                        <th width="5%">ID</th>
                        <th width="35%">图片</th>
                        <th width="30%">分类名称</th>
                        <th width="5%">属性管理</th>
                        <th width="5%">产品管理</th>
                        <th width="10%">排序</th>
                        <th width="5%">编辑</th>
                        <th width="5%">删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="c">

                        <tr>
                            <td>${c.id}</td>
                            <td><img height="40" src="img/category/${c.id}.jpg" data-action="zoom"></td>
                            <td>${c.name}</td>

                            <td><a href="/admin_property_list?cid=${c.id}"><span
                                    class="glyphicon glyphicon-th-list"></span></a>
                            </td>
                            <td><a href="/admin_product_list?cid=${c.id}"><span
                                    class="glyphicon glyphicon-shopping-cart"></span></a>
                            </td>

                            <td>
                                <span id="${c.id}" class="down"></span>
                                <span id="${c.id}" class="up"></span>
                            </td>
                            <td><a href="/admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a>
                            </td>
                            <td><a deleteLink="true" _href="/admin_category_delete?id=${c.id}"><span
                                    class="   glyphicon glyphicon-trash"></span></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="pageDiv">
                <%@include file="../include/admin/adminPage.jsp" %>
            </div>
        </refresh>
        <div class="panel panel-warning addDiv">
            <div class="panel-heading">新增分类</div>
            <div class="panel-body">
                <form method="post" id="addForm" action="/admin_category_add" enctype="multipart/form-data">
                    <table class="addTable">
                        <tr>
                            <td>分类名称</td>
                            <td><input id="name" name="name" type="text" class="form-control"></td>
                        </tr>
                        <tr>
                            <td>分类圖片</td>
                            <td>
                                <input id="categoryPic" accept="image/*" type="file" name="image"/>
                            </td>
                        </tr>
                        <tr class="submitTR">
                            <td colspan="2" align="center">
                                <button type="submit" class="btn btn-success">提 交</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>