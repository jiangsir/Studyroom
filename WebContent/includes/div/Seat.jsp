<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>

<%@ page isELIgnored="false"%>
<style type="text/css">
.seat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
}

.unseat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
	box-sizing: border-box;
	border: 1px solid #bbbbbb;
	border-radius: 5px;
	text-align: center;
	vertical-align: middle;
	line-height: 1.4em;

	/*
	display: table-cell;
	*/
}
</style>

<div seatid="${param.seatid}" id="seat">
	<c:choose>
		<c:when test="${!seat:canBookup(date)}">
			<c:if test="${hashBookings[param.seatid].isBooked}">
				<div class="unseat">
					<c:choose>
						<c:when test="${hashBookings[param.seatid].attendance.isSignIn}">
							<div style="margin-top: 0.6em; color: #00aa00">
								${hashBookings[param.seatid].studentid }</div>
						</c:when>
						<c:when test="${hashBookings[param.seatid].attendance.isSignOut}">
							<div style="margin-top: 0.6em; color: red;">
								${hashBookings[param.seatid].studentid }</div>
						</c:when>
						<c:otherwise>
							<div style="margin-top: 0.6em; color: #666666;">
								${hashBookings[param.seatid].studentid }</div>
						</c:otherwise>
					</c:choose>
					<span style="font-weight: bold;"> <fmt:formatNumber
							pattern="000" value="${param.seatid}" /></span>
				</div>
			</c:if>
			<c:if test="${!hashBookings[param.seatid].isBooked}">
				<button class="seat">
					<fmt:formatNumber pattern="000" value="${param.seatid}" />
				</button>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${hashBookings[param.seatid].isBooked}">
					<div id="deleteBooking" class="unseat" seatid=${param.seatid}>
						<div style="margin-top: 0.6em;">
							<c:choose>
								<c:when test="${hashBookings[param.seatid].attendance.isSignIn}">
									<div style="margin-top: 0.6em; color: #00aa00">
										${hashBookings[param.seatid].studentid }</div>
								</c:when>
								<c:when
									test="${hashBookings[param.seatid].attendance.isSignOut}">
									<div style="margin-top: 0.6em; color: red;">
										${hashBookings[param.seatid].studentid }</div>
								</c:when>
								<c:otherwise>
									<div style="margin-top: 0.6em; color: #666666;">
										${hashBookings[param.seatid].studentid }</div>
								</c:otherwise>
							</c:choose>
							<span style="font-weight: bold;"> <fmt:formatNumber
									pattern="000" value="${param.seatid}" /></span>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<button id="insertBooking" class="seat" seatid=${param.seatid}>
						搶<br />
						<fmt:formatNumber pattern="000" value="${param.seatid}" />
					</button>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</div>