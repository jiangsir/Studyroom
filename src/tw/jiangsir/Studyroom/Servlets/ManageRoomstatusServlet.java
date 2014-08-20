package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class ManageStudyroom
 */
@WebServlet(urlPatterns = { "/ManageRoomstatus" })
public class ManageRoomstatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageRoomstatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Date thedate;
		try {
			thedate = Date.valueOf(request.getParameter("date"));
		} catch (Exception e) {
			thedate = new Date(new java.util.Date().getTime());
		}

		request.setAttribute("thedate", thedate);
		request.setAttribute("prevmonth", DateTool.getPrevMonth(thedate));
		request.setAttribute("nextmonth", DateTool.getNextMonth(thedate));
		request.setAttribute("firstweekdayofmonth",
				DateTool.getFirstdayOfMonth(thedate));
		request.setAttribute("lastdayofmonth",
				DateTool.getLastDayOfMonth(thedate));
		request.setAttribute("daysOfMonth", DateTool.getDaysOfMonth(thedate));
		request.getRequestDispatcher("ManageRoomstatus.jsp").forward(request,
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
