<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>

<script>
    function search(event) {
        //当前按钮对应的form
        var data = $(event).closest("form").serializeArray();
        var url = "/admin_link_list?start=0";
        Partial.refresh(url, "refresh", data);
    }

    $(function () {
        initEvent();
    });

    /**
     * 局部刷新后，需要重新初始化局部内容里所有的相关事件(使用on方式基于已存在的document节点重新赋予事件)
     */
    function initEvent() {
        $(document).on("click", "span.up", function () {
            if (CheckSearchFormIsEmpty()) {
                var trParent = $(this).parent().parent();
                if (trParent.prev().length > 0) {
                    trParent.prev().before(trParent);
                    var id = $(this).attr("id");
                    $.get("/admin_link_move?direction=up&id=" + id, null, function () {
                    })
                }
            }

        });

        $(document).on("click", "span.down", function () {
            if (CheckSearchFormIsEmpty()) {
                var trParent = $(this).parent().parent();
                if (trParent.next().length > 0) {
                    trParent.next().after(trParent);
                    var id = $(this).attr("id");
                    $.get("/admin_link_move?direction=down&id=" + id, function (result) {
                    })
                }
            }
        });

        $("#batchDelBtn").click(function () {
            if ($("input[type='checkbox']:checked").length == 0) {
                toastr.warning("请勾选要删除的记录");
                return;
            }
            if (confirm("确定删除?")) {
                $("#linkForm").attr("action", "/admin_link_batch_delete");
                $("#linkForm").submit();
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
        })
    }
</script>

<title>分类管理</title>

<div class="workingArea">
    <%--<h1 class="label label-info">分类管理</h1>--%>

    <div class="panel panel-default">
        <div class="panel-heading">
            分类管理
        </div>
        <div class="panel-body">
            <form role="form" class="form-inline">
                <div class="form-group">
                    <label for="search_title">标题</label>
                    <input type="text" class="form-control" id="search_title" name="title" placeholder="请输入标题">
                </div>
                <div class="form-group">
                    <label for="search_url">链接</label>
                    <input type="text" class="form-control" id="search_url" name="url" placeholder="请输入链接">
                </div>
                <%-- <div class="form-group">
                     <label for="name">状态</label>
                     <select class="form-control">
                         <option>上架</option>
                         <option>下架</option>
                     </select>
                 </div>--%>
                <div class="form-group">
                    <button type="button" class="btn btn-default" onclick="search(this)">开始搜索</button>
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
                <h1 class="label label-info" id="batchDelBtn">批量删除</h1>
            </form>
        </div>

        <refresh id="refresh">
            <div class="listDataTableDiv">
                <form method="post" id="linkForm">
                    <table class="table table-striped table-bordered table-hover  table-condensed">
                        <thead>
                        <tr class="success">
                            <th>操作</th>
                            <th>ID</th>
                            <th>标题</th>
                            <th>链接</th>
                            <th>排序</th>
                            <%--<th>编辑</th>--%>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${links}" var="link">
                            <tr>
                                <td><input type="checkbox" name="ids" value="${link.id}"></td>
                                <td>${link.id}</td>
                                <td>${link.title}</td>
                                <td>${link.url}</td>
                                <td>
                                    <span id="${link.id}" class="down"></span>
                                    <span id="${link.id}" class="up"></span>
                                </td>
                                <td><a deleteLink="true" _href="/admin_link_delete?id=${link.id}"><span
                                        class="   glyphicon glyphicon-trash"></span></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </form>
            </div>

            <div class="pageDiv">
                <%@include file="../include/admin/adminPage.jsp" %>
            </div>
        </refresh>
        <div class="panel panel-warning addDiv">
            <div class="panel-heading">新增分类</div>
            <div class="panel-body">
                <form method="post" action="/admin_link_add" enctype="multipart/form-data">
                    <table class="addTable">
                        <tr>
                            <td>标题</td>
                            <td><input name="title" type="text" class="form-control"></td>
                        </tr>
                        <tr>
                            <td>地址</td>
                            <td><input name="url" type="text" class="form-control"></td>
                        </tr>
                        <%-- <tr>
                             <td>分类圖片</td>
                             <td>
                                 <input id="categoryPic" accept="image/*" type="file" name="image"/>
                             </td>
                         </tr>--%>
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

<%@include file="../include/admin/adminFooter.jsp" %>