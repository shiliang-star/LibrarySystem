<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN" class="ax-vertical-centered">
<head>
    <meta charset="UTF-8">
    <title>逾期信息</title>
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
    <script src="${pageContext.request.contextPath}/static/js/readerUpdateInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/readerUpdatePwd.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/getReaderForfeitInfo.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/reader.js"></script>

</head>

<body class="bootstrap-admin-with-small-navbar">
<nav class="navbar navbar-inverse navbar-fixed-top bootstrap-admin-navbar bootstrap-admin-navbar-under-small"
     role="navigation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="collapse navbar-collapse main-navbar-collapse">
                    <a class="navbar-brand"
                       href="${pageContext.request.contextPath}/reader.jsp"><strong>欢迎使用图书馆管理系统</strong></a>
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${sessionScope.reader!=null}"><!-- 判断是否已经登录 -->
                        <li class="dropdown">
                            <a href="#" role="button" class="dropdown-toggle" data-hover="dropdown"> <i
                                    class="glyphicon glyphicon-user"></i> 欢迎您，${sessionScope.reader.name}
                                <i class="caret"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="#updateinfo" data-toggle="modal">个人资料</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="#updatepwd" data-toggle="modal">修改密码</a></li>
                                <li role="presentation" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/readerLoginAction_logout.action">退出</a>
                                </li>
                            </ul>
                        </li>
                        </c:if>
                        <c:if test="${sessionScope.reader==null}"><!-- 如果未登录，出现登录按钮 -->
                        <button type="button" class="btn btn-default btn-sm " id="btn_login" style="margin: 10px"
                                data-dismiss="modal">登录
                        </button>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-2 bootstrap-admin-col-left">
            <ul class="nav navbar-collapse collapse bootstrap-admin-navbar-side">
                <li>
                    <a href="${pageContext.request.contextPath}/reader/bookSearchController_findBookByPage.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 图书查询</a>
                </li>
                <c:if test="${sessionScope.reader!=null}"><!-- 判断是否登录 -->
                <li>
                    <a href="${pageContext.request.contextPath}/reader/borrowInfoController_findMyBorrowInfoByPage.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 借阅信息</a>
                </li>
                </c:if>
                <c:if test="${sessionScope.reader!=null}"><!-- 判断是否登录 -->
                <li class="active">
                    <a href="${pageContext.request.contextPath}/reader/overDueInfoController_findMyForfeitInfoByPage.action"><i
                            class="glyphicon glyphicon-chevron-right"></i> 逾期信息</a>
                </li>
                </c:if>
            </ul>
        </div>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">罚金查询</div>
                        </div>
                        <div class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal"
                                  action="${pageContext.request.contextPath}/reader/overDueInfoController_findMyForfeitInfoByPage.action"
                                  method="post">
                                <div class="row">
                                    <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label"><label
                                                class="text-danger"></label>借阅编号</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" name="borrowId" type="text" value="${query.borrowId}"
                                                   placeholder="请输入借阅编号">
                                            <label class="control-label" style="display: none"></label>
                                        </div>
                                    </div>

                                    <div class="col-lg-5 form-group">
                                        <label class="col-lg-4 control-label"><label
                                                class="text-danger"></label>图书ISBN号</label>
                                        <div class="col-lg-8">
                                            <input class="form-control" name="ISBN" type="text" value="${query.ISBN}"
                                                   placeholder="请输入借阅图书的ISBN号">
                                            <label class="control-label" style="display: none"></label>
                                        </div>
                                    </div>


                                    <div class="col-lg-2 form-group">
                                        <button type="submit" class="btn btn-primary" id="btn_borrow">查询</button>
                                    </div>
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
                            <th>借阅编号</th>
                            <th>图书ISBN号</th>
                            <th>图书名称</th>
                            <th>读者证件号</th>
                            <th>读者名称</th>
                            <th>逾期天数</th>
                            <th>需缴纳罚金</th>
                            <th>操作</th>
                        </tr>
                        </thead>


                        <!---在此插入信息-->
                        <c:if test="${requestScope.pb.list!=null}">
                            <c:forEach items="${requestScope.pb.list}" var="forfeit">
                                <tbody>
                                <td><c:out value="${forfeit.borrowId}"/></td>
                                <td><c:out value="${forfeit.ISBN}"/></td>
                                <td><c:out value="${forfeit.bookName}"/></td>
                                <td><c:out value="${forfeit.paperNO}"/></td>
                                <td><c:out value="${forfeit.readerName}"/></td>
                                <td><c:out value="${forfeit.overDay}"/></td>
                                <td><c:out value="${forfeit.forfeit}"/></td>
                                <td>
                                    <button type="button" class="btn btn-info btn-xs" data-toggle="modal"
                                            data-target="#findBackModal"
                                            onclick="getForfeitInfoById(<c:out value="${forfeit.borrowId}"/>)">查看
                                    </button>

                                </td>
                                </tbody>
                            </c:forEach>
                        </c:if>
                        <c:if test="${requestScope.pb.list==null}">
                            <tbody>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            </tbody>
                        </c:if>

                    </table>

