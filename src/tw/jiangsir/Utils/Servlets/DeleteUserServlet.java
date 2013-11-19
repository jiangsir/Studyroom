package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Exceptions.RoleException;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Services.UserService;

/**
 * Servlet implementation class InsertUserServlet
 */
@WebServlet(urlPatterns = { "/DeleteUser.do" })
@RoleSetting
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int userid = Integer.parseInt(request.getParameter("userid"));
		User currentUser = new UserService().getCurrentUser(session);
		if (currentUser.getRole() != User.ROLE.ADMIN
				&& currentUser.getId().intValue() != userid) {
			throw new RoleException("您沒有這個權限！！");
		}
		new UserService().delete(userid);
		response.sendRedirect(request.getContextPath()
				+ AdminServlet.class.getAnnotation(WebServlet.class)
						.urlPatterns()[0]);
	}

}
