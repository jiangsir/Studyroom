<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${applicationScope.title}</title>
</head>
<body>
	<jsp:include page="Header.jsp" />

	<p>Filter</p>
	<ul>
		<li>LoginFilter: 處理所有要求要登入的頁面，以及登入後的身份是否能夠瀏覽該頁面。如：<a
			href="${pageContext.request.contextPath}/Booking.do">劃位</a>。
		</li>
		<li>EncodingFilter: 對所有的 request parameter 進行編碼，可處理中文參數。</li>
		<li>EscapeFilter: 對所有的 request parameter 過濾特殊符號，可避免 XSS 攻擊。</li>
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
		<li><a href="${pageContext.request.contextPath}/FileUpload.do">上傳檔案</a></li>
	</ul>

	<p>非同步資料處理：實作伺服器推播</p>
	<ul>
		<li><a href="${pageContext.request.contextPath}/AsyncNum.html">非同步即時資料</a></li>
	</ul>

	<p>前端技術：jquery</p>
	<ul>
		<li>用 jquery 來控制 select options.<a
			href="${pageContext.request.contextPath}/UpdateUser.do?userid=${sessionScope.user.id}">修改使用者時自動選好原始的
				ROLE</a></li>
	</ul>
	<jsp:include page="Footer.jsp" />
</body>
</html>