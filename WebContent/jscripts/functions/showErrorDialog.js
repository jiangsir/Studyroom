function showErrorDialog(jqXHR, textStatus, errorThrown) {

	var errorjson;
	if (jqXHR.responseText !== '') {
		errorjson = jQuery.parseJSON(jqXHR.responseText);
	} else {
		errorjson = errorThrown;
	}

	var error_dialog = $("div.error_dialog");
	error_dialog.find("#title").html(errorjson.title);
	error_dialog.find("#subtitle").html(errorjson.subtitle);
	error_dialog.find("#content").html(errorjson.content);
	var $dialog = error_dialog.dialog({
		autoOpen : false,
		modal : true,
		width : '40%',
		title : errorjson.type,
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
