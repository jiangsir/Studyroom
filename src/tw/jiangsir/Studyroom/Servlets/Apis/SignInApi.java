package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.DAOs.AttendanceService;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.DAOs.RoomstatusService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/SignIn.api" })
public class SignInApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignInApi() {
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
		Booking booking = new BookingService()
				.getBookingTodayByStudentid(studentid);
		if (booking == null) {
			throw new ApiException("『" + studentid + "』 今天並未訂位，無法進行簽到／退。");
		}

		Attendance attendance = new AttendanceService()
				.getLastAttendanceTodayByStudentid(studentid);
		if (attendance == null
				|| attendance.getStatus() == Attendance.STATUS.SignOut) {
			new AttendanceService().doSignIn(studentid);
		} else {
			new AttendanceService().doSignOut(studentid);
		}
	}

}
