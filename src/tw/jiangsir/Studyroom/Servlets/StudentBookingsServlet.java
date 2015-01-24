package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.User.ROLE;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class ManageStudyroom
 */
@WebServlet(urlPatterns = { "/StudentBookings" })
@RoleSetting(allowHigherThen = ROLE.ADMIN)
public class StudentBookingsServlet extends HttpServlet implements
		IAccessFilter {
	private static final long serialVersionUID = 1L;

	public enum PARAM {
		studentid, //
		date;//
	}

	public class StudentBooking {
		private Booking booking;
		private java.util.Date date;

		public Booking getBooking() {
			return booking;
		}

		public void setBooking(Booking booking) {
			this.booking = booking;
		}

		public java.util.Date getDate() {
			return date;
		}

		public void setDate(java.util.Date date) {
			this.date = date;
		}
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		if (request.getParameter(PARAM.studentid.name()) == null) {
			throw new AccessException("缺少必要參數 " + PARAM.studentid);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentBookingsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String studentid = request.getParameter(PARAM.studentid.name());
		Date thedate;
		try {
			thedate = Date.valueOf(request.getParameter(PARAM.date.name()));
		} catch (Exception e) {
			thedate = new Date(new java.util.Date().getTime());
		}

		// LinkedHashMap<java.util.Date, Booking> dateBookings = new
		// LinkedHashMap<java.util.Date, Booking>();
		// // ArrayList<HashMap<java.util.Date, Booking>> dateBookings = new
		// // ArrayList<HashMap<java.util.Date, Booking>>();
		// synchronized (dateBookings) {
		ArrayList<StudentBooking> studentBookings = new ArrayList<StudentBooking>();
		BookingService bookingService = new BookingService();
		for (java.util.Date date : DateTool.getDaysOfMonth(thedate)) {
			if (date == null) {
				studentBookings.add(null);
			} else {
				StudentBooking studentBooking = new StudentBooking();
				studentBooking.setBooking(bookingService
						.getBookingByStudentidDate(new Date(date.getTime()),
								studentid));
				studentBooking.setDate(date);
				studentBookings.add(studentBooking);
				// dateBookings.put(date, bookingService
				// .getBookingByStudentidDate(
				// new Date(date.getTime()), studentid));

			}
		}
		// }
		request.setAttribute("thedate", thedate);
		request.setAttribute("prevmonth", DateTool.getPrevMonth(thedate));
		request.setAttribute("nextmonth", DateTool.getNextMonth(thedate));
		request.setAttribute("firstweekdayofmonth",
				DateTool.getFirstdayOfMonth(thedate));
		request.setAttribute("lastdayofmonth",
				DateTool.getLastDayOfMonth(thedate));
		request.setAttribute("daysOfMonth", studentBookings);
		request.getRequestDispatcher("StudentBookings.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
