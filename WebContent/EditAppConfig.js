// JavaScript Document
jQuery(document).ready(function() {
	jQuery("span[id='uploadimage']").click(function() {
		var $dialog = $("#upload_dialog").dialog({
			autoOpen : false,
			width : '60%',
			title : 'Upload Image',
			buttons : {
				"取消" : function() {
					$(this).dialog("close");
				},
				"上傳" : function() {
					$(this).dialog("close");
					UploadImage();
				}
			}
		});
		$dialog.dialog('open');
		return false;
	});

	$("#tabs").tabs({
		select : function(event, ui) {
			var url = $.data(ui.tab, 'load.tabs');
			if (url) {
				location.href = url;
				return false;
			}
			return true;
		}
	});

	jQuery("#SystemClosedMessage").focus(function() {
		$("#SYSTEM_CLOSE").attr("checked", true);
	});

});

function UploadImage() {
	jQuery.ajax({
		type : "POST",
		url : "./UploadImage",
		data : $('#form').serialize(),
		async : false,
		timeout : 5000,
		success : function(result) {
			// alert(result);
			var json = JSON.parse(result);
			jQuery("#Testjudge_dialog3 #Testjudge_htmlstatus").text(json.htmlstatus);
			jQuery("#Testjudge_dialog3 #Testjudge_result").text(json.result);
			var $dialog3 = $("#Testjudge_dialog3").dialog({
				autoOpen : true,
				width : '60%',
				title : 'Result',
				close : function(event, ui) {
					location.reload();
				},
				buttons : {
					"返回" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
	});
}
