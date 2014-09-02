<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>
<%@taglib prefix="room" uri="/WEB-INF/room.tld"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<script type="text/javascript" src="Index.js"></script>
<script type="text/javascript"
	src="jscripts/functions/showErrorDialog.js"></script>
<script type="text/javascript"
	src="jscripts/functions/showLoadingDialog.js"></script>
</head>
<body>
	<jsp:include page="includes/dialog/Error.jsp" />
	<jsp:include page="includes/dialog/Loading.jsp" />
	<jsp:include page="includes/dialog/InsertBookingDialog.jsp" />
	<jsp:include page="includes/dialog/DeleteBookingDialog.jsp" />

	<jsp:include page="Header.jsp" />

	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center;">
		<h1>
			<fmt:formatDate value="${date}" pattern="yyyy-MM-dd (E)" />
			的訂位狀況
		</h1>
		<c:if test="${sessionScope.currentUser.isAdmin}">
			<div style="margin: 1em;">
				<a href="?date=${prevdate }" type="button">前一日</a> <a href="?"
					type="button">今天</a> <a href="?date=${nextdate}" type="button">後一日</a>
			</div>
		</c:if>
	</div>
	<c:if test="${fn:trim(appConfig.announcement)!=''}">
		<fieldset style="width: 60%; margin: auto; margin-bottom: 1em;">
			<legend>說明：</legend>
			<pre>${applicationScope.appConfig.announcement}</pre>
		</fieldset>
	</c:if>
	<c:choose>
		<c:when test="${room:isOpen(date) }">
			<table style="border: 0px;">
				<c:set var="grouplist" value="${fn:split('26,24,20', ',')}" />
				<c:set var="base" value="0" />
				<c:forEach var="group" items="${grouplist }">
					<c:forEach var="row" begin="1" end="2" step="1">
						<tr>
							<c:forEach var="col" begin="1" end="${group}" step="1">
								<c:set var="seatid" value="${base+(row-1)*group+col}" />
								<td><c:set var="bookupMap" value="${bookupMap}"
										scope="request" /> <jsp:include page="includes/div/Seat.jsp">
										<jsp:param name="seatid" value="${seatid }" />
									</jsp:include> <%-- <c:choose>
										<c:when test="${!seat:canBookup(date)}">
											<button class="seat">
												<fmt:formatNumber pattern="000" value="${seatid}" />
											</button>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${seat:isOccupied(bookupMap, seatid)}">
													<div class="deleteBooking">${seat:studentid(bookupMap,
														seatid)}</div>
												</c:when>
												<c:otherwise>
													<button id="insertBooking" class="seat" seatid=${seatid}>
														搶
														<fmt:formatNumber pattern="000" value="${seatid}" />
													</button>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose> --%></td>
							</c:forEach>
						</tr>
					</c:forEach>
					<c:set var="base" value="${base+group*2 }" />

					<tr style="height: 1em;">
					</tr>
				</c:forEach>
			</table>

		</c:when>
		<c:otherwise>
			<div style="text-align: center;">
				<h1>今日不開放訂位哦！</h1>
			</div>
		</c:otherwise>
	</c:choose>

	<jsp:include page="Footer.jsp" />
</body>
</html>