<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${applicationScope.title}</title>
<script type="text/javascript" src="./jscripts/jquery-2.0.3.min.js"></script>

<script language="javascript">
	jQuery(document).ready(function() {
		jQuery("input:first").focus();

	});
</script>
</head>
<body>
	<jsp:include page="Header.jsp" />

	<form method="post" action="Login">
		帳號：<input name="account" /><br /> 密碼：<input name="passwd" /><br />
		<input type="hidden" name="returnPage" value="${returnPage}" /> <input
			type="submit" value="送出" />

	</form>
	<jsp:include page="Footer.jsp" />
</body>
</html>