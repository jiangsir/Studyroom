package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.DAOs.AppConfigService;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class EditAppConfigServlet
 */
@WebServlet(urlPatterns = { "/EditAppConfig" })
@RoleSetting
public class EditAppConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAppConfigServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("EditAppConfig.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AppConfig appConfig = ApplicationScope.getAppConfig();

		for (String name : request.getParameterMap().keySet()) {
			try {
				Method method = appConfig.getClass().getMethod(
						"set" + name.toUpperCase().substring(0, 1)
								+ name.substring(1), String.class);
				method.invoke(appConfig,
						new Object[] { (String) request.getParameter(name) });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		new AppConfigService().insert(appConfig);
		ApplicationScope.setAppConfig(appConfig);

		response.sendRedirect(request.getContextPath()
				+ new SessionScope(request.getSession(false)).getPreviousPage());
		return;
	}

	// private void writeAppConfig(HttpServletRequest request) {
	// AppConfig appConfig = ApplicationScope.getAppConfig();
	// for (Field field : appConfig.getClass().getDeclaredFields()) {
	// Property property = field.getAnnotation(Property.class);
	// if (property == null)
	// continue;
	// Method method;
	// try {
	// method = appConfig.getClass().getMethod(
	// "set" + field.getName().toUpperCase().substring(0, 1)
	// + field.getName().substring(1), String.class);
	// method.invoke(appConfig, new Object[] { (String) request
	// .getParameter(property.key()) });
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// e.printStackTrace();
	// }
	// }
	// ConfigHandler.writeAppConfig(appConfig);
	//
	// }

}
