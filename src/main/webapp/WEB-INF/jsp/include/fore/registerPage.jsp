<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    //注册表单验证
    $(validRegisterForm());

    $(function () {
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#submit").click();
            }
        });

        $("#submit").click(function () {
            if (checkCaptcha() && validRegisterForm().form()) {
                $.ajax({
                    url: "/foreRegister",
                    data: $("#registerForm").serializeFormToJson(),
                    type: "post",
                    success: function (result) {
                        if (result == "success") {
                            window.location.href = "/registerSuccessPage";
                        }

                        if (result == "fail") {
                            toastr.warning("用户名已存在,请重新输入!");
                        }
                    }
                })
            }
        });
    });
</script>

<form method="post" class="registerForm" id="registerForm">
    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>

        <table class="registerTable" align="center">
            <tr>
                <td class="registerTip registerTableLeftTD">设置会员名</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆名</td>
                <td class="registerTableRightTD"><input name="userName" placeholder="会员名一旦设置成功，无法修改"></td>
            </tr>
            <tr>
                <td class="registerTip registerTableLeftTD">设置登陆密码</td>
                <td class="registerTableRightTD">登陆时验证，保护账号信息</td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登陆密码</td>
                <td class="registerTableRightTD">
                    <input id="password" name="password" type="password"
                           placeholder="设置你的登陆密码">
                </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码确认</td>
                <td class="registerTableRightTD"><input name="repassword" type="password" placeholder="请再次输入你的密码">
                </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">性别</td>
                <td class="registerTableRightTD">
                    <label class="radio-inline">
                        <input type="radio" name="sex" value="1" checked>
                        男
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="sex" value="0">
                        女
                    </label>
                </td>
            </tr>

            <tr>
                <td class="registerTableLeftTD">邮箱</td>
                <td class="registerTableRightTD">
                    <input name="email" type="text" placeholder="设置你的邮箱">
                </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">手机号码</td>
                <td class="registerTableRightTD">
                    <input name="mobile" type="text" placeholder="设置你的手机号码">
                </td>
            </tr>

            <tr>
                <td class="registerTableLeftTD">
                    验证码
                </td>
                <td class="registerTableRightTD">
                    <input type="text" placeholder="请输入验证码（区分大小写）"
                           style="height: 30px; position: relative;top: -11px; font-size: 12px;" id="captcha">
                    <canvas id="captcha$img" width="100" height="30"
                            style="border: 1px solid #ccc; border-radius: 5px;" onclick="changeCaptcha()"></canvas>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="registerButtonTD">
                    <%--                    <a href="registerSuccess.jsp">--%>
                    <button type="button" id="submit">提 交</button>
                    <%--                    </a>--%>
                </td>
            </tr>
        </table>
    </div>
</form>