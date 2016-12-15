package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Studyroom.Objects.Student;
import tw.jiangsir.Studyroom.Tables.Attendance;
import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = {"/SignIn.api"})
public class SignInApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignInApi() {
		super();
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String studentid = request.getParameter("id");
		Time now = DateTool.getNowtime();

		AppConfig appConfig = ApplicationScope.getAppConfig();
		if (!(now.after(appConfig.getSigninbegin()) && now.before(appConfig.getSigninend()))) {
			throw new ApiException("簽到／退時間為 " + appConfig.getSigninbegin() + " 到 " + appConfig.getSigninend()
					+ "，請在時間內進行簽到／退。目前時間=" + now);
		}
		if (new Student(studentid, new Date(System.currentTimeMillis())).getIsStopBooking()) {
			throw new ApiException("『" + studentid + "』 您已被停權，無法進行簽到／退。");
		}
		// FIXME 簽到的時候要處理 OverBooking 的問題。要判斷最新的那個 booking.
		Booking booking = new BookingService().getAvailableBookingByStudentidDate(studentid,
				new Date(System.currentTimeMillis()));
		if (booking == null) {
			throw new ApiException("『" + studentid + "』 今天並未訂位，無法進行簽到／退。");
		}

		try {
			Attendance attendance = new AttendanceService().getLastAttendanceTodayByStudentid(studentid);
			if (attendance == null || attendance.getStatus() == Attendance.STATUS.SignOut) {
				new AttendanceService().doSignIn(studentid);
			} else {
				new AttendanceService().doSignOut(studentid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException("簽到錯誤，請通知管理員。(" + e.getLocalizedMessage() + ")");
		}
	}
}
