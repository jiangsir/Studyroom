<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
<script type="text/javascript"
	src="ManageViolations.js?${applicationScope.built }"></script>
<script type="text/javascript"
	src="includes/dialog/Confirm.js?${applicationScope.built }"></script>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<jsp:include page="includes/dialog/CancelViolationDialog.jsp" />
	<div style="margin: 1em; text-align: center;">
		<a href="?date=${prevdate }" type="button">前一日</a> <a href="?"
			type="button">今天</a> <a href="?date=${nextdate}" type="button">後一日</a>
	</div>
	<h1>
		統計到
		<fmt:formatDate value="${date}" pattern="yyyy-MM-dd E" />
		之前 有違規記錄的使用者
	</h1>
	<%-- 	<jsp:include page="includes/dialog/Confirm.jsp">
		<jsp:param name="content" value="確定要刪除所有人的違規記錄？本動作只應每學期進行一次。" />
		<jsp:param name="type" value="POST" />
		<jsp:param name="url" value="Violation.api" />
		<jsp:param name="data" value="action=disableAllViolations" />
	</jsp:include>
	<button type="confirm" id="disableAllViolations">取消所有違規記錄(每學期僅做一次)</button>
 --%>
	<form action="Violation.api?action=disableViolationsByDates"
		method="post">
		取消 <input name="date"
			value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />">日(含)
		以前的違規記錄
		<button type="confirm" id="disableViolationsByDates"
			title="確定要取消違規記錄？">取消多日違規紀錄</button>
	</form>
	<form action="Violation.api?action=disableViolationsByDate"
		method="post">
		取消 <input name="date"
			value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />">日的違規記錄
		<button type="confirm" id="disableViolationsByDate" title="確定要取消違規記錄？">取消單日違規紀錄</button>
	</form>
	<hr>
	<c:forEach var="student" items="${students}">
		<div style="display: inline-block;">
			<a href="./StudentBookings?studentid=${student.studentid}">${student.studentid}</a>
			<%-- 			,違規次數:${fn:length(student.violationQueue)} 次

 --%>
			<ul style="display: inline-block;">
				<c:forEach var="violation" items="${student.violationQueue}">
					<li style="display: inline-block;">
						<button type="button" id="cancelViolation"
							violationid="${violation.id }">
							取消 [
							<fmt:formatDate value="${violation.date}" pattern="yyyy-MM-dd E" />
							] ${violation.reason.value }
						</button>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${student.isStopBooking}">
				<span style="color: red;"> --停權中！！</span>
			</c:if>
		</div>
		<br>歷史違規列表( ${fn:length(student.violations)}次)
		<select style="font-size: 1em;">
			<c:forEach var="violation" items="${student.violations}"
				varStatus="varStatus">
				<option>${varStatus.count}.
					<fmt:formatDate value="${violation.date}" pattern="yyyy-MM-dd E" />
				</option>
			</c:forEach>
		</select>

		<hr>
	</c:forEach>
	<jsp:include page="Footer.jsp" />
</body>
</html>