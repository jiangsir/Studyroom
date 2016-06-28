package tw.jiangsir.Utils.Scopes;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import tw.jiangsir.Studyroom.DAOs.AppConfigService;
import tw.jiangsir.Studyroom.Servlets.BookUpServlet;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Objects.User;

public class ApplicationScope {
	public static ServletContext servletContext = null;
	private static String version = "Undefined Version";
	private static String built = "Undefined BuiltNumber";
	private static HashMap<String, HttpSession> onlineSessions = new HashMap<String, HttpSession>();
	private static HashMap<String, User> onlineUsers = new HashMap<String, User>();
	private static HashMap<String, HttpServlet> urlpatterns = new HashMap<String, HttpServlet>();
	private static File appRoot = null;
	private static AppConfig appConfig = null;

	public static void setAllAttributes(ServletContext servletContext) {
		ApplicationScope.servletContext = servletContext;

		ApplicationScope.setAppRoot();
		ApplicationScope.setVersion();
		ApplicationScope.setBuilt();
		ApplicationScope.setOnlineSessions(onlineSessions);
		ApplicationScope.setOnlineUsers(onlineUsers);
		ApplicationScope.setUrlpatterns(urlpatterns);
		// ApplicationScope.setAppConfig(ConfigHandler.getAppConfig());
		ApplicationScope.setAppConfig(new AppConfigService().getAppConfig());
		ApplicationScope.setCanBookup();
	}

	public static HashMap<String, HttpSession> getOnlineSessions() {
		return onlineSessions;
	}

	public static void setOnlineSessions(HashMap<String, HttpSession> onlineSessions) {
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

	/**
	 * 直接指定 AppRoot，在單機直接執行的時候使用。因此不具備 serveltContext
	 * 
	 * @param appRoot
	 */
	public static void setAppRoot(File appRoot) {
		ApplicationScope.appRoot = appRoot;
		// servletContext.setAttribute("appRoot", appRoot);
	}

	public static void setAppRoot() {
		ApplicationScope.appRoot = new File(servletContext.getRealPath("/"));
		ApplicationScope.servletContext.setAttribute("appRoot", appRoot);
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
			new BookUpServlet().AccessFilter(new Date(System.currentTimeMillis()));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void setCanBookup() {
		servletContext.setAttribute("canBookup", ApplicationScope.getCanBookup());
	}

	/**
	 * 取得目前系統的版本。
	 */
	public static String getVersion() {
		if (ApplicationScope.version == null) {
			setVersion();
		}
		return ApplicationScope.version;
	}

	/**
	 * 取得目前系統的版本。
	 */
	public static void setVersion() {
		try {
			ApplicationScope.version = FileUtils
					.readFileToString(new File(ApplicationScope.appRoot + File.separator + "META-INF", "Version.txt"))
					.trim();
		} catch (IOException e) {
			e.printStackTrace();
			// ApplicationScope.version = "";
		}
		servletContext.setAttribute("version", ApplicationScope.version);
	}

	public static String getBuilt() {
		if (ApplicationScope.built == null) {
			setBuilt();
		}
		return ApplicationScope.built;
	}
	public static void setBuilt() {
		ApplicationScope.built = new SimpleDateFormat("yyMMdd")
				.format(new Date(ApplicationScope.getAppRoot().lastModified()));
		servletContext.setAttribute("built", ApplicationScope.built);
	}

}
