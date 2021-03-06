<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>


<div id="footer" style="text-align: right; margin-top: 3em;">
	<div style="text-align: right;">
		<a href="${pageContext.request.contextPath}/ShowSessions">顯示session</a>
		| spend=${now.time-ms}ms | 共有
		${fn:length(applicationScope.onlineUsers) } 人登入
	</div>
	<div>
		Designed &amp; <a href="${pageContext.request.contextPath}/Admin.do">管理</a>
		by <a href="mailto:aming@tea.nknush.kh.edu.tw">黃裕明@高師大附中</a>.
	</div>
</div>
