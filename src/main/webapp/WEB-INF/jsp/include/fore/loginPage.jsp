<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    //注册表单验证
    $(validLoginForm());

    $(function () {
        $(document).keydown(function(event){
            if(event.keyCode == 13){
                $("#loginBtn").click();
            }
        });

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
                            window.location.href = "/foreHome";
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


<div id="loginDiv" style="position: relative">

    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>

    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">

    <%--	action="forelogin"--%>
    <form class="loginForm" method="post" id="loginForm">
        <div id="loginSmallDiv" class="loginSmallDiv">
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
                <input id="password" name="password" type="password" placeholder="密码" type="text">
            </div>
            <%--            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>--%>
            <input type="text" placeholder="请输入验证码（区分大小写）"
                   style="height: 30px; position: relative;top: -11px; font-size: 12px;" id="captcha">
            <canvas id="captcha$img" width="100" height="30"
                    style="border: 1px solid #ccc; border-radius: 5px;" onclick="changeCaptcha()"></canvas>
            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="button" id="loginBtn">登录</button>
            </div>
        </div>
    </form>
</div>