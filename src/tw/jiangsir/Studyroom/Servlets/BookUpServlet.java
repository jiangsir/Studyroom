package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.GoogleChecker.PopChecker;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/BookUp" })
public class BookUpServlet extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookUpServlet() {
		super();
		// TODO Auto-generated constructor stub
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
		HttpSession session = request.getSession(false);
		java.sql.Date date;
		try {
			date = java.sql.Date.valueOf(request.getParameter("date"));
		} catch (Exception e) {
			e.printStackTrace();
			date = new java.sql.Date(System.currentTimeMillis());
		}

		int seatid = Integer.parseInt(request.getParameter("seatid"));
		String studentid = request.getParameter("studentid");
		String passwd = request.getParameter("passwd");

		try {
			new PopChecker().isGmailAccount(studentid.trim()
					+ "@stu.nknush.kh.edu.tw", passwd);
		} catch (Exception e) {
			throw new ApiException(e);
		}

		Booking newBooking = new Booking();
		newBooking.setSeatid(seatid);
		newBooking.setStudentid(studentid);
		newBooking
				.setUserid(new SessionScope(session).getCurrentUser() == null ? 0
						: new SessionScope(session).getCurrentUser().getId());
		newBooking.setDate(date);
		new BookingService().insert(newBooking);
	}

	@Override
	public boolean accessible(HttpServletRequest request)
			throws AccessException {
		java.sql.Date date;
		try {
			date = java.sql.Date.valueOf(request.getParameter("date"));
		} catch (Exception e) {
			e.printStackTrace();
			date = new java.sql.Date(System.currentTimeMillis());
		}

		return canBookup(date);
	}

	public boolean canBookup(java.sql.Date date) {
		java.sql.Timestamp starttime = java.sql.Timestamp.valueOf(date + " "
				+ ApplicationScope.getAppConfig().getStarttime().toString());
		java.sql.Timestamp deadline = java.sql.Timestamp.valueOf(date + " "
				+ ApplicationScope.getAppConfig().getDeadline().toString());
		java.sql.Timestamp thedate = new java.sql.Timestamp(
				System.currentTimeMillis());
		if (thedate.before(starttime) || thedate.after(deadline)) {
			// throw new DataException("目前不是開放訂位時間，訂位時間為 " + starttime + "到 "
			// + deadline);
			return false;
		}
		return true;
	}

}
