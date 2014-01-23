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
			src="${pageContext.request.contextPath}/images/BANNER_IMAGE.jpg"></a>
	</div>

	<ul id="menu">
		<li><c:if test="${sessionScope.currentUser==null}">
				<a href="${pageContext.request.contextPath}/Login">登入</a>
			</c:if> <c:if test="${sessionScope.currentUser!=null}">
				<li><a href="#">${sessionScope.currentUser.name}</a>
					<ul>
						<li><a
							href="${pageContext.request.contextPath}/UpdateUser.do?userid=${sessionScope.currentUser.id}">修改</a></li>
						<li><a href="${pageContext.request.contextPath}/Logout">登出</a></li>
					</ul></li>

			</c:if></li>
		<!-- 		<li><a href="#">Item 1</a></li>
		<li><a href="#">Item 2</a></li>
		<li><a href="#">Item 3</a>
			<ul>
				<li><a href="#">Item 3-1</a>
					<ul>
						<li><a href="#">Item 3-11</a></li>
						<li><a href="#">Item 3-12</a></li>
						<li><a href="#">Item 3-13</a></li>
					</ul></li>
				<li><a href="#">Item 3-2</a></li>
				<li><a href="#">Item 3-3</a></li>
				<li><a href="#">Item 3-4</a></li>
				<li><a href="#">Item 3-5</a></li>
			</ul></li>
		<li><a href="#">Item 4</a></li>
 -->
	</ul>
	<div style="clear: both;"></div>
</div>
