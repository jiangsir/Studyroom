<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="seat" uri="/WEB-INF/seat.tld"%>
<%@taglib prefix="room" uri="/WEB-INF/room.tld"%>
<%@taglib prefix="zero" uri="http://jiangsir.tw/jstl/zero"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="CommonHead.jsp" />

<script type="text/javascript" src="Index.js"></script>
<script type="text/javascript"
	src="jscripts/functions/showErrorDialog.js"></script>
<style type="text/css">
.seat {
	font-size: 0.8em;
	max-height: 4em;
	height: 4em;
	max-width: 4em;
	width: 4em;
}
</style>
</head>
<body>
	<jsp:include page="includes/dialog/Error.jsp" />

	<jsp:include page="Header.jsp" />
	<div id="insertBookingDialog" title="請輸入帳號密碼！" style="display: none;">
		<fieldset style="padding: 5px;">
			<legend>
				確定要訂位 <span id="seatid"></span> 號的位置？
			</legend>
			<br /> <label>請輸入學號</label><br /> <input type="text"
				name="studentid" value="" style="width: 90%;"></input><br /> <label>請輸入學生信箱密碼
				(stu.nknush.kh.edu.tw)</label><br /> <input type="text" name="passwd"
				value="" style="width: 90%;"></input> <br />
		</fieldset>
	</div>
	<div id="deleteBookingDialog" title="請輸入帳號密碼！" style="display: none;">
		<fieldset style="padding: 5px;">
			<legend>
				確定要取消訂位 <span id="seatid"></span> 號的位置？
			</legend>
			<br /> <label>請輸入學號</label><br /> <input type="text"
				name="studentid" value="" style="width: 90%;"></input><br /> <label>請輸入學生信箱密碼
				(stu.nknush.kh.edu.tw)</label><br /> <input type="text" name="passwd"
				value="" style="width: 90%;"></input> <br />
		</fieldset>
	</div>
	<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
	<div style="text-align: center;">
		<h1>
			<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />
			的訂位狀況
		</h1>
		<a href="?date=${prevdate }" type="button">前一日</a> <a href="?"
			type="button">今天</a> <a href="?date=${nextdate}" type="button">後一日</a>
	</div>

	<span class="ui-icon ui-icon-arrowthick-1-n"></span>
	<div>已被訂位: ${bookupMap } ${applicationScope.appConfig.starttime }
		: ${applicationScope.appConfig.deadline }</div>
	<div>now: ${now}, canBookup: ${seat:canBookup(date)}</div>
	<c:if test="${!room:isOpen(date) }">今日不開放訂位哦！</c:if>
	<table style="border: 0px;">
		<c:set var="grouplist" value="${fn:split('26,24,20', ',')}" />
		<c:set var="base" value="0" />
		<c:forEach var="group" items="${grouplist }">
			<c:forEach var="row" begin="1" end="2" step="1">
				<tr>
					<c:forEach var="col" begin="1" end="${group}" step="1">
						<c:set var="seatid" value="${base+(row-1)*group+col}" />
						<td><c:choose>
								<c:when test="${!seat:canBookup(date)}">
									<button class="seat">
										<fmt:formatNumber pattern="000" value="${seatid}" />
									</button>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${seat:isOccupied(bookupMap, seatid)}">
											<span class="deleteBooking">${seat:studentid(bookupMap,
												seatid)}</span>
										</c:when>
										<c:otherwise>
											<button id="insertBooking" class="seat" seatid=${seatid}>
												搶
												<fmt:formatNumber pattern="000" value="${seatid}" />
											</button>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
			</c:forEach>
			<c:set var="base" value="${base+group*2 }" />

			<tr style="height: 1em;">
			</tr>
		</c:forEach>
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
			<button id="icon01"
				style="margin: 10px; padding: 1px; width: 24px; height: 26px;">刪除</button>
			<button id="icon02">勾選</button>以及表單。 <span
			class="ui-icon ui-icon-closethick"></span> <span
			style="margin: 10px; position: relative; padding: 2px; cursor: pointer; float: left; list-style: none;"
			class="ui-state-default ui-corner-all" title=".ui-icon-closethick"><span
				class="ui-icon ui-icon-closethick"></span></span>
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