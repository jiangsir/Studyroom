package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = { "/ManageBooking" })
@RoleSetting
public class ManageBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		java.sql.Date date;
		try {
			date = java.sql.Date.valueOf(request.getParameter("date"));
		} catch (Exception e) {
			date = new java.sql.Date(System.currentTimeMillis());
		}

		request.setAttribute("date", date);
		request.setAttribute("nextdate", DateTool.getNextDate(date));
		request.setAttribute("prevdate", DateTool.getPrevDate(date));
		request.setAttribute("bookupMap",
				new BookingService().getBookupMapByDate(date));
		request.getRequestDispatcher("/ManageBooking.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
