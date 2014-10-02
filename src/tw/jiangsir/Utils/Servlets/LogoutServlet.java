package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/Logout" }, name = "Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = LogoutServlet.class.getAnnotation(
			WebServlet.class).urlPatterns();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// new SessionScope(session).getCurrentUser().doLogout();
		new SessionScope(session).doLogout();
		response.sendRedirect(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
