package tw.jiangsir.Utils.GoogleChecker;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.apple.eawt.Application;

import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/GooglePopLogin" }, name = "GooglePopLogin", initParams = { @WebInitParam(name = "VIEW", value = "/Login.jsp") })
public class GooglePopLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = GooglePopLoginServlet.class
			.getAnnotation(WebServlet.class).urlPatterns();
	public ServletConfig config;
	public String VIEW = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		this.VIEW = config.getInitParameter("VIEW");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");

		try {
			new PopChecker().isGmailAccount(account + "@"
					+ ApplicationScope.getAppConfig().getCheckhost(), passwd);
			CurrentUser currentUser = new CurrentUser();
			currentUser.setAccount(account);
			currentUser.setPasswd(passwd);
			currentUser.setName(account);
			currentUser.setRole(User.ROLE.USER);
			// currentUser.setSession(session);
			SessionScope sessionScope = new SessionScope(session);

			sessionScope.setCurrentUser(currentUser);
			response.sendRedirect("." + sessionScope.getPreviousPage());
			return;
		} catch (Exception e) {
			throw new ApiException(e);
		}

		// if (new PopChecker().isGmailAccount(account +
		// "@stu.nknush.kh.edu.tw",
		// passwd)) {
		// } else {
		// request.getRequestDispatcher(VIEW).forward(request, response);
		// return;
		// }
	}

}
