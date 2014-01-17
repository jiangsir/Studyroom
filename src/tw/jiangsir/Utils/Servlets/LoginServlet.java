package tw.jiangsir.Utils.Servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Services.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/Login" }, name = "Login", initParams = { @WebInitParam(name = "VIEW", value = "/Login.jsp") })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = LoginServlet.class.getAnnotation(
			WebServlet.class).urlPatterns();
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
		User user = new UserService().getUser(account, passwd);
		if (user != null) {
			// session.setAttribute("user", user);
			// session.setAttribute("currentUser", currentUser);
			SessionScope sessionScope = new SessionScope(session);
			sessionScope.setCurrentUser(new UserService().getCurrentUser(user
					.getId()));
			response.sendRedirect(request.getContextPath()
					+ sessionScope.getHistories().get(0));
			return;
		} else {
			// request.setAttribute("returnPage",
			// request.getParameter("returnPage"));
			// new RequestScope(request).setReturnPage();
			request.getRequestDispatcher(VIEW).forward(request, response);
			return;
		}
	}

}
