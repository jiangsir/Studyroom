<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="room" uri="/WEB-INF/room.tld"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<script>
	$(function() {
		$('div.css_day').hover(function() {
			$(this).addClass('highlight');
		}, function() {
			$(this).removeClass('highlight');
		});

		$("div.css_day").click(function() {
			var date = $(this).attr("date");
			jQuery.ajax({
				type : "POST",
				url : "Roomstatus.api",
				data : "action=doChangeStatus&date=" + date,
				async : false,
				timeout : 5000,
				success : function(result) {
					location.reload();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					showErrorDialog(jqXHR, textStatus, errorThrown);
				}
			});
		});
	});
</script>
<style type="text/css">
#css_table {
	display: table;
	margin: auto;
}

.css_tr {
	display: table-row;
	line-height: 1em;
	padding: 1em;
	font-size: 1.2em;
}

.css_td {
	display: table-cell;
	width: 2.5em;
	height: 2.5em;
	vertical-align: middle;
	text-align: center;
}

.css_day {
	display: table-cell;
	width: 2.5em;
	height: 2.5em;
	vertical-align: middle;
	text-align: center;
}

.highlight {
	box-sizing: border-box;
	border: 1px solid #666666;
	border-radius: 5px;
	/* 	background: #dddddd;
 */ /* 	-o-transition: all 0.1s ease-in-out;
	-webkit-transition: all 0.1s ease-in-out;
	-moz-transition: all 0.1s ease-in-out;
	-ms-transition: all 0.1s ease-in-out;
 	transition: all 0.1s ease-in-out;
 	*/
}
</style>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center;">
		<h1>設定開館、閉館日期</h1>
		<a href="?date=${prevmonth}" type="button">上個月(<fmt:formatDate
				value="${prevmonth}" pattern="yyyy-MM" />)
		</a> <a href="?" type="button">本月(<fmt:formatDate value="${now}"
				pattern="yyyy-MM" />)
		</a> <a href="?date=${nextmonth}" type="button">下個月(<fmt:formatDate
				value="${nextmonth}" pattern="yyyy-MM" />)
		</a>
		<hr>
		<h1>
			<fmt:formatDate value="${thedate}" pattern="yyyy-MM" />
		</h1>
		<div id="css_table">
			<div class="css_tr">
				<div class="css_td">日</div>
				<div class="css_td">一</div>
				<div class="css_td">二</div>
				<div class="css_td">三</div>
				<div class="css_td">四</div>
				<div class="css_td">五</div>
				<div class="css_td">六</div>
			</div>
			<c:forEach var="row" begin="1" end="6" step="1">
				<div class="css_tr">
					<c:forEach var="col" begin="${(row-1)*7}" end="${(row-1)*7+6}"
						step="1" items="${daysOfMonth }">
						<div class="css_td">

							<%-- 						<c:set var="index" value="${col+(row-1)*7}" />
						<c:set var="day" value="${index-firstweekdayofmonth+1 }" />
 --%>
							<c:choose>
								<c:when test="${col==null }"></c:when>
								<c:when test="${room:isOpen(col)}">
									<div class="css_day"
										date="<fmt:formatDate value="${col}" pattern="yyyy-MM-dd" />">
										<fmt:formatDate value="${col}" pattern="dd" />
									</div>
								</c:when>
								<c:when test="${!room:isOpen(col)}">
									<div class="css_day" style="color: red;"
										date="<fmt:formatDate value="${col}" pattern="yyyy-MM-dd" />">
										<fmt:formatDate value="${col}" pattern="dd" />
									</div>
								</c:when>
							</c:choose>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="Footer.jsp" />
</body>
</html>