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

<script>
	$(function() {
		$("#dialog-confirm").dialog({
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			buttons : {
				"+加入" : function() {
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#opendialog").click(function() {
			$("#dialog-confirm").dialog("open");
		});

		$(".insertBooking").each(function() {
			$(this).click(function() {
				$("div#insertBookingDialog span#seatid").html($(this).attr("seatid"));
				$("#insertBookingDialog").dialog("open");
			});
		});
		$("#insertBookingDialog").dialog({
			autoOpen : false,
			height : 400,
			width : 350,
			modal : true,
			buttons : {
				"訂位" : function() {
					var studentid = $(this).find("input[name='studentid']").val();
					var passwd = $(this).find("input[name='passwd']").val();
					var seatid = $(this).find("span[id='seatid']").text();

					jQuery.ajax({
						type : "POST",
						url : "BookUp",
						data : "studentid=" + studentid + "&passwd=" + passwd + "&seatid=" + seatid,
						async : false,
						timeout : 5000,
						success : function(result) {
							location.reload();
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
				$("div#deleteBookingDialog span#seatid").html($(this).attr("seatid"));
				$("#deleteBookingDialog").dialog("open");
			});
		});
		$("#deleteBookingDialog").dialog({
			autoOpen : false,
			height : 400,
			width : 350,
			modal : true,
			buttons : {
				"取消訂位" : function() {
					var studentid = $(this).find("input[name='studentid']").val();
					var passwd = $(this).find("input[name='passwd']").val();
					var seatid = $(this).find("span[id='seatid']").text();

					jQuery.ajax({
						type : "POST",
						url : "Cancel",
						data : "studentid=" + studentid + "&passwd=" + passwd + "&seatid=" + seatid,
						async : false,
						timeout : 5000,
						success : function(result) {
							location.reload();
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
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div id="insertBookingDialog" title="請輸入帳號密碼！" style="display: none;">
		<fieldset style="padding: 5px;">
			<legend>
				確定要訂位 <span id="seatid"></span> 號的位置？
			</legend>
			<br /> <label>請輸入學號</label><br /> <input type="text"
				name="studentid" value="" style="width: 90%;"></input><br /> <label>請輸入學生信箱密碼
				(stu.nknush.kh.edu.tw)</label><br /> <input type="text" name="passwd"
				value="" style="width: 90%;"></input> <br />
		</fieldset>
	</div>
	<div id="deleteBookingDialog" title="請輸入帳號密碼！" style="display: none;">
		<fieldset style="padding: 5px;">
			<legend>
				確定要取消訂位 <span id="seatid"></span> 號的位置？
			</legend>
			<br /> <label>請輸入學號</label><br /> <input type="text"
				name="studentid" value="" style="width: 90%;"></input><br /> <label>請輸入學生信箱密碼
				(stu.nknush.kh.edu.tw)</label><br /> <input type="text" name="passwd"
				value="" style="width: 90%;"></input> <br />
		</fieldset>
	</div>
	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center;">
		<h1>
			<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />
			的訂位狀況
		</h1>
		<a href="?date=${prevdate }" type="button">前一日</a> <a href="?"
			type="button">今天</a> <a href="?date=${nextdate}" type="button">後一日</a>
	</div>
	<div>已被訂位: ${bookupMap } ${applicationScope.appConfig.starttime }
		: ${applicationScope.appConfig.deadline }</div>
	<div>date: ${date}, canBookup: ${seat:canBookup(date)}</div>
	<table style="border: 0px; padding-right: 5em;">
		<c:set var="grouplist" value="${fn:split('26,24,20', ',')}" />
		<c:set var="base" value="0" />
		<c:forEach var="group" items="${grouplist }">
			<c:forEach var="row" begin="1" end="2" step="1">
				<tr>
					<c:forEach var="col" begin="1" end="${group}" step="1">
						<c:set var="seatid" value="${base+(row-1)*group+col}" />
						<td><c:choose>
								<c:when test="${!seat:canBookup(date)}">
									<c:if test="${seat:isOccupied(bookupMap, seatid)}">
										<span>${seat:studentid(bookupMap, seatid)}</span>
									</c:if>
									<c:if test="${!seat:isOccupied(bookupMap, seatid)}">
										<button style="font-size: 0.8em; height: 4em;">
											<fmt:formatNumber pattern="000" value="${seatid}" />
										</button>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${seat:isOccupied(bookupMap, seatid)}">
											<span class="deleteBooking">${seat:studentid(bookupMap,
												seatid)}</span>
										</c:when>
										<c:otherwise>
											<button class="insertBooking"
												style="font-size: 0.8em; height: 4em;" seatid=${seatid}>
												搶
												<fmt:formatNumber pattern="000" value="${seatid}" />
											</button>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose></td>
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