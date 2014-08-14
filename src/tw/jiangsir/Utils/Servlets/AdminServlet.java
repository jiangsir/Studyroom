package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Annotations.*;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(urlPatterns = { "/Admin" }, name = "Admin")
@RoleSetting(allows = { User.ROLE.ADMIN }, denys = {})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("users", new UserService().getUsers());
		request.getRequestDispatcher("/Admin.jsp").forward(request, response);
	}

}
