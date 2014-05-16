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

		$("#addRow").click(function() {
			var lastRow = $("li#row").last();
			var newRow = lastRow.clone(true).insertAfter(lastRow);
			// 新的 row input 全部清空。
			newRow.find("input").val("");
			// 新的 row button 重新 hover
			newRow.find("button").hover();
			newRow.find("#removeRow").on('click', function(event) {
				if ($("li#row").size() > 1) {
					$(this).parent().remove();
				}
				event.preventDefault(); // 讓預設的動作失效！
			});

			// datepicker 在 clone 時的主要問題是 id 重複。
			// 先將原本的 datepicker 消除。
			// 並且移除 id 在 init datepicker 這時它會自動再產生一個新的不重復的 id. 
			newRow.find('input.datepicker').datepicker("destroy");
			newRow.find('input.datepicker').removeAttr('id').datepicker({
				dateFormat : 'yy-mm-dd'
			});
		});

		$("button#removeRow").click(function() {
			if ($("li#row").size() > 1) {
				$(this).parent().remove();
			}
		});

		$("#addRow2").click(function() {
			var lastRow = $("li#row2").last();
			var newRow = lastRow.clone(true).insertAfter(lastRow);
			// 新的 row input 全部清空。
			newRow.find("input").val("");
			// 新的 row button 重新 hover
			newRow.find("button").hover();
			newRow.find("#removeRow2").on('click', function(event) {
				if ($("li#row2").size() > 1) {
					$(this).parent().remove();
				}
				event.preventDefault(); // 讓預設的動作失效！
			});

			// datepicker 在 clone 時的主要問題是 id 重複。
			// 先將原本的 datepicker 消除。
			// 並且移除 id 在 init datepicker 這時它會自動再產生一個新的不重復的 id. 
			newRow.find('input.datepicker').datepicker("destroy");
			newRow.find('input.datepicker').removeAttr('id').datepicker({
				dateFormat : 'yy-mm-dd'
			});
		});

		$("button#removeRow2").click(function() {
			if ($("li#row2").size() > 1) {
				$(this).parent().remove();
			}
		});

		$("input.datepicker").datepicker({
			dateFormat : 'yy-mm-dd'
		});

		$("input#addBatchBookings").click(function(event) { // 事件發生
			event.preventDefault();
			jQuery.ajax({
				type : "POST",
				url : "addBatchBookings",
				data : $('form#addBatchBookings').serialize(),
				dataType : "json",
				async : false,
				timeout : 5000,
				success : function(result) {
				}
			});
		});

		$("input#deleteBatchBookings").click(function(event) { // 事件發生
            event.preventDefault();
            jQuery.ajax({
                type : "POST",
                url : "deleteBatchBookings",
                data : $('form#deleteBatchBookings').serialize(),
                dataType : "json",
                async : false,
                timeout : 5000,
                success : function(result) {
                }
            });
        });

	});
</script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<div>新增長期訂位！</div>
	<button id="addRow" style="font-size: 0.8em;">＋增加一列</button>
	<form id="addBatchBookings">
		<ul>
			<li id="row">學號：<input name="studentid" />: 座位號碼：<input
				name="seatid" />: 由<input class="datepicker" name="begindate" />日
				到 <input class="datepicker" name="enddate" /> 日
				<button id="removeRow" class="closethick"
					style="display: inline; vertical-align: middle; font-size: 0.5em;">移除本列</button>
			</li>
		</ul>
	</form>
	<div style="display: inline;">
		<input id="addBatchBookings" type="submit" value="全部訂位"></input>
	</div>
	<p></p>
	<button id="addRow2" style="font-size: 0.8em;">＋增加一列</button>
	<form id="deleteBatchBookings">
		<ul>
			<li id="row2">學號：<input name="studentid" />: 由<input
				class="datepicker" name="begindate" />日 到 <input class="datepicker"
				name="enddate" /> 日
				<button id="removeRow2" class="closethick"
					style="display: inline; vertical-align: middle; font-size: 0.5em;">移除本列</button>
			</li>
		</ul>
	</form>

	<div style="display: inline;">
		<input id="deleteBatchBookings" type="submit" value="全部退訂"></input>
	</div>
	<p></p>
	<div>
		學號：<input />: 訂位由 ??? 日 到 ??? 日 （在這段時間當中只要有訂位，就取消，沒有訂位就跳過。）
	</div>
	<div>是否有以人為準的訂位表，列出這個人所有訂位日期跟座位號碼。</div>
	<div>快速取消訂位！（可以由 ManageBooking 來處理。依據日期取出座位表，直接點選取消，不必認證。）</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>