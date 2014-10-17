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
	<jsp:include page="includes/dialog/ViolationsDialog.jsp" />

	<table>
		<c:forEach var="studentid" items="${studentids }">
			<tr>
				<td id="violationsDialog" studentid="${studentid.key}"><a
					href="#">${studentid.key}</a></td>
				<td>違規次數:${studentid.value } 次</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="Footer.jsp" />
</body>
</html>