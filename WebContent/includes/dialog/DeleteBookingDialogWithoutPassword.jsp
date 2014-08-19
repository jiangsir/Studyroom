<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>

<div id="deleteBookingDialogWithoutPassword" title="請輸入帳號密碼！"
	style="display: none;">
	<fieldset style="padding: 5px;">
		<legend>
			確定要取消 <span id="seatid"></span> 號的位置？
		</legend>

	</fieldset>
</div>
