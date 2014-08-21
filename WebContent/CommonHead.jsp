<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${applicationScope.appConfig.title}</title>

<script src="jquery/jquery-ui-1.10.4/js/jquery-1.10.2.js"></script>
<script src="jquery/jquery-ui-1.10.4/js/jquery-ui-1.10.4.custom.min.js"></script>
<link
	href="jquery/jquery-ui-1.10.4/css/smoothness/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet" />
<style>
.ui-menu {
	overflow: hidden;
	margin-bottom: 1em; /* 下面留一點空間。 */
	padding-right: 10em; /* 第一層左右都內縮，看起來不比較好看 */
	padding-left: 10em;
	z-index: 100; /* 提升 menu 的圖層，否則會被 button 擋到。 */
}

.ui-menu .ui-menu {
	padding: 1px 1px 1px 1px; /* 第二層以下的 menu 就不內縮了。 */
	overflow: visible !important;
}

.ui-menu>li {
	float: right;
	display: block;
	width: auto !important;
}

.ui-menu ul li {
	display: block;
	float: none;
}

.ui-menu ul li ul {
	left: 120px !important;
	width: 100%;
}

.ui-menu ul li ul li {
	width: auto;
}

.ui-menu ul li ul li a {
	float: left;
}

.ui-menu>li {
	margin: 5px 5px !important;
	padding: 0 0 !important;
}

.ui-menu>li>a {
	float: left;
	display: block;
	clear: both;
	overflow: hidden;
}

.ui-menu .ui-menu-icon {
	margin-top: 0.3em !important;
}

.ui-menu .ui-menu .ui-menu li {
	float: left;
	display: block;
}
</style>
<script language="javascript">
	jQuery(document).ready(function() {
		$("input:first").focus();
		$("input[type=submit], [type='button'], [type='checkbox']").button();
		$("input[type='checkbox']").buttonset();
		$("button").button().click(function(event) {
			event.preventDefault(); // 讓預設的動作失效！
		});
		$(".closethick").button({
			icons : {
				primary : "ui-icon-closethick"
			},
			text : false
		});

		$("#menu").menu({
			position : {
				at : "left bottom"
			}
		});
		/* 				$("ul#uimenu").menu({
		 minWidth : 120,
		 arrowSrc : 'arrow_right.gif',
		 onClick : function(e, menuItem) {
		 alert('you clicked item "' + $(this).text() + '"');
		 }
		 });
		 */
		//mytime(parseInt(${now.time}) );
		//$("#account_menu").menu();
		var servletPath = jQuery("span#servletPath").attr("page");
		jQuery("div#menu > ul > li").each(function() {
			if ($(this).attr("uri") == servletPath || $(this).attr("uri2") == servletPath) {
				$(this).addClass("selected");
			}
		});

	});
</script>

