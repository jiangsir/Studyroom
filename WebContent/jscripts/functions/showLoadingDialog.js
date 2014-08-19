function showLoadingDialog() {

	var loading_dialog = $("div.loading_dialog");
	var $dialog = loading_dialog.dialog({
		autoOpen : false,
		modal : true,
		width : '40%',
		title : "Loading...",
		close : function() {
			$(this).dialog("destroy");
		},
		buttons : {
			"返回" : function() {
				$(this).dialog("destroy");
			}
		}
	});
	$dialog.dialog('open');
	return false;
}
