package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.tribes.util.Arrays;

import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Objects.CurrentUser;

/**
 * Servlet implementation class BatchBooking
 */
@WebServlet(urlPatterns = { "/addBatchBookings" })
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

		System.out.println("studentids=" + Arrays.toString(studentids));
		System.out.println("seatids=" + Arrays.toString(seatids));
		System.out.println("begindates=" + Arrays.toString(begindates));
		System.out.println("enddates=" + Arrays.toString(enddates));

		for (int i = 0; i < studentids.length; i++) {
			if (studentids[i] == null || "".equals(studentids[i])) {
				continue;
			}
			Date begin = Date.valueOf(begindates[i]);
			Date end = Date.valueOf(enddates[i]);
			new BookingService().insertBookings(currentUser.getId(),
					studentids[i], Integer.parseInt(seatids[i]), begin, end);
		}

	}
}
