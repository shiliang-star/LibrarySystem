<%--
  Created by IntelliJ IDEA.
  User: 19655
  Date: 2020/3/21
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap-dropdown.min.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/ajaxutils.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdateInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdatePassword.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/adminmanage/addAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminmanage/updateAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminmanage/deleteAdmin.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminmanage/updateAuthorization.js"></script>


</head>
<body class="bootstrap-admin-with-small-navbar">
<nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small"
     role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="collapse navbar-collapse main-navbar-collapse">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/index.jsp"><strong>欢迎使用图书馆管理系统</strong></a>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> <i
                                    class="glyphicon glyphicon-user"></i> 欢迎您， ${sessionScope.admin.name}
                                <i class="caret"></i></a>

                            <ul class="dropdown-menu">
                                <li><a href="#updateinfo" data-toggle="modal">个人资料</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="#updatepwd" data-toggle="modal">修改密码</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/adminLoginController_logout.action">退出</a>
                                </li>
                            </ul>

                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="container">
    <!-- left, vertical navbar & content -->
    <div class="row">
        <!-- left, vertical navbar -->
        <div class="col-md-2 bootstrap-admin-col-left">
            <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/bookManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 图书管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/bookTypeManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 图书分类管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/borrowManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 图书借阅</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/backManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 图书归还</a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/admin/borrowSearchController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 借阅查询</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/overDueManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 逾期处理</a>
                </li>
                <c:if test="${sessionScope.admin.authorization.superSet==1}"><!-- 对超级管理员和普通管理员进行权限区分 -->
                <li class="active">
                    <a href="${pageContext.request.contextPath}/admin/adminManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 管理员管理</a>
                </li>
                </c:if>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/readerManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 读者管理</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/readerTypeManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 系统设置</a>
                </li>

            </ul>
        </div>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">查询</div>
                        </div>
                        <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal"
                                  action="${pageContext.request.contextPath}/admin/adminManageController_list.action"
                                  method="post">
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">管理员用户名</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" placeholder="请输入管理员用户名" id="adminUserName" value="${query.userName}" name="userName">
                                        <label class="control-label" for="adminUserName" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">管理员名称</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" type="text" placeholder="请输入管理员名称" id="adminName" value="${query.name}" name="name">
                                        <label class="control-label" for="adminName" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-2 form-group">
                                    <button type="submit" class="btn btn-primary" id="btn_query" onclick="query()">查询
                                    </button>
                                    <button type="button" class="btn btn-primary" id="btn_add" data-toggle="modal"
                                            data-target="#addModal">添加
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <table id="data_list" class="table table-hover table-bordered" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>管理员用户名</th>
                            <th>管理员姓名</th>
                            <th>联系号码</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <c:choose>
                            <c:when test="${pb.list != null }">
                                <c:forEach items="${pb.list }" var="admin">
                                    <tbody>
                                    <td>${admin.userName}</td>
                                    <td>${admin.name}</td>
                                    <td>${admin.phone}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${admin.authorizationDo.superSet!=1}">
                                                <button type="button" class="btn btn-warning btn-xs" data-toggle="modal"
                                                        data-target="#updateModal"
                                                        onclick="updateAdmin(${admin.id})">修改
                                                </button>
                                                <button type="button" class="btn btn-danger btn-xs"
                                                        onclick="deleteAdmin(${admin.id})">删除
                                                </button>
                                                <button type="button" class="btn btn-success btn-xs" data-toggle="modal"
                                                        data-target="#powerModal"
                                                        onclick="power(${admin.id})">权限
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                超级管理员
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    </tbody>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tbody>
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                </tbody>
                            </c:otherwise>
                        </c:choose>
                    </table>
                    <script type="text/javascript">
                        function page(currentPage) {
                            window.location.href = "${pageContext.request.contextPath}/admin/adminManageController_list.action?currentPage=" + currentPage+
                                "&userName=${query.userName}&name=${query.name}";
                        }
                    </script>
                    <jsp:include page="../share/page.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
