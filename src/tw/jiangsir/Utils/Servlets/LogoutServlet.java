package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/Logout" }, name = "Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = LogoutServlet.class
			.getAnnotation(WebServlet.class).urlPatterns();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// HttpSession session = request.getSession(false);

		// new SessionScope(session).getCurrentUser().doLogout();
		SessionScope sessionScope = new SessionScope(request);
		CurrentUser currentUser = sessionScope.getCurrentUser();
		sessionScope.doLogout();

		// if (currentUser != null && currentUser.getIsGoogleUser()) {
		// response.sendRedirect(
		// "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://"
		// + request.getLocalAddr()
		// + (request.getServerPort() == 80 ? ""
		// : ":" + request.getServerPort())
		// + "/" + request.getContextPath());
		// return;
		// } else {
		// response.sendRedirect(request.getContextPath());
		// return;
		// }
		response.sendRedirect(
				"https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://"
						+ request.getServerName()
						+ (request.getServerPort() == 80 ? ""
								: ":" + request.getServerPort())
						+ "/" + request.getContextPath());
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
