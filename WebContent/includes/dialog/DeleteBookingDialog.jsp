<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>

<div id="deleteBookingDialog" title="請輸入帳號密碼！" style="display: none;">
	<fieldset style="padding: 5px;">
		<legend>
			確定要取消訂位 <span id="seatid"></span> 號的位置？
		</legend>
		<br /> <label>請輸入學號</label><br /> <input type="text"
			name="studentid" value="" style="width: 90%;"></input><br /> <label>請輸入學生信箱密碼
			(${applicationScope.appConfig.checkhost})</label><br /> <input
			type="password" name="passwd" value="" style="width: 90%;"></input> <br />
	</fieldset>
	<hr>
	<jsp:include page="../div/ViolationQueue.jsp" />

</div>
