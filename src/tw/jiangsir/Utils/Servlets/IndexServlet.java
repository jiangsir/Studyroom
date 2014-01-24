package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.DAOs.BookingService;

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
		request.setAttribute("seatidsToday",
				new BookingService().getSeatidToday());
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
