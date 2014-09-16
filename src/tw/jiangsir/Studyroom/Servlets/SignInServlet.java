package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Config.AppConfig;
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.AttendanceService;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.CurrentUser;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = { "/SignIn" })
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CurrentUser currentUser = new SessionScope(request).getCurrentUser();
		AppConfig appConfig = ApplicationScope.getAppConfig();
		if (currentUser.getIsAdmin()
				|| request.getRemoteAddr().equals(appConfig.getSigninip())) {
			request.setAttribute("attendances",
					new AttendanceService().getAttendances(1));
			request.getRequestDispatcher("SignIn.jsp").forward(request,
					response);
			return;
		} else {
			throw new DataException("您所在的位置無法使用這個功能。");
		}

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
