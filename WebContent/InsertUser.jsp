<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<script language="javascript">
	jQuery(document).ready(function() {
		$("select#role").children().each(function() {
			if ($(this).parent().attr("role") == $(this).val()) {
				$(this).attr("selected", "true");
				return;
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div>新增一個 User.</div>
	<form method="post" action="">
		帳號：<input name="account" value="${user.account}" /><br /> 密碼：<input
			type="password" name="passwd1" /><br /> 確認密碼：<input type="password"
			name="passwd2" /><br /> 姓名：<input name="name" value="${user.name}" /><br />
		角色： <select id="role" name="role" role="${user.role}">
			<c:forEach var="role" items="${user.roles}">
				<option>${role }</option>
			</c:forEach>
		</select> <br /> <input type="hidden" name="userid" value="${user.id }" /> <input
			type="submit" value="送出" />
	</form>
	<jsp:include page="Footer.jsp" />
</body>
</html>