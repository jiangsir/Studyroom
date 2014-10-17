<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>

<%@ page isELIgnored="false"%>
<div id="violations" style="color: red;">
	<c:if test="${sessionScope.currentUser.isAdmin==true }">
		<c:if test="${fn:length(violations)>0}">
			<div>您(${violations[0].studentid})目前總共有
				${fn:length(violations)} 次違規記錄</div>
			<c:forEach var="violation" items="${violations }">
    ${violation.date}: ${violation.studentid} : ${violation.reason}: ${violation.status}<br />
			</c:forEach>
		</c:if>
	</c:if>
</div>