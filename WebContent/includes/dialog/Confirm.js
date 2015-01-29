jQuery(document).ready(function() {

	// jQuery("form input[type='submit']").click(function() {
	// var form = $(this).closest("form");
	// var $confirm_dialog = form.find(".confirm_dialog");
	// var $dialog = $confirm_dialog.dialog({
	// autoOpen : false,
	// width : '60%',
	// title : $(this).val(),
	// buttons : {
	// "確定" : function() {
	// form.submit();
	// },
	// "取消" : function() {
	// $(this).dialog("destroy");
	// }
	// }
	// });
	// $dialog.dialog('open');
	// return false;
	// });

	jQuery("button[type='confirm']").click(function() {
		var confirm = jQuery("button[type='confirm']");
		var index = confirm.index(this);
		var $confirm_dialog = $(".confirm_dialog:eq(" + index + ")");
		// var $title = $confirm_dialog.attr("title");
		var $type = $confirm_dialog.attr("type");
		var $url = $confirm_dialog.attr("url");
		var $data = $confirm_dialog.attr("data");

		var $dialog = $confirm_dialog.dialog({
			autoOpen : false,
			width : '60%',
			title : confirm.text(),
			buttons : {
				"確定" : function() {
					jQuery.ajax({
						type : $type,
						url : $url,
						data : $data,
						async : false,
						timeout : 5000,
						success : function(result) {
						}
					}); // jQuery ajax
					$(this).dialog("destroy");
					location.reload();
				},
				"取消" : function() {
					$(this).dialog("destroy");
				}
			}
		});
		$dialog.dialog('open');
		return false;
	});

});
