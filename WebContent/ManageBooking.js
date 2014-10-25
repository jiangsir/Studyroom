jQuery(document).ready(function() {
	var insertWithoutPassword = $("#insertBookingDialogWithoutPassword");
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
			var seatid = $(this).attr("seatid");
			var span_seatid = insertWithoutPassword.find("span#seatid");
			span_seatid.html(seatid);
			insertWithoutPassword.dialog("open");
		});
	});
	insertWithoutPassword.dialog({
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
	var deleteWithoutPassword = $("#deleteBookingDialogWithoutPassword");
	$("div#deleteBooking").each(function() {
		$(this).click(function() {
			$("img#loader").show();
			var seatid = $(this).attr("seatid");
			deleteWithoutPassword.find("span#seatid").html(seatid);
			var studentid = $(this).attr("studentid");
			var date = $(this).attr("date");
			jQuery.ajax({
				type : "POST",
				url : "Attendance.api",
				data : "studentid=" + studentid + "&date=" + date,
				async : false,
				timeout : 5000,
				success : function(result) {
					// alert(result);
					$("img#loader").hide();
					deleteWithoutPassword.find("#attendance").html(result);
				}
			});

			deleteWithoutPassword.dialog("open");
		});
	});
	deleteWithoutPassword.dialog({
		autoOpen : false,
		width : 600,
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
	jQuery("button#rebuiltViolationsByDate").click(function() {
		$(this).closest("form").submit();
	});
	jQuery("button#doPunishingByDeleteBooking").click(function() {
		$(this).closest("form").submit();
	});
	jQuery("button#doPunished").click(function() {
		//alert("doPunished");
		$(this).closest("form").submit();
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

	// $("div[id='deleteBooking']").tooltip('#demotip');
	// $("div#deleteBooking").tooltip({
	// content : "Awesome title!"
	// });
	// $("div[id='deleteBooking']").hover(function() {
	// $(this).tooltip({
	// content : "asdf"
	// // content : function() {
	// // jQuery.ajax({
	// // type : "GET",
	// // url : "Attendance.api",
	// // data : "",
	// // async : false,
	// // timeout : 5000,
	// // success : function(result) {
	// // // alert(result);
	// // return result;
	// // }
	// // });
	// // }
	// });
	// // $(this).tooltip({
	// // position : {
	// // my : "center bottom-20",
	// // at : "center top",
	// // using : function(position, feedback) {
	// // $(this).css(position);
	// //
	// // }
	// // }
	// // });
	// });

});
