package tw.jiangsir.Utils.Listeners;

import java.util.logging.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Config.SessionScope;

/**
 * Application Lifecycle Listener implementation class MySessionListener
 * 
 */
@WebListener
public class MySessionListener implements HttpSessionListener {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Default constructor.
	 */
	public MySessionListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		logger.info("新建立 sessionid=" + event.getSession().getId());
		HttpSession session = event.getSession();
		SessionScope sessionScope = new SessionScope(session);
		ApplicationScope.getOnlineSessions().put(event.getSession().getId(),
				event.getSession());
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		ApplicationScope.getOnlineSessions().remove(se.getSession().getId());
	}

}
