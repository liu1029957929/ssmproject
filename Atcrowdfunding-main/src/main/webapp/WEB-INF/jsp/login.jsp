<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct" value="superadmin" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" class="form-control" id="fuserpswd" value="123" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" id="ftype">
                <option value="member">会员</option>
                <option value="user" selected>管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="login()"> 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>

<script>

    $(function () {

        //键盘回车绑定事件
        $(window).keydown(function (event) {
            if(event.keyCode==13){
                login();
            }
        })
    })

    //登录
    function login() {
        var floginacct = $("#floginacct");
        var fuserpswd = $("#fuserpswd");
        var ftype = $("#ftype");

        if($.trim(floginacct.val())==""){
            layer.msg("用户名不能为空", {time:1000, icon:5, shift:6});
            //将光标放置在该文本框
            floginacct.focus();
            floginacct.val("");
            return false;
        }

        if($.trim(fuserpswd.val())==""){
            layer.msg("密码不能为空", {time:1000, icon:5, shift:6});
            //将光标放置在该文本框
            fuserpswd.focus();
            fuserpswd.val("");login.jsp
            return false;
        }

        var type = $.trim(ftype.val())
        if(type=="member"){
            window.location.href="${APP_PATH}/member/doLogin.do";
        }else if(type=="user"){

            var loadingIndex=-1;
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/dologin.do",
                data:{
                    "loginacct":$.trim(floginacct.val()),
                    "userpswd":$.trim(fuserpswd.val()),
                    "type":$.trim(ftype.val())
                },
                beforeSend:function () {
                    loadingIndex = layer.msg("登录中", {icon: 16});
                    return true;
                },
                success:function (result) {
                    layer.close(loadingIndex);
                    if(result.success){
                        window.location.href="${APP_PATH}/main.htm";
                    }else{
                        layer.msg(result.message, {time:1000, icon:5, shift:6});
                    }
                },
                error:function (result) {
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            })
        }else{
            layer.msg(result.message, {time:1000, icon:5, shift:6});
        }
    }


</script>
</body>
</html>