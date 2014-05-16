package tw.jiangsir.Utils.Listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import tw.jiangsir.Utils.Config.ApplicationScope;

/**
 * Application Lifecycle Listener implementation class InitializedListener
 * 
 */
@WebListener
public class InitializedListener implements ServletContextListener {
	private List<AsyncContext> asyncContexts = new ArrayList<AsyncContext>();

	/**
	 * Default constructor.
	 */
	public InitializedListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		ApplicationScope.setAllAttributes(servletContext);

		servletContext.setAttribute("asyncContexts", asyncContexts);
		// servletContext.setAttribute("title",
		// servletContext.getContextPath());
		// servletContext.setAttribute("onlineSessions",
		// ApplicationScope.getOnlineSessions());
		// servletContext.setAttribute("onlineUsers",
		// ApplicationScope.getOnlineUsers());
		Map<String, ? extends ServletRegistration> registrations = servletContext
				.getServletRegistrations();
		for (String key : registrations.keySet()) {
			String servletClassName = registrations.get(key).getClassName();
			WebServlet webServlet;
			try {
				if (Class.forName(servletClassName).newInstance() instanceof HttpServlet) {
					HttpServlet httpServlet = (HttpServlet) Class.forName(
							servletClassName).newInstance();
					webServlet = httpServlet.getClass().getAnnotation(
							WebServlet.class);
					if (webServlet != null) {
						for (String urlpattern : webServlet.urlPatterns()) {
							ApplicationScope.getUrlpatterns().put(urlpattern,
									httpServlet);
						}
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		for (String urlpattern : ApplicationScope.getUrlpatterns().keySet()) {
			System.out.println("urlpattern=" + urlpattern + ", servlet="
					+ ApplicationScope.getUrlpatterns().get(urlpattern));
		}
		servletContext.setAttribute("urlpatterns",
				ApplicationScope.getUrlpatterns());

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep((int) (Math.random() * 10000));
						int num = (int) (Math.random() * 100);
						synchronized (asyncContexts) {
							for (AsyncContext asyncContext : asyncContexts) {
								asyncContext.getResponse().getWriter()
										.println("num=" + num);
								asyncContext.complete();
							}
							asyncContexts.clear();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
				}
			}
		}).start();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