<%--                    <s:if test="${requestScope.pb.pb!=null}">--%>

<%--                        &lt;%&ndash; 定义页码列表的长度，5个长 &ndash;%&gt;--%>
<%--                        <c:choose>--%>
<%--                            &lt;%&ndash; 第一条：如果总页数<=5，那么页码列表为1 ~ totaPage 从第一页到总页数&ndash;%&gt;--%>
<%--                            &lt;%&ndash;如果总页数<=5的情况 &ndash;%&gt;--%>
<%--                            <c:when test="${pb.totaPage <= 5 }">--%>
<%--                                <c:set var="begin" value="1"/>--%>
<%--                                <c:set var="end" value="${pb.totaPage }"/>--%>
<%--                            </c:when>--%>
<%--                            &lt;%&ndash;总页数>5的情况 &ndash;%&gt;--%>
<%--                            <c:otherwise>--%>
<%--                                &lt;%&ndash; 第二条：按公式计算，让列表的头为当前页-2；列表的尾为当前页+2 &ndash;%&gt;--%>
<%--                                <c:set var="begin" value="${pb.pageCode-2 }"/>--%>
<%--                                <c:set var="end" value="${pb.pageCode+2 }"/>--%>

<%--                                &lt;%&ndash; 第三条：第二条只适合在中间，而两端会出问题。这里处理begin出界！ &ndash;%&gt;--%>
<%--                                &lt;%&ndash; 如果begin<1，那么让begin=1，相应end=5 &ndash;%&gt;--%>
<%--                                <c:if test="${begin<1 }">--%>
<%--                                    <c:set var="begin" value="1"/>--%>
<%--                                    <c:set var="end" value="5"/>--%>
<%--                                </c:if>--%>
<%--                                &lt;%&ndash; 第四条：处理end出界。如果end>tp，那么让end=tp，相应begin=tp-4 &ndash;%&gt;--%>
<%--                                <c:if test="${end>pb.totaPage }">--%>
<%--                                    <c:set var="begin" value="${pb.totaPage-4 }"/>--%>
<%--                                    <c:set var="end" value="${pb.totaPage }"/>--%>
<%--                                </c:if>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>


<%--                        <div class="pull-right"><!--右对齐--->--%>
<%--                            <ul class="pagination">--%>
<%--                                <li class="disabled"><a href="#">第<s:out--%>
<%--                                        value="${requestScopepb.pageCode}"/>页/共<s:out--%>
<%--                                        value="${requestScopepb.totaPage}"/>页</a>--%>
<%--                                </li>--%>
<%--                                <li>--%>
<%--                                    <a href="${pageContext.request.contextPath}/reader/overdueAction_${pb.url }pageCode=1">首页</a>--%>
<%--                                </li>--%>
<%--                                <li>--%>
<%--                                    <a href="${pageContext.request.contextPath}/reader/overdueAction_${pb.url }pageCode=${pb.pageCode-1 }">&laquo;</a>--%>
<%--                                </li><!-- 上一页 -->--%>
<%--                                    &lt;%&ndash; 循环显示页码列表 &ndash;%&gt;--%>
<%--                                <c:forEach begin="${begin }" end="${end }" var="i">--%>
<%--                                    <c:choose>--%>
<%--                                        &lt;%&ndash;如果是当前页则设置无法点击超链接 &ndash;%&gt;--%>
<%--                                        <c:when test="${i eq pb.pageCode }">--%>
<%--                                            <li class="active"><a>${i }</a>--%>
<%--                                            <li>--%>
<%--                                        </c:when>--%>
<%--                                        <c:otherwise>--%>
<%--                                            <li>--%>
<%--                                                <a href="${pageContext.request.contextPath}/reader/overdueAction_${pb.url }pageCode=${i}">${i}</a>--%>
<%--                                            </li>--%>
<%--                                        </c:otherwise>--%>
<%--                                    </c:choose>--%>
<%--                                </c:forEach>--%>
<%--                                    &lt;%&ndash;如果当前页数没到总页数，即没到最后一页,则需要显示下一页 &ndash;%&gt;--%>
<%--                                <c:if test="${pb.pageCode < pb.totaPage }">--%>
<%--                                    <li>--%>
<%--                                        <a href="${pageContext.request.contextPath}/reader/overdueAction_${pb.url }pageCode=${pb.pageCode+1}">&raquo;</a>--%>
<%--                                    </li>--%>
<%--                                </c:if>--%>
<%--                                    &lt;%&ndash;否则显示尾页 &ndash;%&gt;--%>
<%--                                <li>--%>
<%--                                    <a href="${pageContext.request.contextPath}/reader/overdueAction_${pb.url }pageCode=${pb.totaPage}">尾页</a>--%>
<%--                                </li>--%>
<%--                            </ul>--%>
<%--                        </div>--%>
<%--                    </s:if>--%>
                    <script type="text/javascript">
                        function page(currentPage) {
                            window.location.href = "${pageContext.request.contextPath}/reader/overDueInfoController_findMyForfeitInfoByPage.action?currentPage=" + currentPage+
                                "&borrowId=${query.borrowId}&ISBN=${query.ISBN}";
                        }
                    </script>
                    <jsp:include page="/share/page.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>


