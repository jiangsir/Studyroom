package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Utils.DAOs.AttendanceService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/SignOut.api" })
public class SignOutApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignOutApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accessible(HttpServletRequest request)
			throws AccessException {
		return true;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String studentid = request.getParameter("id");
		Attendance attendance = new Attendance();
		attendance.setStudentid(studentid);
		attendance.setStatus(Attendance.STATUS.SignOut);
		new AttendanceService().insert(attendance);
	}

}
