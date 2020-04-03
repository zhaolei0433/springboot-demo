<%--
  @author zhaolei
  Create: 2019/11/18 16:58
  Modified By:
  Description: 登录页
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <jsp:include page="inc.jsp"></jsp:include>
    <title>EasyUI 用户登录</title>
    <style>
        body {
            padding: 0px;
            margin: 0px auto;
            text-align: center;
            background: url(images/login.png);
            color: #706E6E;
        }

        .btn {
            float: right;
            width: 85px;
            height: 35px;
            text-align: center;
            line-height: 35px;
            font-size: 14px;
            font-weight: bold;
            cursor: pointer;
            background-color: #eee;
            border: 1px solid #C9C7C7;
            margin-top: 13px;
            margin-right: 110px;
        }

        .btn:hover {
            background-color: #37BCED;
            color: #fff;
        }

        #loginbox {
            position: absolute;
            top: 200px;
            left: 32%;
            width: 502px;
            height: 349px;
            text-align: right;
            background: url(images/output.png) no-repeat;
        }

        .warning {
            color: #f00;
            position: absolute;
            top: 250px;
            left: 187px;
            font-size: 13px;
        }
    </style>
</head>
<body>
<div id="loginbox">
    <form method="post" id="user_login_form" action="user_login">
        <div style="margin-top: 135px; margin-left:108px;">
            <table width="300px" height="120px">
                <tr width="50%">
                    <td width="30%">
                        用户名：
                    </td>
                    <td width="70%">
                        <div>
                            <input id="username" name="username" class="easyui-textbox" prompt="Username" iconWidth="28"
                                   style="width:100%;height:34px;padding:10px;">
                        </div>
                    </td>
                </tr>
                <tr width="50%">
                    <td width="30%">
                        密&nbsp;码：
                    </td>
                    <td width="70%">
                        <div>
                            <input id="password" name="password" class="easyui-passwordbox" prompt="Password" iconWidth="28"
                                   style="width:100%;height:34px;padding:10px" align="left">
                        </div>
                    </td>
                </tr>
            </table>
            <div class="warning" style="padding-left:40px">
                ${login_error}
            </div>
        </div>
        <div>
            <div id="cancelBtn" class="btn">取&nbsp;&nbsp;消</div>
            <div id="loginBtn" class="btn" style="margin-right:40px;">登&nbsp;&nbsp;录</div>
        </div>
    </form>
</div>

<!-- <div class="foot"><p class="first">版权所有 © 1999-2014 深圳市茁壮网络股份有限公司　粤ICP备12069260</p><p>Copyright © 1999-2014 iPanel.TV Inc.,All Rights Reserved</p></div> -->
</body>
</html>
<script>
    $('#loginBtn').click(function () {
        $('#loginBtn').css('color', '#ffffff');
        $('#cancelBtn').css('color', '#000000');
        $('#loginBtn').css('background-image', 'url(images/bg_button1.png)');
        $('#cancelBtn').css('background-image', 'url(images/bg_button0.png)');
        var result = $('#user_login_form').form('validate');
        if (!result) {
            return false;
        }
        $("#user_login_form").submit();
    });

    $('#cancelBtn').click(function () {
        $("input[name='loginUserName']").val("");
        $("input[name='loginPassword']").val("");
        $('#loginBtn').css('color', '#000000');
        $('#cancelBtn').css('color', '#ffffff');
        $('#loginBtn').css('background-image', 'url(images/bg_button0.png)');
        $('#cancelBtn').css('background-image', 'url(images/bg_button1.png)');
    });

    $('#loginbox input').click(function () {
        $(this).css("background", "none");
    });

    $(document).keydown(function (event) {
        event = event || window.event;
        if (event.keyCode == 13) {
            var result = $('#user_login_form').form('validate');
            if (!result) {
                return false;
            }
            $("#user_login_form").submit();
        }
    });
</script>