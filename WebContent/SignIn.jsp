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
<script type="text/javascript"
	src="jscripts/functions/showErrorDialog.js"></script>

<style type="text/css">
.box {
	display: table-cell;
	/* 	width: 300px;
	height: 300px;
 */
	background-color: #eeeeee;
	text-align: center;
	vertical-align: middle;
	border: solid 2px #bbbbbb;
	margin: auto;
	vertical-align: middle;
	padding: 3em;
	border-radius: 1em;
}

#css_table {
	display: table;
	margin: auto;
}

input {
	font-size: 2em;
	border: none;
	border: solid 1px #ccc;
	border-radius: 10px;
}
</style>
<script type="text/javascript">
	$(function() {
		$(document).click(function() {
			$("input#SignIn").focus();
		});

		var time = 0;
		var prev_now = 0;
		$("input#SignIn").keypress(function(e) {
			if (prev_now == 0) {
				prev_now = $.now();
			}
			time += $.now() - prev_now;

			code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13) {
				if (time > 1000) {
					$("#errorjson").html("請勿使用鍵盤輸入！");
					$("input#SignIn").val("");
					location.reload();
				} else {
					time = 0;
					prev_now = 0;
					//targetForm是表單的ID
					jQuery.ajax({
						type : "POST",
						url : "SignIn.api",
						data : "id=" + $(this).val(),
						async : false,
						timeout : 5000,
						success : function(result) {
							location.reload();
						},
						error : function(jqXHR, textStatus, errorThrown) {
							//location.reload();
							//showErrorDialog(jqXHR, textStatus, errorThrown);
							var errorjson;
							if (jqXHR.responseText !== '') {
								errorjson = jQuery.parseJSON(jqXHR.responseText);
							} else {
								errorjson = errorThrown;
							}
							$("#errorjson").html(errorjson.title);
							$("input#SignIn").val("");
						}
					});
				}
			}
		});

	});
</script>
</head>
<body>
	<jsp:include page="includes/dialog/Error.jsp" />
	<jsp:include page="Header.jsp" />

	<div style="text-align: center;">
		<h1>簽到／退</h1>
	</div>

	<div id="css_table">
		<div class="box">
			<input type="text" id="SignIn" />
		</div>
	</div>
	<hr>
	<div id="errorjson"
		style="text-align: center; color: red; font-size: 2em;"></div>
	<table style="margin: auto; width: 40%;">
		<tr>
			<td>學號</td>
			<td>座位</td>
			<td>簽到／退</td>
			<td>時間</td>
		</tr>
		<c:forEach var="attendance" items="${attendances }">
			<tr>
				<td>${attendance.studentid}</td>
				<td>${attendance.seatid}</td>
				<td><c:if test="${attendance.status=='SignIn' }">
						<span style="color: green;">簽到</span>
					</c:if> <c:if test="${attendance.status=='SignOut' }">
						<span style="color: red;">簽退</span>
					</c:if></td>
				<td>${attendance.timestamp}</td>
			</tr>
		</c:forEach>
	</table>

	<jsp:include page="Footer.jsp" />
</body>
</html>