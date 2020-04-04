<%--
  Created by IntelliJ IDEA.
  User: 19655
  Date: 2020/3/12
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%
    String path = request.getContextPath();
    //request.getSchema()可以返回当前页面使用的协议，http 或是 https;
    //request.getServerName()可以返回当前页面所在的服务器的名字;
    //request.getServerPort()可以返回当前页面所在的服务器使用的端口,就是80;
    //request.getContextPath()可以返回当前页面所在的应用的名字;
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
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

    <script src="${pageContext.request.contextPath}/static/js/ajaxutils.js"></script>

    <script src="${pageContext.request.contextPath}/static/js/admin/getAllBookTypes.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/addBook.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/getBookInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/updateBook.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/addBookNum.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/deleteBook.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/exportBook.js"></script>

    <script src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/ajax_upload.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/admin/batchAddBook.js"></script>

    <script type="text/javascript">
        //js 获取域中的值
        var bookType = "${query.bookTypeId}";
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
                <li class="active">
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
                                  action="${pageContext.request.contextPath}/admin/bookManageController_list.action"
                                  method="post">
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">图书ISBN号</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="bookId" name="ISBN" type="text"
                                               value="${query.ISBN}" placeholder="请输入图书ISBN号">
                                        <label class="control-label" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">图书分类</label>
                                    <div class="col-lg-8">
                                        <select class="form-control" id="bookTypeId" name="bookTypeId">
                                            <option value="-1">请选择</option>
                                        </select>

                                    </div>
                                </div>

                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">图书名称</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="bookName" name="bookName" type="text"
                                               value="${query.bookName}" placeholder="请输入图书名称">
                                        <label class="control-label" style="display: none;"></label>
                                    </div>
                                </div>

                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">作者名称</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="author" placeholder="请输入作者名称"  name="author" type="text" value="${query.author}">
                                        <label class="control-label" style="display: none;"></label>
                                    </div>
                                </div>

                                <div class="col-lg-5 form-group">
                                    <label class="col-lg-4 control-label">出版社名称</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="press" placeholder="请输入出版社名称" name="press" type="text"
                                               value="${query.press}">
                                        <label class="control-label" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-2 form-group">
                                    <button type="submit" class="btn btn-primary" id="btn_query" onclick="query()">查询
                                    </button>
                                </div>
                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary" id="btn_add">添加图书
                                    </button>
                                </div>
                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary" data-toggle="modal"
                                            data-target="#batchAddModal">批量添加
                                    </button>
                                </div>
                                <div class="col-lg-2 form-group">
                                    <button type="button" class="btn btn-primary" onclick="exportBook()">导出</button>
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
                            <th>图书ISBN号</th>
                            <th>图书类型</th>
                            <th>图书名称</th>
                            <th>作者名称</th>
                            <th>出版社</th>
                            <th>上架日期</th>
                            <th>总数量</th>
                            <th>在馆数量</th>
                            <th>价格</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <c:choose>
                            <c:when test="${pb.list != null }">
                                <c:forEach items="${pb.list }" var="b">
                                    <!---在此插入信息-->
                                    <tbody>
                                    <td>${b.isbn}</td>
                                    <td>${b.booktype}</td>
                                    <td>${b.name }</td>
                                    <td>${b.author}</td>
                                    <td>${b.press }</td>
                                    <td><fmt:formatDate value="${b.putdate}"/></td>
                                    <td>${b.num}</td>
                                    <td>${b.currentNum}</td>
                                    <td>${b.price}</td>
                                    <td>
                                        <button type="button" class="btn btn-info btn-xs"
                                                data-toggle="modal" data-target="#findModal"
                                                onclick="getBookInfo(${b.id})">查看
                                        </button>
                                        <button type="button" class="btn btn-warning btn-xs"
                                                data-toggle="modal" data-target="#updateModal"
                                                id="btn_update" onclick="updateBook(${b.id})">修改
                                        </button>
                                        <button type="button" class="btn btn-danger btn-xs"
                                                onclick="deleteBook(${b.id})">删除
                                        </button>
                                        <button type="button" class="btn btn-success btn-xs"
                                                onclick="addBookNum(${b.id },${b.isbn })"
                                            <%-- data-toggle="modal" data-target="#addNumModal"--%>>新增
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
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                <td>暂无数据</td>
                                </tbody>
                            </c:otherwise>
                        </c:choose>
                    </table>
                    <script type="text/javascript">
                        function page(pageCode) {
                            window.location.href = "${pageContext.request.contextPath}/admin/bookManageController_list.action?currentPage=" + pageCode+
                            "&ISBN=${query.ISBN}&bookTypeId=${query.bookTypeId}&bookName=${query.bookName}&author=${query.author}&press=${query.press}";

                        }
                    </script>
                    <jsp:include page="../share/page.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>


