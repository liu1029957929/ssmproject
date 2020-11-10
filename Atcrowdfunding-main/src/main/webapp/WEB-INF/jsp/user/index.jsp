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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" id="searchCondition" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" onclick="deleteUserBatch()" ><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/user/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="qx"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="activitybody">

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination" id="paginationId">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
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
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
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

        //展示列表
        showPage(1,10);

        showMenu();

        //为条件查询绑定事件
        $("#searchBtn").click(function () {
            showPage();
        })

        //单选和全选
        $("#qx").click(function () {
            $("input[name='xz']").prop("checked",this.checked);
            //$("tbody tr td input[type=checkbox]").prop("checked",this.checked);
        })

        $("#activitybody").on("click",$("input[name='xz']"),function () {
            $("#qx").prop("checked",$("input[name='xz']").length==$("input[name='xz']:checked").length);
        })

    });

    $("tbody .btn-success").click(function(){
        window.location.href = "assignRole.html";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
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


    function showPage(pageno,pagesize) {
        var loadingIndex=-1;
        var searchCondition=$.trim($("#searchCondition").val());

        $.ajax({
            type:"POST",
            url:"${APP_PATH}/user/doIndex.do",
            data:{
                "pageno":pageno,
                "pagesize":pagesize,
                "searchCondition":searchCondition
            },
            beforeSend:function () {
                loadingIndex = layer.msg("查询中", {icon: 16});
                return true;
            },
            success:function (result) {
                layer.close(loadingIndex);

                var page = result.page;
                var userlist = page.userlist;
                var param="";

                //用户信息列表
                $.each(userlist,function (i,n) {
                    param+='<tr>';
                    param+='<td>'+i+'</td>';
                    param+='<td><input type="checkbox" name="xz" value="'+n.id+'"></td>';
                    param+='<td>'+n.loginacct+'</td>';
                    param+='<td>'+n.username+'</td>';
                    param+='<td>'+n.email+'</td>';
                    param+='<td>';
                    param+='<button type="button" class="btn btn-success btn-xs" onclick="toAssignRole('+n.id+')"><i class=" glyphicon glyphicon-check"></i></button>';
                    param+='<button type="button" class="btn btn-primary btn-xs" onclick="toDelete('+n.id+')"><i class=" glyphicon glyphicon-pencil"></i></button>';
                    param+='<button type="button" class="btn btn-danger btn-xs" onclick="deleteUser('+n.id+')"><i class=" glyphicon glyphicon-remove"></i></button>';
                    param+='</td>';
                    param+='</tr>';
                })
                $("#activitybody").html(param);

                //分页
                var context="";
                if(pageno!=1){
                    context+='<li><a href="#" onclick="showPage('+(pageno-1)+')">上一页</a></li>';
                }else{
                    context+='<li class="disabled"><a href="#">上一页</a></li>';
                }

                for(var i = 1;i<=page.totalno;i++){
                    context+='<li';
                    if(i==pageno){
                        context+=' class="active"';
                    }
                    context+='><a href="#" onclick="showPage('+i+')">'+i+'</a></li>';
                }

                if(pageno!=page.totalno){
                    context+='<li><a href="#" onclick="showPage('+(pageno+1)+')">下一页</a></li>';
                }else{
                    context+='<li class="disabled"><a href="#">下一页</a></li>';
                }

                $("#paginationId").html(context);

            },
            error:function () {
                layer.msg("查询失败", {time:1000, icon:5, shift:6});
            }
        })
    }

    //删除用户列表（单条删除）
    function deleteUser(id) {
        var loadingIndex=-1;
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/user/doDelete.do",
            data:{
                "id":id
            },
            beforeSend:function () {
                loadingIndex = layer.msg("删除中", {icon: 16});
                return true;
            },
            success:function (result) {
                layer.close(loadingIndex);
                if(result.success){
                    //刷新页面
                    showPage(1,10);
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            },
            error:function (result) {
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        })
    }


    //删除用户列表（多条删除）
    function deleteUserBatch() {
        var loadingIndex=-1;
        var $xz=$("input[name=xz]:checked");
        //判断是否选择了一条或以上数据
        if($xz.length==0){
            layer.msg("请选择要删除的用户数据", {time:1000, icon:5, shift:6});
            return false;
        }
        var dataObj={};
        $.each($xz,function (i,n) {
            //这里的datalist要和controller中接收参数里面的变量一样
            dataObj["userlist["+i+"].id"]=n.value;
        })


        layer.confirm("确定要删除吗",  {icon: 3, title:'提示'}, function(cindex){
            layer.close(cindex);
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/doDeleteBatch.do",
                data:dataObj,
                beforeSend:function () {
                    loadingIndex = layer.msg("删除中", {icon: 16});
                    return true;
                },
                success:function (result) {
                    layer.close(loadingIndex);
                    if(result.success){
                        //刷新页面
                        showPage(1,10);
                    }else{
                        layer.msg(result.message, {time:1000, icon:5, shift:6});
                    }
                },
                error:function (result) {
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            })

        });
    }

    //同步请求，到修改页面，铺页面
    function toDelete(id) {
        window.location.href="${APP_PATH}/user/toEdit.htm?id="+id;
    }

    function toAssignRole(id) {
        window.location.href="${APP_PATH}/user/assignrole.htm?id="+id;
    }

</script>
</body>
</html>
