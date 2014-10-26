<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>

<div id="cancelViolationDialog" title="取消這個違規記錄" style="display: none;">
	<div id="caneclViolation">
		<form action="Violation.api?action=cancelViolation" method="post">
			取消理由：<input name="comment" value=""></input><input name="violationid"
				value="${violationid}" type="hidden"></input>
		</form>
	</div>
</div>
