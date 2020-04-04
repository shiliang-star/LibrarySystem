<%--
  Created by IntelliJ IDEA.
  User: 19655
  Date: 2020/3/7
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html lang="zh-CN" class="ax-vertical-centered">
<head>
    <meta charset="UTF-8">
    <title>管理员主页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">

    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap-dropdown.min.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdateInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdatePassword.js"></script>
</head>


<body class="bootstrap-admin-with-small-navbar">
<nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small"
     role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="collapse navbar-collapse main-navbar-collapse">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/index.jsp"><strong>欢迎使用爱十元图书馆管理系统</strong></a>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> <i
                                    class="glyphicon glyphicon-user"></i> 欢迎您，${sessionScope.admin.name }
                                <i class="caret"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="#updateinfo" data-toggle="modal">个人资料</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="#updatepwd" data-toggle="modal">修改密码</a></li>
                                <li role="presentation" class="divider"></li>
                                <!-- href="#identifier"  来指定要切换的特定的模态框（带有 id="identifier"）。-->
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

<%--<shiro:authenticated>--%>
<%--我是认证后才显示--%>
<%--</shiro:authenticated>--%>

<%--<shiro:hasPermission name="superSet">--%>
<%--我拥有超级管理员权限--%>
<%--</shiro:hasPermission>--%>

<div class="container">
    <div class="row">
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
                            class="glyphicon glyphicon-chevron-right"></i>逾期处理</a>
                </li>
                <c:if test="${sessionScope.admin.authorization.superSet == 1}"><!-- 对超级管理员和普通管理员进行权限区分 -->
<%--                <shiro:hasPermission name="superSet">--%>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/adminManageController_list.action"><i
                                class="glyphicon glyphicon-chevron-right"></i> 管理员管理</a>
                    </li>
<%--                </shiro:hasPermission>--%>
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

        <div class="col-md-10">
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">图书管理</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>根据图书编号、图书名称查询图书基本信息</li>
                                <li>添加、修改、删除图书</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">图书分类管理</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>根据分类名称查询图书分类信息</li>
                                <li>添加、修改、删除图书分类</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">图书借阅</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>根据学号、图书编号借阅图书</li>
                                <li>展示此学号的借阅信息</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">图书归还</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>根据学号、图书编号归还图书</li>
                                <li>展示此学号的借阅信息</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">借阅查询</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>展示所有学生的图书借阅信息</li>
                                <li>可根据图书编号、图书名称、学号、姓名进行查询</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">读者管理</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>根据学号、姓名查询学生基本信息</li>
                                <li>添加、修改、删除学生信息</li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">逾期处理</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>逾期处理</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">系统设置</div>
                        </div>
                        <div class="bootstrap-admin-panel-content">
                            <ul>
                                <li>系统设置</li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
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
                                <label class="control-label" for="oldPwd" style="display: none;"></label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">新密码</label>
                            <div class="col-sm-7">
                                <input type="password" class="form-control" id="newPwd" placeholder="请输入新密码">
                                <label class="control-label" for="newPwd" style="display: none;"></label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">确认密码</label>
                            <div class="col-sm-7">
                                <input type="password" class="form-control" id="confirmPwd" placeholder="请输入确认密码">
                                <label class="control-label" for="confirmPwd" style="display: none;"></label>
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
                </div>
            </div>
        </div>

    </form>
    <!-------------------------------------------------------------->


    <!-------------------------个人资料模糊框------------------------------------->

    <form class="form-horizontal">   <!--保证样式水平不混乱-->
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="updateinfo" tabindex="-1" role="dialog" aria-labelledby="ModalLabel"
             aria-hidden="true">
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
                                       value='${sessionScope.admin.username} '>
                                <label class="control-label" for="username" style="display: none;"></label>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-3 control-label">真实姓名</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="name" placeholder="请输入您的真实姓名"
                                       value='${sessionScope.admin.name}'>
                                <label class="control-label" for="name" style="display: none;"></label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">联系号码</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="phone" placeholder="请输入您的联系号码"
                                       value='${sessionScope.admin.phone} '>
                                <label class="control-label" for="phone" style="display: none;"></label>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" id="admin_updateInfo">
                            修改
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </form>
    <!-------------------------------------------------------------->



    <div class="modal fade" id="modal_info" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
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
</div>
</body>
</html>
