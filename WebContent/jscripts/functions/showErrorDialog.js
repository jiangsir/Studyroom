function showErrorDialog(jqXHR, textStatus, errorThrown) {

	var error;
	if (jqXHR.responseText !== '') {
		error = jqXHR.responseText;
	} else {
		error = errorThrown;
	}

	var error_dialog = $("div.error_dialog");
	error_dialog.find("h2").html(error);
	var $dialog = error_dialog.dialog({
		autoOpen : false,
		width : '40%',
		title : 'Error Message',
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
