<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<script>
	$(function() {
		$("#dialog-confirm").dialog({
			autoOpen : false,
			height : 300,
			width : 350,
			modal : true,
			buttons : {
				"+加入" : function() {
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#opendialog").click(function() {
			$("#dialog-confirm").dialog("open");
		});
		$("button[id='icon01']").button({
			icons : {
				primary : "ui-icon-closethick"
			},
			text : false
		});
		$("button[id='icon02']").button({
			icons : {
				primary : "ui-icon-check"
			},
			text : false
		});

	});
</script>
</head>
<body>
	<jsp:include page="Header.jsp" />
	<table style="border: 0px;">
		<tr>
			<td>1<a
				href="${pageContext.request.contextPath}/BookUp?seatid=1">訂位</a></td>
			<td>2<a
				href="${pageContext.request.contextPath}/BookUp?seatid=2">訂位</a></td>
		</tr>
		<tr>
			<td>3<a
				href="${pageContext.request.contextPath}/BookUp?seatid=3">訂位</a></td>
			<td>4<a
				href="${pageContext.request.contextPath}/BookUp?seatid=4">訂位</a></td>
		</tr>
		<tr>
			<td>5<a
				href="${pageContext.request.contextPath}/BookUp?seatid=5">訂位</a></td>
			<td>6<a
				href="${pageContext.request.contextPath}/BookUp?seatid=6">訂位</a></td>
		</tr>
	</table>
	<p>
		<zero:toUpperCase>Filter</zero:toUpperCase>
	</p>
	<ul>
		<li>RoleFilter: 處理所有要求要登入的頁面，以及登入後的身份是否能夠瀏覽該頁面。如：<a
			href="${pageContext.request.contextPath}/Booking.do">劃位</a>。
		</li>
		<li>EncodingFilter: 對所有的 request parameter 進行編碼，可處理中文參數。</li>
		<li>EscapeFilter: 對所有的 request parameter 過濾特殊符號，可避免 XSS 攻擊。</li>
		<li>ExceptionHandlerFilter: 捕捉所有 Servlet 拋出的 DataException,
			並且包裝後轉發專門處理的頁面。</li>
	</ul>
	<p>Listener</p>
	<ul>
		<li>InitializedListener: 系統啓動時會會執行一次。</li>
		<li>MySessionListener: 只要有 session 產生就會執行，可以用來計算多少 session 在線上</li>
		<li>HttpSessionBindingListener: 讓 user implement
			HttpSessionBindingListener 可以在使用者登入的時候執行特定動作。比如：統計登入人數。</li>
		<li>MyRequestListener: 只要有一個 request 產生，就會執行。</li>
	</ul>

	<p>Exceptions</p>
	<ul>
		<li>DataException: 負責捕捉資料錯誤，比如帳號為空、email不符格式</li>
		<li>RoleException: 負責捕捉權限錯誤的問題，比如企圖修改他人的資料。</li>
	</ul>

	<p>Annotations</p>
	<ul>
		<li>Persistent: 用來標示某 Object的 field 在資料庫內的欄位名稱</li>
		<li>ServletRole: 在 Servlet 內標示那些 role 的人可以進來瀏覽。</li>
	</ul>

	<p>Annotations</p>
	<ul>
		<li>Persistent: 用來標示某 Object的 field 在資料庫內的欄位名稱</li>
		<li>ServletRole: 在 Servlet 內標示那些 role 的人可以進來瀏覽。</li>
	</ul>

	<p>錯誤頁面的處理: 為了避免將系統錯誤畫面顯示出來，而捕捉所有錯誤，改用自定畫面。</p>
	<ul>
		<li>ErrorPageHandlerServlet: 頁面錯誤，常見的如 404 找不到頁面。<a
			href="${pageContext.request.contextPath}/sdfadf">不存在的頁面</a></li>
		<li>ExceptionHandlerServlet: 捕捉由 servlet 拋出的 Exception
			並顯示在自定畫面裡。比如：讀取不符權限的頁面<a
			href="${pageContext.request.contextPath}/DeleteUser.do?userid=888">刪除user</a>
		</li>
	</ul>

	<p>資料庫處理</p>
	<ul>
		<li>使用 PreparedStatement ，可避免 SQL Injection</li>
		<li><a href="${pageContext.request.contextPath}/Admin.do">增刪改查</a></li>
	</ul>
	<p>上傳檔案，並存入資料庫</p>
	<ul>
		<li><a href="${pageContext.request.contextPath}/ShowUpfiles">檔案列表</a></li>
		<li><a href="${pageContext.request.contextPath}/InsertUpfile.do">上傳檔案</a></li>
	</ul>

	<p>非同步資料處理：實作伺服器推播</p>
	<ul>
		<li><a href="${pageContext.request.contextPath}/AsyncNum.html">非同步即時資料</a></li>
	</ul>
	<p>自定 JSTL：可以自定 tag 或 function 備用。</p>
	<ul>
		<c:if test="${zero:isUrlVisible(sessionScope.currentUser)}">
			<li>這一行文字只有在 ADMIN 登入時才會看到。</li>
		</c:if>
		<li>一般使用者只會看到這一行。登入 ADMIN 可以看到另一行！</li>
	</ul>
	<p>前端技術：jquery</p>
	<div id="dialog-confirm" title="Empty the recycle bin?">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>These items will
			be permanently deleted and cannot be recovered. Are you sure?
		</p>
	</div>
	<ul>
		<li>用 jQuery UI 來美化<a href="" type="button">超連結</a>，
			<button style="font-size: 0.8em;">較小按鈕</button>，<input type="submit" />一律控管為
			button。對話框等效果。
		</li>
		<li>用 jQuery dialog 來處理各種提示
			<button id="opendialog">確認框</button>，小圖示
			<button id="icon01" style="font-size: 0.9em;">刪除</button>
			<button id="icon02">勾選</button>以及表單。
		</li>
		<li>用 jquery 來控制 select options.<a
			href="${pageContext.request.contextPath}/UpdateUser.do?userid=${sessionScope.user.id}">修改使用者時自動選好原始的
				ROLE</a></li>
		<li>用 jQuery 處理行動版網頁。</li>
		<li>用 jQuery 處理 grid 表格 使用 jqGrid <a
			href="${pageContext.request.contextPath}/EditUsers">管理使用者</a></li>
	</ul>
	<p>進行 google apps 郵件認證！</p>
	<ul>
		<li><a href="${pageContext.request.contextPath}/GooglePopLogin">用
				pop3</a></li>
		<li><a href="${pageContext.request.contextPath}/GoogleLogin">用
				OAuth2</a></li>
	</ul>
	<p>防止重複 submit</p>
	<ul>
		<li>表單</li>
	</ul>
	<p>Anti Forgery Form</p>
	<ul>
		<li>防止偽造。</li>
	</ul>
	<jsp:include page="Footer.jsp" />
</body>
</html>