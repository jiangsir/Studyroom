<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>

<div class="confirm_dialog"
	style="cursor: default; padding: 10px; display: none; text-align: center"
	type="${param.type}" url="${param.url}" data="${param.data}">
	<h2>${param.content}</h2>
</div>
