package tw.jiangsir.Utils.Listeners;

import java.util.logging.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

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
	 * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event
				.getServletRequest();
		// HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
		// request.getServletPath());
		// if (httpServlet != null) {
		// HttpSession session = request.getSession(); // 獲取對應的session
		// SessionScope sessionScope = new SessionScope(session);
		// sessionScope.setSession_ip(request.getRemoteAddr());
		// // sessionScope.setReturnPage(request.getServletPath(),
		// // request.getQueryString());
		// RequestScope requestScope = new RequestScope(request);
		// requestScope.setReturnPage((String) session
		// .getAttribute("returnPage"));
		// logger.info("requestListener: requestURI="
		// + request.getRequestURI() + ", servletPath="
		// + request.getServletPath());
		// }
	}

	/**
	 * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent event) {
		// HttpServletRequest request = (HttpServletRequest) event
		// .getServletRequest();
		//
		// HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
		// request.getServletPath());
		// if (httpServlet != null) {
		// try {
		// HttpSession session = request.getSession();
		// RequestScope requestScope = new RequestScope(request);
		// requestScope.setReturnPage();
		// session.setAttribute("returnPage", requestScope.getReturnPage());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
	}

}
