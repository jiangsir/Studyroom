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
<script type="text/javascript" src="ManageViolations.js"></script>

</head>
<body>
	<jsp:include page="Header.jsp" />
	<jsp:include page="includes/dialog/CancelViolationDialog.jsp" />
	<hr>
	<h1>目前有違規記錄的使用者</h1>
	<table>
		<c:forEach var="studentid" items="${studentids }">
	${studentid.key}, 違規次數:${fn:length(studentid.value)} 次
			<c:if
				test="${fn:length(studentid.value)>=applicationScope.appConfig.punishingthreshold}">
				<span style="color: red;"> --停權中！！</span>
			</c:if>
			<ul>
				<c:forEach var="violation" items="${studentid.value}">
					<li>${violation.date}: ${violation.reason.value } －

						<button type="button" id="cancelViolation"
							violationid="${violation.id }">取消這個違規記錄</button>
					</li>
				</c:forEach>
			</ul>
		</c:forEach>
	</table>
	<jsp:include page="Footer.jsp" />
</body>
</html>