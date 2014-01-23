<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />

	<form method="post" action="" enctype="multipart/form-data">
		上傳相片：<input type="file" name="photo" /><br> <br> <input
			type="submit" value="送出" />
	</form>
	<jsp:include page="Footer.jsp" />
</body>
</html>