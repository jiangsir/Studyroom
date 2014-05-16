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

		$(".insertBooking").each(
				function() {
					$(this).click(
							function() {
								$("div#insertBookingDialog span#seatid").html(
										$(this).attr("seatid"));
								$("#insertBookingDialog").dialog("open");
							});
				});
		$("#insertBookingDialog").dialog(
				{
					autoOpen : false,
					height : 400,
					width : 350,
					modal : true,
					buttons : {
						"訂位" : function() {
							var studentid = $(this).find(
									"input[name='studentid']").val();
							var passwd = $(this).find("input[name='passwd']")
									.val();
							var seatid = $(this).find("span[id='seatid']")
									.text();

							jQuery.ajax({
								type : "POST",
								url : "BookUp",
								data : "studentid=" + studentid + "&passwd="
										+ passwd + "&seatid=" + seatid,
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

		$(".deleteBooking").each(
				function() {
					$(this).click(
							function() {
								$("div#deleteBookingDialog span#seatid").html(
										$(this).attr("seatid"));
								$("#deleteBookingDialog").dialog("open");
							});
				});
		$("#deleteBookingDialog").dialog(
				{
					autoOpen : false,
					height : 400,
					width : 350,
					modal : true,
					buttons : {
						"取消訂位" : function() {
							var studentid = $(this).find(
									"input[name='studentid']").val();
							var passwd = $(this).find("input[name='passwd']")
									.val();
							var seatid = $(this).find("span[id='seatid']")
									.text();

							jQuery.ajax({
								type : "POST",
								url : "Cancel",
								data : "studentid=" + studentid + "&passwd="
										+ passwd + "&seatid=" + seatid,
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
	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center;">
		<a href="?date=${prevdate }" type="button">上個月</a> <a
			href="?date=${nextdate}" type="button">下個月</a>
		<p></p>
		<table style="margin: auto;">
			<c:forEach var="row" begin="1" end="6" step="1">
				<tr style="line-height: 1em;">
					<c:forEach var="col" begin="1" end="7" step="1">
						<td style="padding: 1em; font-size: 1.2em;">${col+(row-1)*7}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>