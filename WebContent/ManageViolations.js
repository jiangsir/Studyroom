jQuery(document).ready(function() {
	var violationsDialog = jQuery("#violationsDialog");
	violationsDialog.dialog({
		autoOpen : false,
		width : 600,
		modal : true,
		buttons : {
			"返回" : function() {
				$(this).dialog("close");
			}
		}
	});

	jQuery("td[id='violationsDialog']").click(function() {
		event.preventDefault(); // 讓預設的動作失效！
		var studentid = $(this).attr("studentid");
		jQuery.ajax({
			type : "GET",
			url : "Violation.api",
			data : "action=getViolationsByStudentid&studentid=" + studentid,
			async : false,
			timeout : 5000,
			beforeSend : function() {
			},
			success : function(result) {
				violationsDialog.html(result);
			}
		});

		violationsDialog.dialog("open");
	});

});
