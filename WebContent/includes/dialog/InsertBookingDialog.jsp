<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page isELIgnored="false"%>

<div id="insertBookingDialog" title="請輸入帳號密碼！" style="display: none;">
	<fieldset style="padding: 5px;">
		<legend>
			確定要訂位 <span id="seatid"></span> 號的位置？
		</legend>
		<br />新生或從未使用過學生信箱的同學請務必先登入自己的信箱，同意使用條款後帳號才會開通，接下來才能在這裡訂位。<br /> <label>請輸入學號</label><br />
		<input type="text" name="studentid" size="8" value=""></input>@${applicationScope.appConfig.checkhost}<br />
		<label>請輸入學生信箱密碼</label><br /> <input type="password" name="passwd"
			value="" style="width: 90%;"></input> <br />
	</fieldset>
</div>
