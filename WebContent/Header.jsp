<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<div id="header">
	<div id="logo"
		style="font-size: 3em; text-align: center; margin: 0.6em;">
		<a href="./">${applicationScope.appConfig.title }</a>
		<%-- <a href="${pageContext.request.contextPath}"><img
			src="${pageContext.request.contextPath}/images/BANNER_IMAGE.jpg"></a> --%>
	</div>

	<ul id="menu">
		<li style="float: left; text-align: left;"><a href="./">回首頁</a></li>
		<li style="float: left; text-align: left;"><a href="#">${pageContext.request.remoteAddr}</a></li>
		<li><c:if test="${sessionScope.currentUser!=null}">
				<c:if test="${sessionScope.currentUser.isAdmin}">
					<li><a href="#">管理</a>
						<ul>
							<li><a href="./ManageBooking">管理每日訂位</a></li>
							<li><a href="./BatchBooking">進行預約(固定)劃位</a></li>
							<li><a href="./ManageRoomstatus">管理開館、閉館日期</a></li>
							<li><a href="./ManageViolations">統計違規紀錄</a></li>
							<li><a href="./EditAppConfig">管理系統參數</a></li>
						</ul></li>
				</c:if>
				<li><a href="./Logout">登出</a></li>
				<li><img src="${sessionScope.currentUser.picture}"
					height="30px" style="vertical-align: middle;" />
					${sessionScope.currentUser.account}(${sessionScope.currentUser.name})
					<%-- 					<ul>
						<li><a
							href="${pageContext.request.contextPath}/UpdateUser.do?userid=${sessionScope.currentUser.id}">修改</a></li>
						<li><a href="${pageContext.request.contextPath}/Logout">登出</a></li>
					</ul> --%></li>
			</c:if> <c:if test="${sessionScope.currentUser==null}">
				<li><a href="./GoogleLogin">登入學生信箱</a></li>
			</c:if></li>
		<c:if
			test="${pageContext.request.remoteAddr == applicationScope.appConfig.signinip || sessionScope.currentUser.isAdmin}">
			<li><a href="./SignIn">簽到／退</a></li>
		</c:if>
		<%-- 		<c:if
			test="${pageContext.request.remoteAddr != applicationScope.appConfig.signinip || sessionScope.currentUser.isAdmin}">
			<li><a href="${pageContext.request.contextPath}/">訂位</a></li>
		</c:if>
 --%>

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
