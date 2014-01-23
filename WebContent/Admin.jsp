<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />

	<p>
		使用者列表：<a href="InsertUser.do">新增一個使用者</a>
	</p>
	<table>
		<tr>
			<td>使用者歷史劃位統計</td>
			<td>操作</td>
		</tr>
		<c:forEach var="user" items="${users }">
			<tr>
				<td><a href="./History/${user.account }">${user.account}:
						${user.name }</a> <br /></td>
				<td><a href="UpdateUser.do?userid=${user.id }">修改</a> | <a
					href="DeleteUser.do?userid=${user.id }">刪除</a></td>
			<tr>
		</c:forEach>
	</table>

	<jsp:include page="Footer.jsp" />
</body>
</html>