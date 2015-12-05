<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<div id="footer" style="text-align: right; margin-top: 3em;">
	<div style="text-align: right;">
		${now} | <a href="./ShowSessions">顯示session</a> |
		spend=${now.time-ms}ms | 共有 ${fn:length(applicationScope.onlineUsers) }
		人登入
	</div>
	<div>
		Studyroom Built${applicationScope.built } | Designed &amp; <a
			href="./Admin">管理 </a> by <a
			href="mailto:jiangsir@tea.nknush.kh.edu.tw">jiangsir@高師大附中</a>.
	</div>
</div>
