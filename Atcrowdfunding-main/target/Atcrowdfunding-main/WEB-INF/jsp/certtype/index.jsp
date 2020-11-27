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
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}

        input[type=checkbox] {
            width:18px;
            height:18px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 分类管理</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th>名称</th>
                                <th >商业公司</th>
                                <th >个体工商户</th>
                                <th >个人经营</th>
                                <th >政府及非营利组织</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${certList}" var="cert">
                                <tr>
                                    <td>${cert.name}</td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="0"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="1"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="2"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="3"></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

        showMenu();

        //回显打勾的数据
        <c:forEach items="${accountTypeCertList}" var="ac">
            $(":checkbox[certid='${ac.certid}'][accttype='${ac.accttype}']").attr("checked",true);
        </c:forEach>

        $(":checkbox").click(function () {
            var flag = this.checked;
            //获取点击的certid和accttype
            var certid=$(this).attr("certid");
            var accttype = $(this).attr("accttype");

            if(flag){
                $.ajax({
                    url:"${APP_PATH}/certtype/insertAccountTypeCert.do",
                    data:{
                        "certid":certid,
                        "accttype":accttype
                    },
                    success:function (result) {
                        if(result.success){

                        }else{
                            alert("not ok");
                        }
                    }

                })

            }else{
                $.ajax({
                    url:"${APP_PATH}/certtype/deleteAccountTypeCert.do",
                    data:{
                        "certid":certid,
                        "accttype":accttype
                    },
                    success:function (result) {
                        if(result.success){

                        }else{
                            alert("not ok");
                        }
                    }
                })
            }
        })

    });

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

