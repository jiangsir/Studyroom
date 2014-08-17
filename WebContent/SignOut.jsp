<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
<style type="text/css">
.box {
	display: table-cell;
	width: 300px;
	height: 300px;
	background-color: #bbb;
	text-align: center;
	vertical-align: middle;
	border: solid 2px #fff;
	margin: auto;
	vertical-align: middle;
}

#css_table {
	display: table;
	margin: auto;
}

input {
	font-size: 2em;
}
</style>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div style="text-align: center;">
		<h1>簽退</h1>
	</div>

	<div id="css_table">
		<div class="box">
			<input type="text" />
		</div>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>