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

	$("#insertBooking").each(function() {
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
						$(this).dialog("destroy");
						location.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						showErrorDialog(jqXHR, textStatus, errorThrown);
					}
				});
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
