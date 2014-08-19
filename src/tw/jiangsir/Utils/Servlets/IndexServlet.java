package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.BookingService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class Index
 */
@WebServlet(urlPatterns = { "/Index" }, name = "Index")
public class IndexServlet extends HttpServlet implements IAccessFilter {
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

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		SessionScope sessionScope = new SessionScope(request.getSession(false));
		if (request.getQueryString() != null
				&& !sessionScope.getCurrentUser().getIsAdmin()) {
			throw new AccessException("未被允許的參數。");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CurrentUser currentUser = new SessionScope(request.getSession(false))
				.getCurrentUser();
		java.sql.Date date;
		if (currentUser != null && currentUser.getIsAdmin()) {
			try {
				date = java.sql.Date.valueOf(request.getParameter("date"));
			} catch (Exception e) {
				date = new java.sql.Date(System.currentTimeMillis());
			}
		} else {
			date = new java.sql.Date(System.currentTimeMillis());
		}

		request.setAttribute("date", date);
		request.setAttribute("nextdate", DateTool.getNextDate(date));
		request.setAttribute("prevdate", DateTool.getPrevDate(date));
		request.setAttribute("bookupMap",
				new BookingService().getBookupMapByDate(date));
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
