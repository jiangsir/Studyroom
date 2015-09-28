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
			$("#insertBookingDialog").find("input").val("");
			jQuery.getJSON("user.api?action=getCurrentUser", function(currentUser) {
				if (currentUser != null && currentUser.isGoogleUser) {
					jQuery.ajax({
						type : "POST",
						url : "Booking.api",
						data : "action=bookingByGoogleLogin&seatid=" + seatid,
						async : false,
						timeout : 5000,
						success : function(result) {
							location.reload();
						},
						error : function(jqXHR, textStatus, errorThrown) {
							showErrorDialog(jqXHR, textStatus, errorThrown);
						}
					});
				} else {
					$("#insertBookingDialog").dialog("open");
					// location.href = "GoogleLogin";
				}
			});
		});
	});
	$("#insertBookingDialog").dialog({
		autoOpen : false,
		modal : true,
		height : 350,
		width : '50%',
		modal : true,
		buttons : {
			// "GoogleLogin 訂位" : function() {
			// var seatid = $(this).find("span[id='seatid']").text();
			// jQuery.ajax({
			// type : "POST",
			// url : "Booking.api",
			// data : "action=bookingByGoogleLogin&seatid=" + seatid,
			// async : false,
			// timeout : 5000,
			// success : function(result) {
			// location.reload();
			// },
			// error : function(jqXHR, textStatus, errorThrown) {
			// showErrorDialog(jqXHR, textStatus, errorThrown);
			// }
			// });
			// $(this).dialog("close");
			// },
			"登入學生信箱" : function() {
				location.href = "GoogleLogin";
			},
			// "訂位" : function() {
			// var studentid = $(this).find("input[name='studentid']").val();
			// var passwd = $(this).find("input[name='passwd']").val();
			// var seatid = $(this).find("span[id='seatid']").text();
			//
			// jQuery.ajax({
			// type : "POST",
			// url : "Booking.api",
			// data : "action=booked&studentid=" + studentid + "&passwd=" +
			// passwd + "&seatid=" + seatid,
			// async : false,
			// timeout : 5000,
			// success : function(result) {
			// location.reload();
			// },
			// error : function(jqXHR, textStatus, errorThrown) {
			// showErrorDialog(jqXHR, textStatus, errorThrown);
			// }
			// });
			// $(this).dialog("close");
			// },
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	var deleteBookingDialog = jQuery("div#deleteBookingDialog");
	$("div#deleteBooking").click(function() {
		// $(this).click(function() {
		var seatid = $(this).attr("seatid");
		var studentid = $(this).attr("studentid");
		// alert(studentid);
		getViolationsByStudentid(deleteBookingDialog, studentid);
		deleteBookingDialog.find("#seatid").html(seatid);
		deleteBookingDialog.find("input").val("");
		deleteBookingDialog.find("input[name='studentid']").val(studentid);
		jQuery.getJSON("user.api?action=getCurrentUser", function(currentUser) {
			if (currentUser != null && currentUser.isGoogleUser) {
				jQuery.ajax({
					type : "POST",
					url : "Booking.api",
					data : "action=cancelByGoogleLogin&seatid=" + seatid,
					async : false,
					timeout : 5000,
					success : function(result) {
						location.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						showErrorDialog(jqXHR, textStatus, errorThrown);
					}
				});
			} else {
				deleteBookingDialog.dialog("open");
			}
		});
		// });
	});
	deleteBookingDialog.dialog({
		autoOpen : false,
		height : 350,
		width : '50%',
		modal : true,
		buttons : {
			"登入學生信箱" : function() {
				location.href = "GoogleLogin";
			},

			// "取消訂位" : function() {
			// var studentid = $(this).find("input[name='studentid']").val();
			// var passwd = $(this).find("input[name='passwd']").val();
			// var seatid = $(this).find("span[id='seatid']").text();
			//
			// jQuery.ajax({
			// type : "POST",
			// url : "Booking.api",
			// data : "action=cancel&studentid=" + studentid + "&passwd=" +
			// passwd + "&seatid=" + seatid,
			// async : false,
			// timeout : 5000,
			// beforeSend : function() {
			// },
			// success : function(result) {
			// location.reload();
			// },
			// error : function(jqXHR, textStatus, errorThrown) {
			// showErrorDialog(jqXHR, textStatus, errorThrown);
			// }
			// });
			// $(this).dialog("close");
			// },
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

	var message_dialog = jQuery("div#message_dialog");
	message_dialog.dialog({
		autoOpen : false,
		width : 600,
		modal : true,
		buttons : {
			"返回" : function() {
				$(this).dialog("close");
			}

		}
	});

	// $("div[id='seat']").mouseover(function() {
	// var studentid = $(this).attr("studentid");
	// getViolationsByStudentid(message_dialog, studentid);
	// // $("span#mouseover").text($(this).attr("seatid") + " over");
	// }).mouseleave(function() {
	// // $("span#mouseover").text($(this).attr("seatid") + " leave");
	// jQuery("div#message_dialog").dialog("close");
	// });

});

function getViolationsByStudentid(deleteBookingDialog, studentid) {
	jQuery.ajax({
		type : "GET",
		url : "Violation.api",
		data : "action=getViolationsByStudentid&studentid=" + studentid,
		async : false,
		timeout : 5000,
		beforeSend : function() {
		},
		success : function(result) {
			deleteBookingDialog.find("#violationQueue").html(result);
		}
	});

}
