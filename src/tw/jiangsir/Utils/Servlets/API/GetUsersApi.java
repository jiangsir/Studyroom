package tw.jiangsir.Utils.Servlets.API;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Objects.User;

/**
 * Servlet implementation class EditUsers
 */
@WebServlet(urlPatterns = { "/getUsers.api" })
@RoleSetting(allowHigherThen = User.ROLE.ADMIN)
public class GetUsersApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUsersApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write(
				mapper.writeValueAsString(new UserService().getUsers()));
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
