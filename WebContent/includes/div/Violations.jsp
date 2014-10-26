<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>

<%@ page isELIgnored="false"%>
<div id="violations" style="color: red;">
	<c:if test="${fn:length(violations)==0}">
		<div>恭喜您(${studentid})，目前沒有任何違規記錄。</div>
	</c:if>
	<c:if test="${fn:length(violations)>0}">
		<div>您(${studentid})目前總共有 ${fn:length(violations)} 次違規記錄</div>
		<ul>
			<c:forEach var="violation" items="${violations }">
				<li>${violation.date}: ${violation.studentid} :
					${violation.reason.value}</li>
			</c:forEach>
		</ul>
		<div>請注意，違規 ${applicationScope.appConfig.punishingthreshold}
			次，將會被停權 ${applicationScope.appConfig.punishingdays} 天</div>
	</c:if>

</div>