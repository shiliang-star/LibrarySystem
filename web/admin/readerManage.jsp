<%--
  Created by IntelliJ IDEA.
  User: 19655
  Date: 2020/3/18
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath =
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>读者管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap-admin-theme.css">

    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/ajaxutils.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap-dropdown.min.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdateInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdatePassword.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/getReader.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdateInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/adminUpdatePassword.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/updateReader.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/addReader.js"></script>


    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/getAllReaderTypes.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/batchAddReader.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/readermanage/exportReader.js"></script>


    <script src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/ajax_upload.js"></script>



    <script src="${pageContext.request.contextPath}/static/js/admin/deleteReader.js"></script>
    <script type="text/javascript">
        //js 获取域中的值
        var readerType = "${query.readerTypeId}";
    </script>

</head>

<body class="bootstrap-admin-with-small-navbar">
<nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small"
     role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="collapse navbar-collapse main-navbar-collapse">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/admin.jsp"><strong>欢迎使用图书馆管理系统</strong></a>
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
                <li>
                    <a href="${pageContext.request.contextPath}/admin/adminManageController_list.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 管理员管理</a>
                </li>
                </c:if>
                <li class="active">
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
                                  action="${pageContext.request.contextPath}/admin/readerManageController_list.action"
                                  method="post">
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">证件号</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" placeholder="请输入读者证件号" id="readerId" type="text" value="${query.paperNo}" name="paperNo">
                                        <label class="control-label" for="readerId" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">姓名</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="readerName" placeholder="请输入读者姓名"  name="readerName" type="text" value="${query.readerName}">
                                        <label class="control-label" for="readerName" style="display: none;"></label>
                                    </div>
                                </div>


                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">读者类型</label>
                                    <div class="col-lg-8">
                                        <select class="form-control" id="readerType" name="readerTypeId">
                                            <option value="-1">请选择</option>

                                        </select>

                                    </div>
                                </div>

                                <div class="col-lg-1 form-group"></div>

                                <div class="col-lg-2 form-group">
                                    <button type="submit" class="btn btn-primary" id="btn_query" onclick="query()">查询
                                    </button>

                                </div>

                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary"
                                            id="btn_add"
                                    >添加读者
                                    </button>
                                </div>

                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary" data-toggle="modal"
                                            data-target="#batchAddModal">批量添加
                                    </button>
                                </div>

                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary" onclick="exportReader()">导出</button>
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
                            <th>读者证件号</th>
                            <th>读者姓名</th>
                            <th>读者类型</th>
                            <th>联系号码</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <c:choose>
                            <c:when test="${pb.list != null }">
                                <c:forEach items="${pb.list }" var="reader">
                                    <tbody>
                                    <td>${reader.paperNo}</td>
                                    <td>${reader.readerName}</td>
                                    <td>${reader.readerTypeName}</td>
                                    <td>${reader.phone}</td>
                                    <td>${reader.createTime}</td>
                                    <td>
                                        <button type="button" class="btn btn-info btn-xs" data-toggle="modal"
                                                data-target="#findModal"
                                                onclick="getReaderInfo(${reader.id})">查看
                                        </button>
                                        <button type="button" class="btn btn-warning btn-xs" data-toggle="modal"
                                                data-target="#updateModal"
                                                onclick="updateReader(${reader.id})">修改
                                        </button>
                                        <button type="button" class="btn btn-danger btn-xs"
                                                onclick="deleteReader(${reader.id})">删除
                                        </button>
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
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                </tbody>
                            </c:otherwise>
                        </c:choose>
                    </table>
                    <script type="text/javascript">
                        function page(currentPage) {
                            window.location.href = "${pageContext.request.contextPath}/admin/readerManageController_list.action?currentPage=" + currentPage+
                                "&paperNo=${query.paperNo}&readerName=${query.readerName}&readerTypeId=${query.readerTypeId}";
                        }
                    </script>

                    <jsp:include page="../share/page.jsp"/>
                </div>
            </div>


        </div>


        <!-------------------------------------批量添加的模糊框------------------------>
        <!--保证样式水平不混乱-->
        <!-- 模态框（Modal） -->
        <div class="modal fade form-horizontal" id="batchAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            批量添加新读者
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/admin/fileUploadController_fileUpload.action" id="uploadForm" method="post" enctype="multipart/form-data">
                            <!---------------------表单-------------------->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">下载模板</label>
                                <div class="col-sm-7" style="padding-top: 7px">
                                    <a href="${pageContext.request.contextPath}/fileDownloadController_fileDownload.action?fileType=2&fileName=reader.xlsx">点击下载</a><br/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">上传文件</label>
                                <div class="col-sm-7">
                                    <label for="upload"></label>
                                    <!--为了jquery获得basePath的值，必须写（如果没有更好的办法） -->
                                    <input type="hidden" value="<%=basePath%>" id="basePath"/>
                                    <input type="hidden" id="excel"/>
                                    <!--id是给jquery使用的，name是给后台action使用的，必须和后台的名字相同！！ -->
                                    <input type="file" id="upload" name="upload"/><br/>
                                    <label class="control-label" for="upload" style="display: none;"></label>

                                </div>
                            </div>
                        </form>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary" id="batchAdd">
                            添加
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>


        <!--------------------------------------添加的模糊框------------------------>


        <!--------------------------------------添加的模糊框------------------------>
        <form class="form-horizontal">   <!--保证样式水平不混乱-->
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title">
                                添加新读者
                            </h4>
                        </div>
                        <div class="modal-body">

                            <!---------------------表单-------------------->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者证件号</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control"  id="addPaperNO"
                                           placeholder="请输入读者证件号">
                                    <label class="control-label" for="addPaperNO" style="display: none;"></label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">真实姓名</label>
                                <div class="col-sm-7">
                                    <input type="text"  class="form-control" id="addName"
                                           placeholder="请输入读者真实姓名">
                                    <label class="control-label" for="addName" style="display: none;"></label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系电话</label>
                                <div class="col-sm-7">
                                    <input type="text"  class="form-control" id="addPhone"
                                           placeholder="请输入读者联系电话">
                                    <label class="control-label" for="addPhone" style="display: none;"></label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">邮箱</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="addEmail"
                                           placeholder="请输入读者邮箱">
                                    <label class="control-label" for="addEmail" style="display: none;"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者类型</label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="addreaderType">
                                        <option value="-1">请选择</option>
                                    </select>
                                    <label class="control-label" for="addreaderType" style="display: none;"></label>
                                </div>
                            </div>

                            <!---------------------表单-------------------->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="addReader">
                                添加
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

        </form>
        <!--------------------------------------添加的模糊框------------------------>


        <!-- 修改模态框（Modal） -->
        <!-------------------------------------------------------------->

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
                                修改读者信息
                            </h4>
                        </div>
                        <div class="modal-body">

                            <!---------------------表单-------------------->

                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者证件号</label>
                                <div class="col-sm-7">
                                    <input type="hidden" id="updateReaderID">
                                    <input type="text" class="form-control" id="updatePaperNO" placeholder="请输入读者证件号" disabled>
                                    <label class="control-label" for="updatePaperNO" style="display: none;"></label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">真实姓名</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="updateName" placeholder="请输入读者真实姓名">
                                    <label class="control-label" for="updateName" style="display: none;"></label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系电话</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="updatePhone" placeholder="请输入读者联系电话">
                                    <label class="control-label" for="updatePhone" style="display: none;"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">邮箱</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="updateEmail" placeholder="请输入读者邮箱">
                                    <label class="control-label" for="updateEmail" style="display: none;"></label>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者类型</label>
                                <div class="col-sm-7">
                                    <select class="form-control" id="updateReaderType">
                                        <option value="-1">请选择</option>
                                    </select>
                                    <label class="control-label" for="updateReaderType" style="display: none;"></label>
                                </div>
                            </div>


                            <!---------------------表单-------------------->

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="button" class="btn btn-primary" id="updateReader">
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
                            <h4 class="modal-title">
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
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
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
                                           value='${sessionScope.admin.username}'>
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
                                           value='${sessionScope.admin.phone}'>
                                    <label class="control-label" for="phone" style="display: none;"></label>
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
                        <button type="button" class="btn btn-default" id="btn_info_close" data-dismiss="modal">关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <!--------------------------------------查看的模糊框------------------------>
        <form class="form-horizontal">   <!--保证样式水平不混乱-->
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="findModal" tabindex="-1" role="dialog" aria-labelledby="findModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="findModalLabel">
                                查看读者信息
                            </h4>
                        </div>
                        <div class="modal-body">

                            <!---------------------表单-------------------->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者证件号</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findPaperNO" readonly="readonly">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者姓名</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findReaderName" readonly="readonly">

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">读者类型</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findReaderType" readonly="readonly">

                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系号码</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findPhone" readonly="readonly">

                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-3 control-label">邮箱</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findEmail" readonly="readonly">

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">操作管理员</label>
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="findAdmin" readonly="readonly">

                                </div>
                            </div>


                            <!---------------------表单-------------------->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

        </form>
        <!--------------------------------------查看的模糊框------------------------>

    </div>
</div>
</body>
</html>
