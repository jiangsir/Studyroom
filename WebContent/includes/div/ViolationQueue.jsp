<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>

<%@ page isELIgnored="false"%>
<div id="violationQueue" style="color: red;">
	<c:if test="${student.isStopBooking}">
		<h3>您目前停權中！</h3>
	</c:if>
	<div>您(${student.studentid})目前總共有
		${fn:length(student.violationQueue)} 次違規記錄</div>
	<ul>
		<c:forEach var="violation" items="${student.violationQueue}">
			<li><fmt:formatDate value="${violation.date}"
					pattern="yyyy-MM-dd E" />: ${violation.reason.value}</li>
		</c:forEach>
	</ul>
	<div>請注意，違規 ${applicationScope.appConfig.punishingthreshold}
		次，將會被停權 ${applicationScope.appConfig.punishingdays} 天</div>

</div>