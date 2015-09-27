package tw.jiangsir.Utils.Servlets.API;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class EditUsers
 */
@WebServlet(urlPatterns = { "/user.api" })
public class UserApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserApi() {
		super();
	}

	public static enum GET {
		getCurrentUser,//
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (GET.valueOf(action)) {
		case getCurrentUser:
			response.getWriter().write(mapper.writeValueAsString(
					new SessionScope(request).getCurrentUser()));
			break;
		default:
			break;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
