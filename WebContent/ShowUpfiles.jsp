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
		檔案列表：<a href="InsertUpfile.do">新增檔案</a>
	</p>
	<table>
		<tr>
			<td>檔案</td>
			<td>操作</td>
		</tr>
		<c:forEach var="upfile" items="${upfiles }">
			<tr>
				<td><a
					href="${pageContext.request.contextPath}/DownloadUpfile?id=${upfile.id}">${upfile.filename}
						(${upfile.filetype })</a> <br /></td>
				<td><a href="DeleteUpfile.do?id=${upfile.id}">刪除</a></td>
			<tr>
		</c:forEach>
	</table>

	<jsp:include page="Footer.jsp" />
<body>
</html>