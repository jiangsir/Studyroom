jQuery(document).ready(function() {
	var cancelViolationDialog = jQuery("#cancelViolationDialog");
	cancelViolationDialog.dialog({
		autoOpen : false,
		width : 600,
		modal : true,
		buttons : {
			"送出" : function() {
				event.preventDefault(); // 讓預設的動作失效！
				var form = $(this).find("form");
				form.submit();

				// jQuery.ajax({
				// type : "POST",
				// url : "Violation.api",
				// data : "action=cancelViolation&" + form.serialize(),
				// async : false,
				// timeout : 5000,
				// beforeSend : function() {
				// },
				// success : function(result) {
				// violationsDialog.html(result);
				// }
				// });

				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	jQuery("button#cancelViolation").click(function() {
		var violationid = $(this).attr("violationid");
		cancelViolationDialog.find("input[name='violationid']").val(violationid);
		cancelViolationDialog.dialog("open");
		// jQuery.ajax({
		// type : "POST",
		// url : "Violation.api",
		// data : "action=cancelViolation&violationid=" + violationid,
		// async : false,
		// timeout : 5000,
		// beforeSend : function() {
		// },
		// success : function(result) {
		// location.reload();
		// }
		// });

	});

});
