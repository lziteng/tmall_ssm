<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
</script>

<title>用户管理</title>
<script>
    $(function () {
        $("button.btn-success").click(function () {
            var id = $(this).attr("uid");
            bootbox.confirm("确定将密码重置为123456？", function (result) {
                if (result) {
                    var page = "/admin_user_reset_password";
                    $.post(page, {"id": id},
                        function (result) {
                            if ("success" == result) {
                                toastr.success('重置成功!');
                            }
                        }
                    )
                }
            });
        })
    })
</script>


<div class="workingArea">
    <h1 class="label label-info">用户管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>用户名称</th>
                <th>邮箱</th>
                <th>手机号码</th>
                <th>性别</th>
                <th>用户类型</th>
                <th>操作(重置密码)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${us}" var="u">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.userName}</td>
                    <td>${u.email}</td>
                    <td>${u.mobile}</td>
                    <td>${u.sex == 0 ? '女' : '男'}</td>
                    <td>${u.userType == 0 ? '外网用户' : '管理员' }</td>
                    <td>
                        <button type="button" class="btn btn-success" uid="${u.id}">重置</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>
