<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<div id="header">
	<div id="logo"
		style="font-size: xx-large; text-align: center; margin-bottom: 1em;">
		<a href="${pageContext.request.contextPath}"><img
			src="${pageContext.request.contextPath}/images/_TITLE_HSIC.png"></a>
	</div>
	<div style="float: right;">
		<c:if test="${sessionScope.user==null}">
			<a href="${pageContext.request.contextPath}/Login">登入</a>
		</c:if>
		<c:if test="${sessionScope.user!=null}">${sessionScope.user.name} | <a
				href="${pageContext.request.contextPath}/UpdateUser.do?userid=${sessionScope.user.id}">修改</a> | <a
				href="${pageContext.request.contextPath}/Logout">登出</a>
		</c:if>
	</div>
	<div style="clear: both;"></div>
</div>
