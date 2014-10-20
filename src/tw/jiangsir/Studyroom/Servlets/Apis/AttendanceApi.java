package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Objects.Attendance.STATUS;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User.ROLE;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Attendance.api" })
@RoleSetting(allowHigherThen = ROLE.ADMIN)
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
			// String attendances = "";

			// for (Attendance attendance : new AttendanceService()
			// .getAttendancesByStudentidDate(studentid, date)) {
			// attendances += attendance.getStudentid() + " "
			// + attendance.getStatus() + " "
			// + attendance.getTimestamp() + "<br>";
			// }
			// System.out.println(attendances);
			// response.getWriter().print(attendances);
			long ms = 0L;
			long prev_SignOut = 0;
			ArrayList<Attendance> attendances = new AttendanceService()
					.getAttendancesByStudentidDate(studentid, date);
			for (Attendance attendance : attendances) {
				if (attendance.getStatus() == STATUS.SignOut) {
					prev_SignOut = attendance.getTimestamp().getTime();
				} else if (attendance.getStatus() == STATUS.SignIn
						&& prev_SignOut > 0) {
					ms += prev_SignOut - attendance.getTimestamp().getTime();
				}
			}
			request.setAttribute("ms", ms);
			request.setAttribute("studentid", studentid);
			request.setAttribute("attendances", attendances);
			request.setAttribute("violations",
					new ViolationService().getEnableViolationsByStudentid(studentid));
			request.getRequestDispatcher("includes/div/StudentAttendance.jsp")
					.forward(request, response);
		} catch (Exception e) {
			throw new ApiException(e);
		}

	}
}
