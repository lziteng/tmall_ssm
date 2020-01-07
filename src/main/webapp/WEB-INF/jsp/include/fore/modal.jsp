<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%--非注册和登录页面时才存在该模态窗口，避免校验出错--%>
<c:if test="${requestScope['javax.servlet.forward.request_uri'].indexOf('/loginPage') == -1 && requestScope['javax.servlet.forward.request_uri'].indexOf('/registerPage') == -1}">
    <script>
        $(validLoginForm());
        $(function () {
            $("#loginBtn").click(function () {
                if (checkCaptcha() && validLoginForm().form()) {
                    $.ajax({
                        url: "/foreLogin",
                        data: {
                            userName: encodeAesString($("#userName").val()),
                            password: encodeAesString($("#password").val())
                        },
                        type: "post",
                        success: function (result) {
                            if (result == "success") {
                                window.location.reload();
                            }

                            if (result == "fail") {
                                toastr.warning("用户名或密码错误,请重新输入!");
                                changeCaptcha();
                            }
                        }
                    })
                }
            });
        });
    </script>


    <div class="modal " id="loginModal" tabindex="-1" role="dialog">
        <div class="modal-dialog loginDivInProductPageModalDiv">
            <div class="modal-content">
                <form method="post" id="loginForm">
                    <div class="loginDivInProductPage">
                        <div class="loginErrorMessageDiv">
                            <div class="alert alert-danger">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                                <span class="errorMessage"></span>
                            </div>
                        </div>

                        <div class="login_acount_text">账户登录</div>
                        <div class="loginInput ">
							<span class="loginInputIcon ">
								<span class=" glyphicon glyphicon-user"></span>
							</span>
                            <input id="userName" name="userName" placeholder="手机/会员名/邮箱" type="text">
                        </div>

                        <div class="loginInput ">
							<span class="loginInputIcon ">
								<span class=" glyphicon glyphicon-lock"></span>
							</span>
                            <input id="password" name="password" type="password" placeholder="密码"
                                   type="text">
                        </div>
                            <%--                        <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>--%>
                        <input type="text" placeholder="请输入验证码（区分大小写）"
                               style="height: 30px; position: relative;top: -11px; font-size: 12px;" id="captcha">
                        <canvas id="captcha$img" width="100" height="30"
                                style="border: 1px solid #ccc; border-radius: 5px;" onclick="changeCaptcha()"></canvas>
                        <div>
                            <a href="#nowhere">忘记登录密码</a>
                            <a href="registerPage" class="pull-right">免费注册</a>
                        </div>
                        <div style="margin-top:20px">
                            <button class="btn btn-block redButton loginSubmitButton" type="button" id="loginBtn">登录
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>


<div class="modal" id="deleteConfirmModal" tabindex="-1" role="dialog">
    <div class="modal-dialog deleteConfirmModalDiv">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">确认删除？</h4>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                <button class="btn btn-primary deleteConfirmButton" id="submit" type="button">确认</button>
            </div>
        </div>
    </div>
</div>
</div>