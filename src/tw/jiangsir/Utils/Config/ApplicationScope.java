package tw.jiangsir.Utils.Config;

import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Objects.User;

public class ApplicationScope {
	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, User> onlineUsers = new HashMap<String, User>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();

	public static HashMap<String, HttpSession> getOnlineSessions() {
		return onlineSessions;
	}

	public static void setOnlineSessions(
			HashMap<String, HttpSession> onlineSessions) {
		ApplicationScope.onlineSessions = onlineSessions;
	}

	public static HashMap<String, User> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(HashMap<String, User> onlineUsers) {
		ApplicationScope.onlineUsers = onlineUsers;
	}

	public static HashMap<String, HttpServlet> getUrlpatterns() {
		return urlpatterns;
	}

	public static void setUrlpatterns(HashMap<String, HttpServlet> urlpatterns) {
		ApplicationScope.urlpatterns = urlpatterns;
	}

}
