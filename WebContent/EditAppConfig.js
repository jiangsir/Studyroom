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

	jQuery("input[name=ServerEnabled]").each(function() {
		if ($(this).attr("ServerEnabled") == 'true') {
			$(this).attr("checked", "true");
		}
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

	jQuery("#addTab").click(function() {
		var i = jQuery("tr[id=tab]").size() + jQuery("tr[id=newTab]").size();
		if (i < 10) {
			if (jQuery("tr[id=newTab]").length == 0) {
				jQuery("tr[id=tab]:last").clone(true).insertAfter("tr[id=tab]:last");
				jQuery("tr[id=tab]:last").attr("id", "newTab");
				jQuery("span[id=deleteTab]:last").attr("id", "deletenewTab");
			} else {
				jQuery("tr[id=newTab]:last").clone(true).insertAfter("tr[id=newTab]:last");
			}
			jQuery("tr[id=newTab]:last input[name=tabid]").val("");
			jQuery("tr[id=newTab]:last input[name=tabname]").val("");
			jQuery("tr[id=newTab]:last input[name=tabdescript]").val("");
			jQuery("tr[id=newTab]:last select").attr("name", "orderby" + (i + 1));
		}
	});

	jQuery("span[id=deleteTab]").click(function() {
		if (jQuery("tr[id=tab]").size() + jQuery("tr[id=newTab]").size() <= 1) {
			alert("至少必須有一個標籤");
			return;
		}
		var index = jQuery("span[id=deletenewTab]").index(this);
		jQuery("tr[id=newTab]:eq(" + index + ")").remove();
	});

	jQuery("span[id=deleteTab]").click(function() {
		if (jQuery("tr[id=tab]").size() + jQuery("tr[id=newTab]").size() <= 1) {
			alert("至少必須有一個標籤");
			return;
		}
		var index = jQuery("span[id=deleteTab]").index(this);
		jQuery("tr[id=tab]:eq(" + index + ")").remove();
	});

	var tabs = JSON.parse(jQuery("span#problem_tabs").text());
	for ( var i = 0; i < tabs.length; i++) {
		var tab = tabs[i];
		jQuery("input[name=tabid]:eq(" + i + ")").val(tab.id);
		jQuery("input[name=tabname]:eq(" + i + ")").val(tab.name);
		jQuery("input[name=tabdescript]:eq(" + i + ")").val(tab.descript);
		var orderby = tab.orderby.split(",");
		// alert(orderby.length);
		var j = 0;
		$("#tab select[name=orderby" + (i + 1) + "]").each(function() {
			$(this).children().each(function() {
				if (orderby.length > j && orderby[j].trim() == $(this).val()) {
					$(this).attr("selected", "true");
				}
			});
			j++;
		});
	}

	jQuery("#addCompiler").click(function() {
		if (jQuery("tr[id=compiler]").size() + jQuery("tr[id=newCompiler]").size() < 10) {
			if (jQuery("tr[id=newCompiler]").length == 0) {
				jQuery("tr[id=compiler]:last").clone(true).insertAfter("tr[id=compiler]:last");
				jQuery("tr[id=compiler]:last").attr("id", "newCompiler");
				jQuery("span[id=deleteCompiler]:last").attr("id", "deletenewCompiler");
			} else {
				jQuery("tr[id=newCompiler]:last").clone(true).insertAfter("tr[id=newCompiler]:last");
			}
			jQuery("tr[id=newCompiler]:last input[name=compiler_enable]").attr("checked", false);
			jQuery("tr[id=newCompiler]:last input[name=compiler_language]").val("");
			jQuery("tr[id=newCompiler]:last input[name=compiler_version]").val("");
		}
	});

	jQuery("span[id=deleteCompiler]").click(function() {
		if (jQuery("tr[id=compiler]").size() + jQuery("tr[id=newCompiler]").size() <= 1) {
			alert("至少必須有一種程式語言");
			return;
		}
		var index = jQuery("span[id=deleteCompiler]").index(this);
		jQuery("tr[id=compiler]:eq(" + index + ")").remove();
	});

	// var compilers = JSON.parse(jQuery("span#Compilers").text());
	//    
	jQuery("#compiler input[name='compiler_enable']").each(function() {
		// for (var i = 0; i < compilers.length; i++) {
		// var compiler = compilers[i];
		if ($(this).attr("language") == $(this).val()) {
			$(this).attr("checked", "true");
		}
		// }
	});

	jQuery("#SystemClosedMessage").focus(function() {
		$("#SYSTEM_CLOSE").attr("checked", true);
	});

	jQuery("#ProblemidPrefix").children().each(function() {
		// alert(jQuery("span[name='locale']").text());
		if ($(this).parent().attr("ProblemidPrefix") == $(this).val()) {
			$(this).attr("selected", true);
		}
	});

	jQuery("input[name='EnableMailer']").each(function() {
		if ($(this).attr("EnableMailer") == $(this).val()) {
			$(this).attr("checked", true);
		}
	});
	jQuery("input[name='SaveTmpFile']").each(function() {
		if ($(this).attr("SaveTmpFile") == $(this).val()) {
			$(this).attr("checked", true);
		}
	});
	jQuery("input[name='SystemMode']").each(function() {
		if ($(this).attr("SystemMode") == $(this).val()) {
			$(this).attr("checked", true);
		}
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
