package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.util.Arrays;

import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.ApiException;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Scopes.SessionScope;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/BatchBooking.api" })
public class BatchBookingApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BatchBookingApi() {
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
	}

	public enum ACTION {
		add, // 大量訂位
		delete;// 大量銷訂
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			switch (ACTION.valueOf(request.getParameter("action"))) {
			case add:
				add(request);
				return;
			case delete:
				delete(request);
				return;
			default:
				break;

			}
		} catch (Exception e) {
			throw new ApiException(e);
		}

	}

	/**
	 * 大量訂位。
	 * 
	 * @param request
	 */
	private void add(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		String[] studentids = request.getParameterValues("studentid");
		String[] seatids = request.getParameterValues("seatid");
		String[] begindates = request.getParameterValues("begindate");
		String[] enddates = request.getParameterValues("enddate");
		String[] weekdays = request.getParameterValues("weekdays");

		System.out.println("weekdays=" + Arrays.toString(weekdays));

		for (int i = 0; i < studentids.length; i++) {
			if (studentids[i] == null || "".equals(studentids[i])) {
				continue;
			}
			Date begin = Date.valueOf(begindates[i]);
			Date end = Date.valueOf(enddates[i]);

			for (Date date : DateTool.getDaysBetween(begin, end)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				try {
					if (weekdays[i].contains(String.valueOf(calendar
							.get(Calendar.DAY_OF_WEEK)))) {
						Booking booking = new Booking();
						booking.setUserid(currentUser.getId());
						booking.setStudentid(studentids[i]);
						booking.setSeatid(Integer.parseInt(seatids[i]));
						booking.setDate(date);
						new BookingService().insert(booking);
					} else {
						new BookingService().deleteByStudentidDate(
								studentids[i], date);
					}
				} catch (NumberFormatException e) {
				} catch (DataException e) {
				}
			}
		}

	}

	/**
	 * 大量退訂
	 * 
	 * @param request
	 */
	private void delete(HttpServletRequest request) {
		String[] studentids = request.getParameterValues("studentid");
		String[] begindates = request.getParameterValues("begindate");
		String[] enddates = request.getParameterValues("enddate");
		for (int i = 0; i < studentids.length; i++) {
			if (studentids[i] == null || "".equals(studentids[i])) {
				continue;
			}
			Date begin = Date.valueOf(begindates[i]);
			Date end = Date.valueOf(enddates[i]);

			for (Date date : DateTool.getDaysBetween(begin, end)) {
				new BookingService().deleteByStudentidDate(studentids[i], date);
			}
		}

	}
}
