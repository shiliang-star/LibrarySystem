<%--
  Created by IntelliJ IDEA.
  User: 19655
  Date: 2020/3/8
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>欢迎登录图书馆管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.min.js" ></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-theme.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.js" ></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css" />
    <script src="${pageContext.request.contextPath}/static/js/admin/adminLogin.js"></script>

    <style type="text/css">
        .divForm{
            position: absolute;/*绝对定位*/
            width: 300px;
            height: 200px;


            text-align: center;/*(让div中的内容居中)*/
            top: 50%;
            left: 50%;
            margin-top: -300px;
            margin-left:-600px;
        }
    </style>

</head>
<body  class="bootstrap-admin-without-padding" >
<div class="divForm">
<div  class="container">
    <div class="row">
        <div class="col-lg-12">
            <div  class="alert alert-info" style="max-width: 400px;text-align: center;margin: 0 auto 20px;">
                管理员登陆
            </div>
            <!--表单提交-->
            <form class="bootstrap-admin-login-form" >
                <div class="form-group">
                    <label class="control-label" for="username">账&nbsp;号</label>
                    <input type="text" value="admin" class="form-control" id="username" placeholder="管理员用户名"/>
                    <label class="control-label" for="username" style="display:none;"></label>
                </div>
                <div class="form-group">
                    <label class="control-label" for="password">密&nbsp;码</label>
                    <input value="admin" type="password" class="form-control" id="password" placeholder="密码"/>
                    <label class="control-label" for="username" style="display:none;"></label>
                </div>
                <input type="button" class="btn btn-lg btn-primary" data-toggle="modal"  id="login_submit"
                       value="登&nbsp;&nbsp;&nbsp;&nbsp;录"/>
            </form>
        </div>
    </div>
</div>
</div>
<div class="modal fade" id="modal_info" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="infoModalLabel">提示</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12" id="div_info"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btn_info_close" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
