package tw.jiangsir.Utils.Config;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Studyroom.Servlets.BookUpServlet;
import tw.jiangsir.Utils.Objects.User;

public class ApplicationScope {
	public static ServletContext servletContext = null;

	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, User> onlineUsers = new HashMap<String, User>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();
	private static File appRoot = null;
	private static AppConfig appConfig = null;
	private static boolean canBookup = false;

	public static void setAllAttributes(ServletContext servletContext) {
		ApplicationScope.servletContext = servletContext;

		ApplicationScope.setAppRoot(new File(servletContext.getRealPath("/")));
		ApplicationScope.setOnlineSessions(onlineSessions);
		ApplicationScope.setOnlineUsers(onlineUsers);
		ApplicationScope.setUrlpatterns(urlpatterns);
		ApplicationScope.setAppConfig(ConfigHandler.getAppConfig());
		ApplicationScope.setCanBookup();
	}

	public static HashMap<String, HttpSession> getOnlineSessions() {
		return onlineSessions;
	}

	public static void setOnlineSessions(
			HashMap<String, HttpSession> onlineSessions) {
		ApplicationScope.onlineSessions = onlineSessions;
		servletContext.setAttribute("onlineSessions", onlineSessions);
	}

	public static HashMap<String, User> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(HashMap<String, User> onlineUsers) {
		ApplicationScope.onlineUsers = onlineUsers;
		servletContext.setAttribute("onlineUsers", onlineUsers);
	}

	public static HashMap<String, HttpServlet> getUrlpatterns() {
		return urlpatterns;
	}

	public static void setUrlpatterns(HashMap<String, HttpServlet> urlpatterns) {
		ApplicationScope.urlpatterns = urlpatterns;
		servletContext.setAttribute("urlpatterns", urlpatterns);
	}

	public static File getAppRoot() {
		return appRoot;
	}

	public static void setAppRoot(File appRoot) {
		ApplicationScope.appRoot = appRoot;
		servletContext.setAttribute("appRoot", appRoot);
	}

	public static AppConfig getAppConfig() {
		return appConfig;
	}

	public static void setAppConfig(AppConfig appConfig) {
		ApplicationScope.appConfig = appConfig;
		servletContext.setAttribute("appConfig", appConfig);
	}

	public static boolean getCanBookup() {
		try {
			new BookUpServlet().AccessFilter(new Date(System
					.currentTimeMillis()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void setCanBookup() {
		servletContext.setAttribute("canBookup",
				ApplicationScope.getCanBookup());
	}
}
