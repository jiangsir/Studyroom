package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.Exceptions.RoleException;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Services.UserService;

/**
 * Servlet implementation class InsertUserServlet
 */
@WebServlet(urlPatterns = { "/UpdateUser.do" })
@RoleSetting(allowHigherThen = User.ROLE.USER)
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public boolean isAccessible(HttpServletRequest request) {
		long userid = Long.parseLong(request.getParameter("userid"));
		CurrentUser currentUser = new SessionScope(request.getSession(false))
				.getCurrentUser();
		if (currentUser.getRole() != User.ROLE.ADMIN
				&& currentUser.getId() != userid) {
			throw new RoleException("不可以修改別人的資料！");
		}
		return true;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.isAccessible(request);

		int userid = Integer.parseInt(request.getParameter("userid"));
		request.setAttribute("user", new UserService().getUser(userid));
		request.getRequestDispatcher("/InsertUser.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.isAccessible(request);

		int userid = Integer.parseInt(request.getParameter("userid"));
		User user = new UserService().getUser(userid);
		user.setAccount(request.getParameter("account"));
		user.setPasswd(request.getParameter("passwd1"),
				request.getParameter("passwd2"));
		user.setName(request.getParameter("name"));
		user.setRole(request.getParameter("role"));
		new UserService().update(user);
		response.sendRedirect(IndexServlet.urlPatterns[0]);
	}

}
