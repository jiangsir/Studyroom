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
<script type="text/javascript"
	src="jscripts/functions/showErrorDialog.js"></script>
<script type="text/javascript" src="ManageBooking.js"></script>

<style type="text/css">
.seat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
	box-sizing: border-box;
	border: 1px solid #000000;
}

.unseat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
	box-sizing: border-box;
	border: 1px solid #000000;
	border-radius: 5px;
	text-align: center;
	vertical-align: middle;
	line-height: 1.4em;
	/*
    display: table-cell;
    */
}

#demotip {
	display: none;
	/*background: transparent url(images/black_arrow.png);*/
	font-size: 12px;
	height: 70px;
	width: 160px;
	padding: 25px;
	color: #fff;
}
</style>
</head>
<body>
	<jsp:include page="includes/dialog/Error.jsp" />
	<jsp:include
		page="includes/dialog/InsertBookingDialogWithoutPassword.jsp" />
	<jsp:include
		page="includes/dialog/DeleteBookingDialogWithoutPassword.jsp" />
	<jsp:include page="Header.jsp" />
	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center; margin: 1em;">
		<h1>
			訂位管理
			<fmt:formatDate value="${date}" pattern="yyyy-MM-dd (E)" />
			(
			<fmt:formatNumber pattern="#.#"
				value="${fn:length(hashBookings)/140*100}" />
			%)

		</h1>
		<a href="?date=${prevdate }" type="button">前一日</a> <a href="?"
			type="button">今天</a> <a href="?date=${nextdate}" type="button">後一日</a>
	</div>
	<table style="border: 0px; padding-right: 5em;">
		<c:set var="grouplist" value="${fn:split('26,24,20', ',')}" />
		<c:set var="base" value="0" />
		<c:forEach var="group" items="${grouplist }">
			<c:forEach var="row" begin="1" end="2" step="1">
				<tr>
					<c:forEach var="col" begin="1" end="${group}" step="1">
						<c:set var="seatid" value="${base+(row-1)*group+col}" />
						<td>
							<div seatid="${seatid}">
								<c:set var="seatidString">${seatid}</c:set>
								<!-- 把seatid 轉換成 string -->
								<c:choose>
									<c:when test="${hashBookings[seatidString].isBooked}">
										<div id="deleteBooking" class="unseat" seatid="${seatid }"
											studentid="${hashBookings[seatidString].studentid}"
											date="${param.date}">
											<div id="demotip">&nbsp;</div>
											<c:choose>
												<c:when
													test="${hashBookings[seatidString].attendance.isSignIn}">
													<div style="margin-top: 0.6em; color: #00aa00">
														${hashBookings[seatidString].studentid } <span
															style="font-weight: bold;"> <fmt:formatNumber
																pattern="000" value="${seatid}" /></span>
													</div>
												</c:when>
												<c:when
													test="${hashBookings[seatidString].attendance.isSignOut}">
													<div style="margin-top: 0.6em; color: red;">
														${hashBookings[seatidString].studentid } <span
															style="font-weight: bold;"> <fmt:formatNumber
																pattern="000" value="${seatid}" /></span>
													</div>
												</c:when>
												<c:otherwise>
													<div style="margin-top: 0.6em; color: #666666;">
														${hashBookings[seatidString].studentid } <span
															style="font-weight: bold;"> <fmt:formatNumber
																pattern="000" value="${seatid}" /></span>
													</div>

												</c:otherwise>
											</c:choose>
										</div>
									</c:when>
									<c:otherwise>
										<button id="insertBooking" class="seat" seatid=${seatid}>
											<fmt:formatNumber pattern="000" value="${seatid}" />
										</button>
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
			<c:set var="base" value="${base+group*2 }" />

			<tr style="height: 1em;">
			</tr>
		</c:forEach>
	</table>

	<div>
		<span style="color: green;">綠色代表已簽到</span>，<span style="color: red;">紅色代表已簽退</span>，黑色代表已訂位但未簽到
	</div>
	<div>總訂位人數：${fn:length(hashBookings)} 人。</div>
	<div>簽到人數： ${attendCount } 人。</div>
	<div>未簽到人數：${fn:length(hashBookings)-attendCount }人。</div>
	<form action="Violation.api?action=rebuiltViolationsByDate" method="post">
		<input name="date" value=${date } />
		<button id="rebuiltViolationsByDate">立即計算違規狀態</button>
	</form>

	<!-- 
	加入圖片放大的功能。
	jquery.hoverpulse.js
	 -->
	<jsp:include page="Footer.jsp" />
</body>
</html>