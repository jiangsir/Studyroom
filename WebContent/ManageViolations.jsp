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

</head>
<body>
	<jsp:include page="Header.jsp" />
	<jsp:include page="includes/dialog/CancelViolationDialog.jsp" />
	<hr>
	<h1>目前有違規記錄的使用者</h1>
	<c:forEach var="studentidViolation" items="${studentidViolations}">
		<div style="">
			<a href="./StudentBookings?studentid=${studentidViolation.studentid}">${studentidViolation.studentid}</a>,
			違規次數:${fn:length(studentidViolation.violationQueue)} 次
			<c:if test="${studentidViolation.isStopBooking}">
				<span style="color: red;"> --停權中！！</span>
			</c:if>
			<br> <br>所有違規列表
			<c:forEach var="violation" items="${studentidViolation.violations}">
			| ${violation.date}
			</c:forEach>

			<ul>
				<c:forEach var="violation"
					items="${studentidViolation.violationQueue}">
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