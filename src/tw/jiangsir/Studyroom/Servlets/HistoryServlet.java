package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Services.UserService;

/**
 * Servlet implementation class History
 */
@WebServlet(urlPatterns = { "/History/*" })
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		String pathinfo = request.getPathInfo();
		if (pathinfo == null || "/".equals(pathinfo)
				|| !userService.isExitedAccount(pathinfo.substring(1))) {
			throw new ServletException("該用戶並不存在！");
		}
		request.setAttribute("user", userService.getUser(pathinfo.substring(1)));
		request.getRequestDispatcher("/History.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
