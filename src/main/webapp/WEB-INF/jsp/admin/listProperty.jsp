<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    function search(event) {
        //当前按钮对应的form
        var data = $(event).closest("form").serializeArray();
        var param = $("#param").val();
        var url = "/admin_property_list?start=0" + param;
        Partial.refresh(url, "refresh", data);
    }

    $(function () {
        $("#addForm").submit(function () {
            if (checkEmpty("name", "属性名称")) {
                return true;
            }
            return false;
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
                    var cid = $(this).attr("cid");
                    var id = $(this).attr("id");
                    $.get("/admin_property_move?direction=up&id=" + id + "&cid=" + cid, null)
                }
            }
        });

        $(document).on("click", "span.down", function () {
            if (CheckSearchFormIsEmpty()) {
                var trParent = $(this).parent().parent();
                if (trParent.next().length > 0) {
                    trParent.next().after(trParent);
                    var cid = $(this).attr("cid");
                    var id = $(this).attr("id");
                    $.get("/admin_property_move?direction=down&id=" + id + "&cid=" + cid, null)
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

<title>属性管理</title>


<div class="workingArea">
    <input type="hidden" id="param" value="${page.param}"/>

    <ol class="breadcrumb">
        <li><a href="/admin_category_list">所有分类</a></li>
        <li><a href="/admin_property_list?cid=${c.id}">${c.name}</a></li>
        <li class="active">属性管理</li>
    </ol>

    <div class="panel panel-default">
        <div class="panel-body">
            <form role="form" class="form-inline" id="searchForm">
                <div class="form-group">
                    <label for="search_name">属性名称</label>
                    <input type="text" class="form-control" id="search_name" name="name" placeholder="请输入属性名称">
                </div>
                <div class="form-group">
                    <button type="button" class="btn btn-default" onclick="search(this)">开始搜索</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </form>
        </div>

        <refresh id="refresh">
            <div class="listDataTableDiv">
                <table
                        class="table table-striped table-bordered table-hover  table-condensed">
                    <thead>
                    <tr class="success">
                        <th>ID</th>
                        <th>属性名称</th>
                        <th width="10%">排序</th>
                        <th>编辑</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ps}" var="p">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.name}</td>
                            <td>
                                <span cid="${p.cid}" id="${p.id}" class="down"></span>
                                <span cid="${p.cid}" id="${p.id}" class="up"></span>
                            </td>
                            <td>
                                <a href="/admin_property_edit?id=${p.id}">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </a>
                            </td>
                            <td>
                                <a deleteLink="true" _href="/admin_property_delete?id=${p.id}">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </a>
                            </td>
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
            <div class="panel-heading">新增属性</div>
            <div class="panel-body">
                <form method="post" id="addForm" action="/admin_property_add">
                    <table class="addTable">
                        <tr>
                            <td>属性名称</td>
                            <td><input id="name" name="name" type="text"
                                       class="form-control"></td>
                        </tr>
                        <tr class="submitTR">
                            <td colspan="2" align="center">
                                <input type="hidden" name="cid" value="${c.id}">
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