<!--------------------------------------查看的模糊框------------------------>
<form class="form-horizontal">   <!--保证样式水平不混乱-->
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="findBackModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        查看罚款信息
                    </h4>
                </div>
                <div class="modal-body">

                    <!---------------------表单-------------------->
                    <div class="form-group">
                        <label for="borrowId" class="col-sm-3 control-label">借阅编号</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="borrowId" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="ISBN" class="col-sm-3 control-label">借阅书籍ISBN号</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="ISBN" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="bookName" class="col-sm-3 control-label">借阅书籍名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="bookName" readonly="readonly">

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookType" class="col-sm-3 control-label">借阅书籍类型</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="bookType" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="paperNO" class="col-sm-3 control-label">读者证件号</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="paperNO" readonly="readonly">

                        </div>
                    </div>
                    <div class="form-group">
                        <label for="readerName" class="col-sm-3 control-label">读者名称</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="readerName" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="readerType" class="col-sm-3 control-label">读者类型</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="readerType" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="overday" class="col-sm-3 control-label">逾期天数</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="overday" readonly="readonly">

                        </div>
                    </div>

                    <div class="form-group">
                        <label for="admin" class="col-sm-3 control-label">操作管理员</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="admin" readonly="readonly">

                        </div>
                    </div>


                    <div class="form-group">
                        <label for="state" class="col-sm-3 control-label">缴纳状态</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="state" readonly="readonly">

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


<!-------------------------------------------------------------->

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
                        <label for="oldPwd" class="col-sm-3 control-label">原密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="oldPwd" placeholder="请输入原密码">
                            <label class="control-label" for="oldPwd" style="display: none"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="newPwd" class="col-sm-3 control-label">新密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="newPwd" placeholder="请输入新密码">
                            <label class="control-label" for="newPwd" style="display: none"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmPwd" class="col-sm-3 control-label">确认密码</label>
                        <div class="col-sm-7">
                            <input type="password" class="form-control" id="confirmPwd" placeholder="请输入确认密码">
                            <label class="control-label" for="confirmPwd" style="display: none"></label>
                        </div>
                    </div>
                    <!--正文-->


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="update_readerPwd">
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
                        <label for="name" class="col-sm-3 control-label">真实姓名</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="name" placeholder="请输入您的真实姓名" disabled="disabled"
                                   value='${sessionScope.reader.name} '>
                            <label class="control-label" for="name" style="display: none"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone" class="col-sm-3 control-label">联系号码</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="phone" placeholder="请输入您的联系号码"
                                   value='${sessionScope.reader.phone}'>
                            <label class="control-label" for="phone" style="display: none"></label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email" class="col-sm-3 control-label">邮箱</label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="email" placeholder="请输入您的邮箱"
                                   value='${sessionScope.reader.email}'>
                            <label class="control-label" for="email" style="display: none"></label>
                        </div>
                    </div>
                    <!--正文-->


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="reader_updateInfo">
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