<!--------------------------------------添加的模糊框------------------------>
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        添加新管理员
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control"  id="addUsername" placeholder="请输入管理员用户名">
                            <label class="control-label" for="addUsername" style="display:none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">真实姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control"  id="addName" placeholder="请输入管理员真实姓名">
                            <label class="control-label" for="addName" style="display:none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系电话</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="addPhone"  placeholder="请输入管理员联系电话">
                            <label class="control-label" for="addPhone" style="display:none;"></label>
                        </div>
                    </div>
                    <!---------------------表单-------------------->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="addAdmin">
                        添加
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</form>
<!--------------------------------------添加的模糊框------------------------>


<!-- 修改模态框（Modal） -->
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="updateModalLabel">
                        修改管理员信息
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->

                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-7">
                            <input type="hidden" id="updateId">
                            <input type="text" class="form-control" id="updateUsername" >
                            <label class="control-label" for="updateUsername" style="display:none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">真实姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updateName" placeholder="请输入管理员真实姓名">
                            <label class="control-label" for="updateName" style="display:none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系电话</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updatePhone" placeholder="请输入管理员联系电话">
                            <label class="control-label" for="updatePhone" style="display:none;"></label>
                        </div>
                    </div>


                    <!---------------------表单-------------------->

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="updateAdmin">
                        修改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</form>
<!-------------------------------------------------------------->


<!------------------------------修改密码模糊框-------------------------------->
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="updatepwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改密码
                    </h4>
                </div>

                <div class="modal-body">

                    <!--正文-->
                    <div class="form-group">
                        <label class="col-sm-3 control-label">原密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="oldPwd" placeholder="请输入原密码">
                            <label class="control-label" for="oldPwd" style="display:none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">新密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="newPwd" placeholder="请输入新密码">
                            <label class="control-label" for="newPwd" style="display:none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">确认密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="confirmPwd" placeholder="请输入确认密码">
                            <label class="control-label" for="confirmPwd" style="display:none;"></label>
                        </div>
                    </div>
                    <!--正文-->


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="update_adminPwd">
                        修改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</form>
<!-------------------------------------------------------------->


<!-------------------------个人资料模糊框------------------------------------->
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="updateinfo" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="ModalLabel">
                        个人资料
                    </h4>
                </div>

                <div class="modal-body">

                    <!--正文-->
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="username" disabled
                                   value='${sessionScope.admin.username}'>
                            <label class="control-label" for="username" style="display:none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">真实姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="name" placeholder="请输入您的真实姓名"
                                   value='${sessionScope.admin.name}'>
                            <label class="control-label" for="name" style="display:none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系号码</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="phone" placeholder="请输入您的联系号码"
                                   value='${sessionScope.admin.phone}'>
                            <label class="control-label" for="phone" style="display:none;"></label>
                        </div>
                    </div>

                    <!--正文-->


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="admin_updateInfo">
                        修改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</form>
<!-------------------------------------------------------------->


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


<!-- 权限模态框（Modal） -->
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <div class="modal fade" id="powerModal" tabindex="-1" role="dialog" aria-labelledby="powerModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="powerModalLabel">
                        设置管理员权限
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书分类管理权限</label>
                        <div class="col-sm-7">
                            <input type="hidden" id="aid">
                            <input type="checkbox" name="power" id="typeSet" value="typeSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书管理权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="bookSet" value="bookSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">读者管理权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="readerSet" value="readerSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">借阅管理权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="borrowSet" value="borrowSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">归还管理权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="backSet" value="backSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">逾期管理权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="forfeitSet" value="forfeitSet">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">系统设置权限</label>
                        <div class="col-sm-7">
                            <input type="checkbox" name="power" id="sysSet" value="sysSet">
                        </div>
                    </div>


                    <!---------------------表单-------------------->

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="setPower()">
                        设置
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

</form>
<!-------------------------------------------------------------->

</body>
</html>
