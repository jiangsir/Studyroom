package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Objects.User;

/**
 * Servlet implementation class InsertUserServlet
 */
@WebServlet(urlPatterns = { "/InsertUser.do" })
@RoleSetting
public class InsertUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("user", new User());
		request.getRequestDispatcher("/InsertUser.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User newuser = new User();
		newuser.setAccount(request.getParameter("account"));
		newuser.setPasswd(request.getParameter("passwd1"),
				request.getParameter("passwd2"));
		newuser.setName(request.getParameter("name"));
		newuser.setRole(request.getParameter("role"));
		new UserService().insert(newuser);
		response.sendRedirect(request.getContextPath()
				+ AdminServlet.class.getAnnotation(WebServlet.class)
						.urlPatterns()[0]);
	}

}
