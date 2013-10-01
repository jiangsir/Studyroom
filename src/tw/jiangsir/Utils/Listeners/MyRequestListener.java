package tw.jiangsir.Utils.Listeners;

import java.util.logging.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Application Lifecycle Listener implementation class MyRequestListener
 * 
 */
@WebListener
public class MyRequestListener implements ServletRequestListener {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Default constructor.
	 */
	public MyRequestListener() {
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event
				.getServletRequest();
		logger.info("requestDistory: requestURI=" + request.getRequestURI()
				+ ", servletPath=" + request.getServletPath());
	}

	/**
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event
				.getServletRequest();
		HttpSession session = request.getSession(true); // 獲取對應的session
		session.setAttribute("ip", request.getRemoteAddr()); // 記錄ip地址
		logger.info("requestListener: requestURI=" + request.getRequestURI()
				+ ", servletPath=" + request.getServletPath());
	}

}