<!-------------------------------------批量添加的模糊框------------------------>
<!--保证样式水平不混乱-->
<div class="modal fade form-horizontal" id="batchAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    批量添加新图书
                </h4>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/admin/fileUploadController_fileUpload.action" method="post"
                      enctype="multipart/form-data" id="uploadForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">下载模板</label>
                        <div class="col-sm-7" style="padding-top: 7px">
                            <a href="${pageContext.request.contextPath}/fileDownloadController_fileDownload.action?fileType=2&fileName=book.xlsx">点击下载</a><br/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">上传文件</label>
                        <div class="col-sm-7">
                            <label></label>
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
                        查看图书信息
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->
                    <div class="form-group">
                        <label class="col-sm-3 control-label">ISBN号</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findISBN" readonly="readonly">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findBookName" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书类型</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findBookType" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">作者名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findAuthor" readonly="readonly">

                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">出版社</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findPress" readonly="readonly">

                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">总数量</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findNum" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">当前数量</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findCurrentNum" readonly="readonly">

                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">价格</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findPrice" readonly="readonly">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">操作管理员</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="findAdmin" readonly="readonly">

                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">简介</label>
                        <div class="col-sm-7">
                            <textarea class="form-control" rows="3" id="findDescription" readonly="readonly"></textarea>
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


<!--------------------------------------增加图书数量的模糊框------------------------>
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="addNumModal" tabindex="-1" role="dialog" aria-labelledby="addNumModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="addNumModalLabel">
                        新增图书数量
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书ISBN号</label>
                        <div class="col-sm-7">
                            <input type="hidden" id="addBookNumId">
                            <input type="text" class="form-control" id="addBookNumISBN" readonly="readonly">
                            <label class="control-label" for="addBookNumISBN" style="display: none;"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">新增数量</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="addBookNum" placeholder="请输入新增的图书数量">
                            <label class="control-label" for="addBookNum" style="display: none;"></label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="add_BookNum">
                        新增
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>
<!--------------------------------------增加图书数量的模糊框------------------------>


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
                        添加新图书
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书ISBN号</label>
                        <div class="col-sm-7">
                            <input type="text" value="6666" class="form-control" id="addISBN" placeholder="请输入国际标准书号">
                            <label class="control-label" for="addISBN" style="display: none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书名称</label>
                        <div class="col-sm-7">
                            <input type="text" value="图书名称" class="form-control" id="addBookName" placeholder="请输入图书名称">
                            <label class="control-label" for="addBookName" style="display: none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书类型</label>
                        <div class="col-sm-7">
                            <select class="form-control" id="addBookType">
                                <option value="-1">请选择</option>
                            </select>
                            <label class="control-label" for="addBookType" style="display: none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">作者名称</label>
                        <div class="col-sm-7">
                            <input type="text" value="作者名称" class="form-control" id="addAuthor" placeholder="请输入作者名称">
                            <label class="control-label" for="addAuthor" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">出版社</label>
                        <div class="col-sm-7">
                            <input type="text" value="出版社名称" class="form-control" id="addPress" placeholder="请输入出版社">
                            <label class="control-label" for="addPress" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">总数量</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="addNum" placeholder="请输入图书总数量">
                            <label class="control-label" for="addNum" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">价格</label>
                        <div class="col-sm-7">
                            <input type="text" value="99" class="form-control" id="addPrice" placeholder="请输入图书价格">
                            <label class="control-label" for="addPrice" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">简介</label>
                        <div class="col-sm-7">
                            <textarea class="form-control" rows="3" id="addDescription"
                                      placeholder="请输入图书简介"></textarea>
                            <label class="control-label" for="addDescription" style="display: none;"></label>
                        </div>
                    </div>

                    <!---------------------表单-------------------->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="addBook">
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
                        修改图书信息
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书ISBN号</label>
                        <div class="col-sm-7">
                            <input type="hidden" name="bookId" id="updateBookId">
                            <input type="text" class="form-control" id="updateISBN" placeholder="请输入国际标准书号">
                            <label class="control-label" for="updateISBN" style="display: none;"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updateBookName" placeholder="请输入图书名称">
                            <label class="control-label" for="updateBookName" style="display: none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">图书类型</label>
                        <div class="col-sm-7">
                            <select class="form-control" id="updateBookType">
                                <option value="-1">请选择</option>
                            </select>
                            <label class="control-label" for="updateBookType" style="display: none;"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">作者名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updateAuthor" placeholder="请输入作者名称">
                            <label class="control-label" for="updateAuthor" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">出版社</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updatePress" placeholder="请输入出版社">
                            <label class="control-label" for="updatePress" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">价格</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="updatePrice" placeholder="请输入图书价格">
                            <label class="control-label" for="updatePrice" style="display: none;"></label>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label">简介</label>
                        <div class="col-sm-7">
                            <textarea class="form-control" rows="3" id="updateDescription"
                                      placeholder="请输入图书简介"></textarea>
                            <label class="control-label" for="updateDescription" style="display: none;"></label>
                        </div>
                    </div>

                    <!---------------------表单-------------------->

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="updateBook">
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


</body>
</html>
