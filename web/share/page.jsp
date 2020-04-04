<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${pb.list != null}">
	<%-- 定义页码列表的长度，5个长 --%>
	<c:choose>
		<%-- 第一条：如果总页数<=5，那么页码列表为1 ~ totalPage 从第一页到总页数--%>
		<%--如果总页数<=5的情况 --%>
		<c:when test="${pb.totalPage <= 5 }">
			<c:set var="begin" value="1"/>
			<c:set var="end" value="${pb.totalPage }"/>
		</c:when>
		<%--总页数>5的情况 --%>
		<c:otherwise>
			<%-- 第二条：按公式计算，让列表的头为当前页-2；列表的尾为当前页+2 --%>
			<c:set var="begin" value="${pb.currentPage-2 }"/>
			<c:set var="end" value="${pb.currentPage+2 }"/>

			<%-- 第三条：第二条只适合在中间，而两端会出问题。这里处理begin出界！ --%>
			<%-- 如果begin<1，那么让begin=1，相应end=5 --%>
			<c:if test="${begin<1 }">
				<c:set var="begin" value="1"/>
				<c:set var="end" value="5"/>
			</c:if>
			<%-- 第四条：处理end出界。如果end>tp，那么让end=tp，相应begin=tp-4 --%>
			<c:if test="${end>pb.totalPage }">
				<c:set var="begin" value="${pb.totalPage-4 }"/>
				<c:set var="end" value="${pb.totalPage }"/>
			</c:if>
		</c:otherwise>
	</c:choose>
	<div class="pull-right"><!--右对齐--->
		<ul class="pagination">
			<li class="disabled"><a href="#">第${pb.currentPage} 页/共${pb.totalPage}页</a>
			</li>
			<li>
				<a href="javascript:page(${1});">首页</a>
			</li>
			<!-- 上一页 -->
				<%-- 循环显示页码列表 --%>
			<c:forEach begin="${begin }" end="${end }" var="i">
				<c:choose>
					<%--如果是当前页则设置无法点击超链接 --%>
					<c:when test="${i eq pb.currentPage }">
						<li class="active"><a>${i }</a>
						<li>
					</c:when>
					<c:otherwise>
						<li>
							<%--中间页码--%>
							<a href="javascript:page(${i});">${i}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
				<%--否则显示尾页 --%>
			<li>
				<a href="javascript:page(${pb.totalPage});">尾页</a>
			</li>
		</ul>
	</div>
</c:if>