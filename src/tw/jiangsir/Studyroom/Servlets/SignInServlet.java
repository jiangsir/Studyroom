package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.IpAddress;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = {"/SignIn"})
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CurrentUser currentUser = new SessionScope(request).getCurrentUser();
		AppConfig appConfig = ApplicationScope.getAppConfig();
		IpAddress remoteAddr = new IpAddress(request.getRemoteAddr());
		// request.getRemoteAddr().equals(appConfig.getSigninip()
		if (remoteAddr.isLoopbackAddress() || remoteAddr.getIsSubnetOf(appConfig.getSigninip())
				|| (currentUser != null && currentUser.getIsAdmin())) {
			request.setAttribute("attendances",
					new AttendanceService().getAttendancesByDate(new Date(System.currentTimeMillis()), 1));
			request.getRequestDispatcher("SignIn.jsp").forward(request, response);
			return;
		} else {
			throw new DataException("您所在的位置無法使用這個功能。");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
