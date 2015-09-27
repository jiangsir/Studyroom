<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>

<%@ page isELIgnored="false"%>

<div id="StudentAttendance" studentid="${student.studentid}">
	<jsp:include page="../div/ViolationQueue.jsp" />
	<hr>
	<%-- 	<c:set var="sec" value="${(student.ms-student.ms%1000)/1000}" />
	<c:set var="min" value="${(sec-sec%60)/60}" />
	<c:set var="hour" value="${(min-min%60)/60}" />
 --%>
	<div>
		${student.studentid } 同學，總共進場：${student.staytime }
		<%-- 		<fmt:formatNumber type="number" pattern="##" value="${hour%60}" />
		小時
		<fmt:formatNumber type="number" pattern="00" value="${min%60}" />
		分鐘
		<fmt:formatNumber type="number" pattern="00" value="${sec%60}" />
		秒。
 --%>
	</div>
	<hr>
	<div>簽到／退清單</div>
	<c:forEach var="attendance" items="${student.attendances }">
	${attendance.studentid}: ${attendance.timestamp} : ${attendance.status.value}<br />
	</c:forEach>
</div>