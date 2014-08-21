jQuery(document).ready(function() {
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

	$("button#insertBooking").each(function() {
		$(this).click(function() {
			var seatid = $(this).attr("seatid");
			$("div#insertBookingDialog span#seatid").html(seatid);
			$("#insertBookingDialog").dialog("open");
		});
	});
	$("#insertBookingDialog").dialog({
		autoOpen : false,
		modal : true,
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
					url : "Booking.api",
					data : "action=booked&studentid=" + studentid + "&passwd=" + passwd + "&seatid=" + seatid,
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

	$("div#deleteBooking").each(function() {
		$(this).click(function() {
			var seatid = $(this).attr("seatid");
			$("div#deleteBookingDialog span#seatid").html(seatid);
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
					url : "Booking.api",
					data : "action=cancel&studentid=" + studentid + "&passwd=" + passwd + "&seatid=" + seatid,
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
