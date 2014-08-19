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

div.deleteBooking {
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
	display: table-cell;
}
</style>

<div class="seat" seatid="${param.seatid}">
	<c:choose>
		<c:when test="${!seat:canBookup(date)}">
			<c:if test="${seat:isOccupied(bookupMap, param.seatid)}">
				<div class="seat">${seat:studentid(bookupMap, param.seatid)}</div>
			</c:if>
			<c:if test="${!seat:isOccupied(bookupMap, param.seatid)}">
				<button class="seat">
					<fmt:formatNumber pattern="000" value="${param.seatid}" />
				</button>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${seat:isOccupied(bookupMap, param.seatid)}">
					<div class="deleteBooking">${seat:studentid(bookupMap,
						param.seatid)}</div>
				</c:when>
				<c:otherwise>
					<button id="insertBooking" class="seat" seatid=${param.seatid}>
						搶
						<fmt:formatNumber pattern="000" value="${param.seatid}" />
					</button>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</div>