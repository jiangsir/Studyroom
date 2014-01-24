package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.GoogleChecker.PopChecker;
import tw.jiangsir.Utils.Objects.Booking;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/BookUp" })
public class BookUpServlet extends HttpServlet {
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
		int seatid = Integer.parseInt(request.getParameter("seatid"));
		String studentid = request.getParameter("studentid");
		String passwd = request.getParameter("passwd");

		new PopChecker().isGmailAccount(studentid.trim()
				+ "@stu.nknush.kh.edu.tw", passwd);

		Booking newBooking = new Booking();
		newBooking.setSeatid(seatid);
		newBooking.setStudentid(studentid);
		newBooking
				.setUserid(new SessionScope(session).getCurrentUser() == null ? 0
						: new SessionScope(session).getCurrentUser().getId());
		new BookingService().insert(newBooking);
	}

}
