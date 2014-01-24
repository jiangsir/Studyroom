package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.GoogleChecker.PopChecker;
import tw.jiangsir.Utils.Objects.Booking;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Cancel" })
public class CancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelServlet() {
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
		int seatid = Integer.parseInt(request.getParameter("seatid"));
		String studentid = request.getParameter("studentid");
		String passwd = request.getParameter("passwd");

		new PopChecker().isGmailAccount(studentid.trim()
				+ "@stu.nknush.kh.edu.tw", passwd);

		Booking booking = new BookingService()
				.getBookingByStudentidToday(studentid);
		if (booking == null) {
			throw new DataException("這個位置今天沒有預約，請訂位！");
		} else if (booking.getSeatid() != seatid) {
			throw new DataException("您(" + studentid + ")可能不是這個位置(" + seatid
					+ ")的主人，無法讓您取消訂位。");
		}
		new BookingService().delete(booking.getId());
	}

}
