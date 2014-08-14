package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class Index
 */
@WebServlet(urlPatterns = { "/Index" }, name = "Index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = IndexServlet.class.getAnnotation(
			WebServlet.class).urlPatterns();
	public static ServletConfig config;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		IndexServlet.config = config;
	}

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
			e.printStackTrace();
			date = new java.sql.Date(System.currentTimeMillis());
		}

		request.setAttribute("date", date);
		request.setAttribute("nextdate", DateTool.getNextDate(date));
		request.setAttribute("prevdate", DateTool.getPrevDate(date));
		request.setAttribute("bookupMap",
				new BookingService().getBookupMapByDate(date));
		//
		// request.setAttribute("bookupMapToday",
		// new BookingService().getBookupMapToday());
		request.getRequestDispatcher("/Index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
