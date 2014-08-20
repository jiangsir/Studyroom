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

<script>
	$(function() {
		$.urlParam = function(name) {
			var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
			if (results == null) {
				return null;
			} else {
				return results[1] || 0;
			}
		}
		$("button#insertBooking").each(function() {
			$(this).click(function() {
				var seatid = $(this).closest("div.seat").attr("seatid");
				$("div#insertBookingDialogWithoutPassword span#seatid").html(seatid);
				$("#insertBookingDialogWithoutPassword").dialog("open");
			});
		});
		$("#insertBookingDialogWithoutPassword").dialog({
			autoOpen : false,
			modal : true,
			width : 350,
			modal : true,
			buttons : {
				"訂位" : function() {
					var studentid = $(this).find("input[name='studentid']").val();
					var seatid = $(this).find("span[id='seatid']").text();
					var date = jQuery.urlParam("date");

					jQuery.ajax({
						type : "POST",
						url : "Booking.api",
						data : "action=booked&studentid=" + studentid + "&seatid=" + seatid + "&date=" + date,
						async : false,
						timeout : 5000,
						success : function(result) {
							location.reload();
						},
						error : function(jqXHR, textStatus, errorThrown) {
							showErrorDialog(jqXHR, textStatus, errorThrown);
						}
					});
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		});

		$(".deleteBooking").each(function() {
			$(this).click(function() {
				var seatid = $(this).closest("div.seat").attr("seatid");
				$("div#deleteBookingDialogWithoutPassword span#seatid").html(seatid);
				$("#deleteBookingDialogWithoutPassword").dialog("open");
			});
		});
		$("#deleteBookingDialogWithoutPassword").dialog({
			autoOpen : false,
			width : 350,
			modal : true,
			buttons : {
				"取消訂位" : function() {
					var seatid = $(this).find("span[id='seatid']").text();
					var date = jQuery.urlParam("date");
					jQuery.ajax({
						type : "POST",
						url : "Booking.api",
						data : "action=cancel&seatid=" + seatid + "&date=" + date,
						async : false,
						timeout : 5000,
						beforeSend : function() {
						},
						success : function(result) {
							location.reload();
						},
						error : function(jqXHR, textStatus, errorThrown) {
							showErrorDialog(jqXHR, textStatus, errorThrown);
						}
					});
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}

			}
		});

		$("button[id='icon01']").button({
			icons : {
				primary : "ui-icon-closethick"
			},
			text : false
		});
		$("button[id='icon02']").button({
			icons : {
				primary : "ui-icon-check"
			},
			text : false
		});

	});
</script>
<style type="text/css">
.seat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
	border-color: black;
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

	/*     display: table-cell;
	vertical-align: middle;
 */
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
			每日訂位管理 （
			<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />
			）

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
							<div class="seat" seatid="${seatid}">
								<c:choose>
									<c:when test="${seat:isOccupied(bookupMap, seatid)}">
										<div class="deleteBooking">${seat:studentid(bookupMap,
											seatid)}</div>
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
	<!-- 
	加入圖片放大的功能。
	jquery.hoverpulse.js
	 -->
	<jsp:include page="Footer.jsp" />
</body>
</html>