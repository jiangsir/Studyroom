package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Studyroom.DAOs.AttendanceDAO;
import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Studyroom.Servlets.SignInServlet;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.IpAddress;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;
import tw.jiangsir.Utils.Tools.DateTool;

/**
 * Servlet implementation class Index
 */
@WebServlet(urlPatterns = {"/Index"}, name = "Index")
public class IndexServlet extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = IndexServlet.class.getAnnotation(WebServlet.class).urlPatterns();
	public static ServletConfig config;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		IndexServlet.config = config;
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		SessionScope sessionScope = new SessionScope(request);
		if (request.getQueryString() != null && !sessionScope.getCurrentUser().getIsAdmin()) {
			throw new AccessException("未被允許的參數。");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CurrentUser currentUser = new SessionScope(request.getSession(false)).getCurrentUser();
		AppConfig appConfig = ApplicationScope.getAppConfig();
		IpAddress remoteAddr = new IpAddress(request.getRemoteAddr());
		if (remoteAddr.getIsSubnetOf(appConfig.getSigninip()) && (currentUser == null || !currentUser.getIsAdmin())) {
			response.sendRedirect(
					request.getContextPath() + SignInServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0]);
			return;
		}
		java.sql.Date date;
		if (currentUser != null && currentUser.getIsAdmin()) {
			try {
				date = java.sql.Date.valueOf(request.getParameter("date"));
			} catch (Exception e) {
				date = new java.sql.Date(System.currentTimeMillis());
			}
		} else {
			// date = new java.sql.Date(System.currentTimeMillis());
			date = java.sql.Date.valueOf(request.getParameter("date"));
		}

		request.setAttribute("date", date);
		request.setAttribute("nextdate", DateTool.getNextDate(date));
		request.setAttribute("prevdate", DateTool.getPrevDate(date));
		// request.setAttribute("bookupMap",
		// new BookingService().getBookupMapByDate(date));
		request.setAttribute("hashBookings", new BookingService().getHashBookings(date));

		request.setAttribute("attendCount", new AttendanceDAO().getAttendCount(date));
		// request.setAttribute("cacheStudents",
		// StudentService.getCacheStudents());

		// TODO 還有幾個實驗要做：
		// TODO_DONE 1. 因為被停權而“可以訂位”的座位，換了一個別人可以訂位嗎？
		// TODO_DONE 2. 因為資料庫的 studentid+date 的 index
		// 設定已經取消，資料庫可能記錄重複的訂位。程式部分要處理。
		// 用 CanOverBooking 來判斷。
		// TODO_DONE 3. 同一天內將會有同一位置擁有 2 個 studentid
		// 的訂位記錄，以時間晚的為優先。(原座位人因為停權而釋出，但未刪除。)
		// TODO 4.發現一個新問題要解決。以 110194 為例，在 0129 是停權中。最後一次違規是 0126 <br>
		// TODO 若他來要求平反，因而取消 0126 的違規。照理說，他應該要復權。但，重新計算後，他的違規日期變成 0106,0122,0123
		// TODO 仍然是停權中。

		request.getRequestDispatcher("/Index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
