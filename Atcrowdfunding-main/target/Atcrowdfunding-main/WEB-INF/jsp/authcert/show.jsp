<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">用户信息</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <div class="form-group">
                        <div class="form-group">
                            <p><b>真实名称</b></p>${member.realname}
                        </div>
                        <div class="form-group">
                            <p><b>身份证号码</b></p>${member.cardnum}
                        </div>
                        <div class="form-group">
                            <p><b>电话号码</b></p>${member.tel}
                        </div>
                        <hr>
                        <c:forEach  items="${mclist}" var="certImg">
                            <div class="form-group">
                                <p><b>${certImg.name}</b></p>
                                <img src="${APP_PATH}/pics/apply/${certImg.iconpath}">
                            </div>
                            <hr>
                        </c:forEach>
                        <button id="passBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 审核通过</button>
                        <button id="refuseBtn" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 审核拒绝</button>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        showMenu();
    })

    $("#passBtn").click(function () {
        var loadingIndex=-1;
        $.ajax({
            url:"${APP_PATH}/authcert/pass.do",
            type:"POST",
            data:{
                "memberid":"${member.id}",
                "taskid":"${param.taskid}"
            },
            beforeSend:function () {
                loadingIndex = layer.msg("加载中", {icon: 16});
                return true;
            },
            success:function (result) {
                layer.close(loadingIndex);
                if(result.success){
                    window.location.href="${APP_PATH}/authcert/index.htm";
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }

            },
            error:function (result) {
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        })
    })


    function showMenu() {
        //因为是内部跳转，所以拿到的地址是内部地址。
        //alert("${pageContext.request.requestURI}");/Atcrowdfunding/WEB-INF/jsp/user/index.jsp

        //获取当前页面的地址
        var addr = window.location.href;//http://127.0.0.1:8080/Atcrowdfunding/user/index.htm
        //获取当前页面的ip
        var ip = window.location.host;//127.0.0.1:8080
        var contextPath = "${APP_PATH}";
        //当前页面的URI
        var URI = addr.substring(ip.length+contextPath.length+7);//  /user/index.htm

        var a = $(".list-group a[href*='"+URI+"']");
        //将对应的a标签赋上颜色
        a.css("color","red");
        //控制关闭
        a.parent().parent().parent().removeClass("tree-closed");
        a.parent().parent().show();

    }

</script>
</body>
</html>
