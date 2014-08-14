<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />

	<h1>ALERT.jsp ${alert.type}: ${alert.title }</h1>
	<h2>${alert.subtitle }</h2>
	<div>${alert.content }</div>
	<%-- 	<c:forEach var="contentmap" items="${alert.contentmap}">${contentmap.key }=${contentmap.value}
	</c:forEach>
 --%>
	<c:if test="${fn:length(alert.stacktrace)>0}">
		<h3>stacktrace:</h3>
		<div style="font-family: monospace;">
			<c:forEach var="stacktrace" items="${alert.stacktrace}">${stacktrace.className}.${stacktrace.methodName}(${stacktrace.fileName}:${stacktrace.lineNumber})<br />
			</c:forEach>
		</div>
	</c:if>
<%-- 	<c:forEach var="url" items="${alert.urls}">
		<a href="${url.value}">${url.key}</a>
	</c:forEach>
 --%>
	<jsp:include page="Footer.jsp" />
</body>
</html>