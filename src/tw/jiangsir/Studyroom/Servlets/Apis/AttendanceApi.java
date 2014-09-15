package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.AttendanceService;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.GoogleChecker.PopChecker;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.CurrentUser;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Attendance.api" })
public class AttendanceApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttendanceApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("dfdfdfdfdfddfdfdf");
	}

	public enum ACTION {
		getAttentances;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		try {
			java.sql.Date date;
			try {
				date = java.sql.Date.valueOf(request.getParameter("date"));
			} catch (Exception e) {
				date = new java.sql.Date(System.currentTimeMillis());
			}

			String studentid = request.getParameter("studentid");
			String attendances = "";
			for (Attendance attendance : new AttendanceService()
					.getAttendancesByStudentidDate(studentid, date)) {
				attendances += attendance.getStudentid() + " "
						+ attendance.getStatus() + " "
						+ attendance.getTimestamp() + "<br>";
			}
			System.out.println(attendances);
			response.getWriter().print(attendances);
		} catch (Exception e) {
			throw new ApiException(e);
		}

	}
}
