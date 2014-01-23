<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/jscripts/jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/jscripts/jquery.jqGrid-4.5.4/css/ui.jqgrid.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery.jqGrid-4.5.4/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jscripts/jquery.jqGrid-4.5.4/js/jquery.jqGrid.min.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#gridTable").jqGrid({
			datatype : "json", //将这里改为使用JSON数据 
			url : 'getUsers.api', //这是Action的请求地址 
			mtype : 'GET',
			height : 250,
			width : 400,
			colNames : [ '编号', '姓名' ],
			colModel : [ {
				name : 'id',
				index : 'id',
				width : 60,
				sorttype : "int"
			}, {
				name : 'name',
				index : 'name',
				width : 90
			} ],
			pager : 'gridPager', //分页工具栏 
			//imgpath : 'js/themes/basic/images', //图片存放路径 
			rowNum : 10, //每页显示记录数 
			viewrecords : true, //是否显示行数 
			rowList : [ 10, 20, 30 ], //可调整每页显示的记录数 
			multiselect : false, //是否支持多选 
			caption : "信息显示"
		});
	});
</script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<table id="gridTable"></table>
	<div id="gridPager"></div>
	<jsp:include page="Footer.jsp" />
</body>
</html>