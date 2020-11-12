<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <link rel="stylesheet" href="${APP_PATH}/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
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
                <%--                <jsp:include page="../common/menu.jsp"></jsp:include>--%>
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 权限菜单列表</h3>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH}/ztree/jquery.ztree.all-3.5.min.js"></script>
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

        var setting={};
        $.ajax({
            url:"${APP_PATH}/permission/loadData.do",
            type:"POST",
            beforeSend:function () {
                return true;
            },
            success:function (result) {
                if(result.success){
                    var zNodes=result.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                }else{
                    alert("not ok");
                }
            }
        })

    });

    $("tbody .btn-success").click(function(){
        window.location.href = "assignRole.html";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
    });

    /*    var setting = {	};

  /*var zNodes =[
          { name:"父节点1 - 展开", open:true,
              children: [
                  { name:"父节点11 - 折叠",
                      children: [
                          { name:"叶子节点111"},
                          { name:"叶子节点112"},
                          { name:"叶子节点113"},
                          { name:"叶子节点114"}
                      ]},
                  { name:"父节点12 - 折叠",
                      children: [
                          { name:"叶子节点121"},
                          { name:"叶子节点122"},
                          { name:"叶子节点123"},
                          { name:"叶子节点124"}
                      ]},
                  { name:"父节点13 - 没有子节点", isParent:true}
              ]},
          { name:"父节点2 - 折叠",
              children: [
                  { name:"父节点21 - 展开", open:true,
                      children: [
                          { name:"叶子节点211"},
                          { name:"叶子节点212"},
                          { name:"叶子节点213"},
                          { name:"叶子节点214"}
                      ]},
                  { name:"父节点22 - 折叠",
                      children: [
                          { name:"叶子节点221"},
                          { name:"叶子节点222"},
                          { name:"叶子节点223"},
                          { name:"叶子节点224"}
                      ]},
                  { name:"父节点23 - 折叠",
                      children: [
                          { name:"叶子节点231"},
                          { name:"叶子节点232"},
                          { name:"叶子节点233"},
                          { name:"叶子节点234"}
                      ]}
              ]},
          { name:"父节点3 - 没有子节点", isParent:true}

      ];

      $(document).ready(function(){
          $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      });*/


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
