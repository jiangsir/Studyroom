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
	<fieldset
		style="text-align: left; padding: 1em; margin: auto; width: 60%;">
		<legend style="font-size: x-large;">${alert.type}</legend>
		<h1>${alert.title}</h1>
		<p></p>
		<h2>${alert.subtitle }</h2>
		<div>${alert.content}</div>
		<ul>
			<c:forEach var="list" items="${alert.list}">
				<li>${list}</li>
			</c:forEach>
		</ul>
		<ul>
			<c:forEach var="map" items="${alert.map}">
				<li>${map.key}:${map.value}</li>
			</c:forEach>
		</ul>
		<hr style="margin-top: 3em;" />
		<div style="text-align: center;">
			<c:forEach var="uri" items="${alert.uris}">
				<a href="${uri.value}" type="button">${uri.key}</a>
			</c:forEach>
		</div>
	</fieldset>

	<%-- 	<c:forEach var="contentmap" items="${alert.contentmap}">${contentmap.key }=${contentmap.value}
	</c:forEach>
 --%>

	<c:if test="${sessionScope.currentUser.isAdmin}">
		<fieldset id="debug">
			<legend>Debug: </legend>
			<ul>
				<c:forEach var="debug" items="${alert.debugs}">
					<li>${debug}</li>
				</c:forEach>
			</ul>
			<div>
				<c:if test="${fn:length(alert.stacktrace)>0}">
					<div style="text-align: left; margin-top: 1em;">
						<h3>stacktrace:</h3>
						<div style="font-family: monospace;">
							<c:forEach var="stacktrace" items="${alert.stacktrace}">${stacktrace.className}.${stacktrace.methodName}(${stacktrace.fileName}:${stacktrace.lineNumber})<br />
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
		</fieldset>
	</c:if>


	<%-- 	<c:forEach var="url" items="${alert.urls}">
		<a href="${url.value}">${url.key}</a>
	</c:forEach>
 --%>
	<jsp:include page="Footer.jsp" />
</body>
</html>