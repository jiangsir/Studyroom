package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.GoogleChecker.PopChecker;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Booking.api" })
public class BookingApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingApi() {
		super();
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
	}

	public enum POSTACTION {
		booked, // 訂位
		cancel;// 取消
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		AppConfig appConfig = ApplicationScope.getAppConfig();
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		try {
			java.sql.Date date;
			try {
				date = java.sql.Date.valueOf(request.getParameter("date"));
			} catch (Exception e) {
				date = new java.sql.Date(System.currentTimeMillis());
			}

			switch (POSTACTION.valueOf(request.getParameter("action"))) {
			case booked:
				if (request.getParameter("seatid") == null
						|| !request.getParameter("seatid").matches("[0-9]+")) {
					throw new DataException("沒有輸入正確的 seatid 參數。");
				}
				if (request.getParameter("studentid") == null) {
					throw new DataException("沒有輸入 studentid 參數。");
				}
				int seatid = Integer.parseInt(request.getParameter("seatid"));
				String studentid = request.getParameter("studentid");

				String passwd = request.getParameter("passwd");

				Booking newBooking = new Booking();
				newBooking.setSeatid(seatid);
				newBooking.setStudentid(studentid);
				newBooking
						.setUserid(new SessionScope(session).getCurrentUser() == null ? 0
								: new SessionScope(session).getCurrentUser()
										.getId());
				newBooking.setDate(date);

				if (currentUser != null && currentUser.getIsAdmin()) {
				} else {
					new PopChecker().isGmailAccount(newBooking.getStudentid()
							+ "@" + appConfig.getCheckhost(), passwd);
				}

				// new BookingService().checkHasBookingRight(
				// newBooking.getStudentid(), date);

				new BookingService().insert(newBooking);

				return;
			case cancel:
				seatid = Integer.parseInt(request.getParameter("seatid"));

				if (currentUser != null && currentUser.getIsAdmin()) {
					new BookingService().delete(seatid, date);
					return;
				}
				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 18) {
					throw new DataException("已經超過開館時間囉，不能取消訂位！");
				}

				studentid = request.getParameter("studentid");
				passwd = request.getParameter("passwd");

				new PopChecker().isGmailAccount(studentid.trim() + "@"
						+ appConfig.getCheckhost(), passwd);

				Booking booking = new BookingService()
						.getBookingTodayByStudentid(studentid);
				if (booking == null) {
					throw new DataException("您(" + studentid
							+ ")今天並沒有訂位，因此不能取消訂位！");
				} else if (booking.getSeatid() != seatid) {
					throw new DataException("您(" + studentid + ")可能不是這個位置("
							+ seatid + ")的主人，無法讓您取消訂位。");
				}
				new BookingService().delete(booking.getId());
				return;
			default:
				break;

			}
		} catch (Exception e) {
			throw new ApiException(e);
		}

	}

}
