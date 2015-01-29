<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
<script type="text/javascript" src="ManageViolations.js"></script>
<script type="text/javascript"
	src="includes/dialog/Confirm.js?${applicationScope.built }"></script>

</head>
<body>
	<jsp:include page="Header.jsp" />
	<jsp:include page="includes/dialog/CancelViolationDialog.jsp" />
	<h1>目前有違規記錄的使用者</h1>
	<jsp:include page="includes/dialog/Confirm.jsp">
		<jsp:param name="content" value="確定要刪除所有人的違規記錄？本動作只應每學期進行一次。" />
		<jsp:param name="type" value="POST" />
		<jsp:param name="url" value="Violation.api" />
		<jsp:param name="data" value="action=disableAllViolations" />
	</jsp:include>
	<button type="confirm" id="disableAllViolations">刪除所有違規記錄(每學期僅做一次)</button>
	<hr>
	<c:forEach var="student" items="${students}">
		<div style="">
			<a href="./StudentBookings?studentid=${student.studentid}">${student.studentid}</a>,
			違規次數:${fn:length(student.violationQueue)} 次
			<c:if test="${student.isStopBooking}">
				<span style="color: red;"> --停權中！！</span>
			</c:if>
			<br> <br>所有違規列表
			<c:forEach var="violation" items="${student.violations}">
			| ${violation.date}
			</c:forEach>

			<ul>
				<c:forEach var="violation" items="${student.violationQueue}">
					<li style="display: inline-block;">
						<button type="button" id="cancelViolation"
							violationid="${violation.id }">取消 ${violation.date}:
							${violation.reason.value }</button>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
	<jsp:include page="Footer.jsp" />
</body>
</html>