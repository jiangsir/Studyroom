<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />
</head>
<body>
	<jsp:include page="Header.jsp" />

	<p>
		使用者列表：<a href="InsertUser.do">新增一個使用者</a>
	</p>
	<table>
		<tr>
			<td>使用者歷史劃位統計</td>
			<td>操作</td>
		</tr>
		<c:forEach var="user" items="${users }">
			<tr>
				<td><a href="./History/${user.account }">${user.account}:
						${user.name }</a> <br /></td>
				<td><a href="UpdateUser.do?userid=${user.id }">修改</a> | <a
					href="DeleteUser.do?userid=${user.id }">刪除</a></td>
			<tr>
		</c:forEach>
	</table>
	<p>
		<zero:toUpperCase>Filter</zero:toUpperCase>
	</p>
	<ul>
		<li>RoleFilter: 處理所有要求要登入的頁面，以及登入後的身份是否能夠瀏覽該頁面。如：<a
			href="./Booking.do">劃位</a>。
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
			href="$./sdfadf">不存在的頁面</a></li>
		<li>ExceptionHandlerServlet: 捕捉由 servlet 拋出的 Exception
			並顯示在自定畫面裡。比如：讀取不符權限的頁面<a href="./DeleteUser.do?userid=888">刪除user</a>
		</li>
	</ul>

	<p>資料庫處理</p>
	<ul>
		<li>使用 PreparedStatement ，可避免 SQL Injection</li>
		<li><a href="./Admin.do">增刪改查</a></li>
	</ul>
	<p>上傳檔案，並存入資料庫</p>
	<ul>
		<li><a href="./ShowUpfiles">檔案列表</a></li>
		<li><a href="./InsertUpfile.do">上傳檔案</a></li>
	</ul>

	<p>非同步資料處理：實作伺服器推播</p>
	<ul>
		<li><a href="./AsyncNum.html">非同步即時資料</a></li>
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
			<!-- <span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;".> -->
			These items will be permanently deleted and cannot be recovered. Are
			you sure?
		</p>
	</div>
	<ul>
		<li>用 jQuery UI 來美化<a href="" type="button">超連結</a>，
			<button style="font-size: 0.8em;">較小按鈕</button>，<input type="submit" />一律控管為
			button。對話框等效果。
		</li>
		<li>用 jQuery dialog 來處理各種提示
			<button id="opendialog">確認框</button>，小圖示
			<button id="icon01"
				style="margin: 10px; padding: 1px; width: 24px; height: 26px;">刪除</button>
			<button id="icon02">勾選</button>以及表單。 <span
			class="ui-icon ui-icon-closethick"></span> <span
			style="margin: 10px; position: relative; padding: 2px; cursor: pointer; float: left; list-style: none;"
			class="ui-state-default ui-corner-all" title=".ui-icon-closethick"><span
				class="ui-icon ui-icon-closethick"></span></span>
		</li>

		<li>用 jquery 來控制 select options.<a
			href="./UpdateUser.do?userid=${sessionScope.user.id}">修改使用者時自動選好原始的
				ROLE</a></li>
		<li>用 jQuery 處理行動版網頁。</li>
		<li>用 jQuery 處理 grid 表格 使用 jqGrid <a
			href="./EditUsers">管理使用者</a></li>
	</ul>
	<p>進行 google apps 郵件認證！</p>
	<ul>
		<li><a href="./GooglePopLogin">用
				pop3</a></li>
		<li><a href="./GoogleLogin">用
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