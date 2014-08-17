<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="CommonHead.jsp" />
<script type="text/javascript" src="EditAppConfig.js"></script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<form id="form1" name="form1" method="post" action="">
		<div id="tabs-1">
			<table align="center" style="margin: 10px;">
				<tr style="padding: 10px;">
					<th>站名</th>
					<td style="padding: 10px;"><input name="Title" type="text"
						id="Title" value="${appConfig.title}" size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>Page Size</th>
					<td style="padding: 10px;"><input name="PageSize" type="text"
						id="Title" value="${appConfig.pageSize}" size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>預設 Login 的路徑</th>
					<td style="padding: 10px;"><input name="DefaultLogin"
						type="text" id="DefaultLogin" value="${appConfig.defaultLogin}"
						size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>clientid</th>
					<td style="padding: 10px;"><input name="client_id" type="text"
						id="Title" value="${appConfig.client_id}" size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>client_secret</th>
					<td style="padding: 10px;"><input name="client_secret"
						type="text" id="Title" value="${appConfig.client_secret}"
						size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>redirect_uri</th>
					<td style="padding: 10px;"><input name="redirect_uri"
						type="text" id="Title" value="${appConfig.redirect_uri}" size="50"
						maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>訂位開始時間</th>
					<td style="padding: 10px;"><input name="starttime" type="text"
						id="Title" value="${appConfig.starttime}" size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>訂位截止時間</th>
					<td style="padding: 10px;"><input name="deadline" type="text"
						id="Title" value="${appConfig.deadline}" size="50" maxlength="50" /></td>
				</tr>
				<tr style="padding: 10px;">
					<th>可以進行簽到／退的 ip 範圍(以 CIDR 格式表示)</th>
					<td style="padding: 10px;"><input name="signinIp" type="text"
						id="SigninIp" value="${appConfig.signinIp}" size="50"
						maxlength="50" /></td>
				</tr>
			</table>
		</div>
		<input type="submit" value="送出" />
	</form>

	<jsp:include page="Footer.jsp" />
</body>
</html>
