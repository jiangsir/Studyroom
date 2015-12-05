package tw.jiangsir.Studyroom.Servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = {"/ManageViolations"})
@RoleSetting
public class ManageViolationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Date date;
		try {
			date = Date.valueOf(request.getParameter("date"));
		} catch (Exception e) {
			date = new Date(System.currentTimeMillis());
		}
		request.setAttribute("date", date);
		request.setAttribute("nextdate", DateTool.getNextDate(date));
		request.setAttribute("prevdate", DateTool.getPrevDate(date));

		request.setAttribute("students", new ViolationService().getStudentsWithEnableViolation(date));
		request.getRequestDispatcher("/ManageViolations.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
