package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.tribes.util.Arrays;

import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User.ROLE;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class BatchBooking
 */
@WebServlet(urlPatterns = { "/addBatchBookings" })
@RoleSetting(allowHigherThen = ROLE.ADMIN)
public class AddBatchBookingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBatchBookingsServlet() {
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
		CurrentUser currentUser = new SessionScope(request.getSession(false))
				.getCurrentUser();
		String[] studentids = request.getParameterValues("studentid");
		String[] seatids = request.getParameterValues("seatid");
		String[] begindates = request.getParameterValues("begindate");
		String[] enddates = request.getParameterValues("enddate");
		String[] weekdays = request.getParameterValues("weekday");

		System.out.println("studentids=" + Arrays.toString(studentids));
		System.out.println("seatids=" + Arrays.toString(seatids));
		System.out.println("begindates=" + Arrays.toString(begindates));
		System.out.println("enddates=" + Arrays.toString(enddates));
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
				for (String weekday : weekdays) {
					if (Integer.parseInt(weekday) == calendar
							.get(Calendar.DAY_OF_WEEK)) {
						Booking booking = new Booking();
						booking.setUserid(currentUser.getId());
						booking.setStudentid(studentids[i]);
						booking.setSeatid(Integer.parseInt(seatids[i]));
						booking.setDate(date);
						new BookingService().insert(booking);
					}
				}
			}

			// for (int day = 0; day < DateTool.getDayCountBetween(begin, end);
			// day++) {
			// Booking booking = new Booking();
			// booking.setStudentid(studentids[i]);
			// booking.setSeatid(Integer.parseInt(seatids[i]));
			// booking.setUserid(currentUser.getId());
			// booking.setDate(date);
			// new BookingService()
			// .insertBookings(currentUser.getId(), studentids[i],
			// Integer.parseInt(seatids[i]), begin, end);
			// }

		}

	}
}
