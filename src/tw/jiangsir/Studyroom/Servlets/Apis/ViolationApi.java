package tw.jiangsir.Studyroom.Servlets.Apis;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class BookUp
 */
@WebServlet(urlPatterns = { "/Violation.api" })
public class ViolationApi extends HttpServlet implements IAccessFilter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViolationApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {

	}

	public enum GETACTION {
		getViolationsByStudentid; // 用 studentid 取得 Violations
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (GETACTION.valueOf(action)) {
		case getViolationsByStudentid:
			String studentid = request.getParameter("studentid");
			ArrayList<Violation> violations = new ViolationService()
					.getEnableViolationsByStudentid(studentid);
			// AppConfig appConfig = ApplicationScope.getAppConfig();
			request.setAttribute("studentid", studentid);
			request.setAttribute("violations", violations);

			// String note = "恭喜您(" + studentid + ")，目前沒有任何違規記錄。";
			// if (violations.size() > 0) {
			// note = "請注意，違規 " + appConfig.getPunishingthreshold()
			// + "次，將會被停權 " + appConfig.getPunishingdays() + " 天";
			// }
			// request.setAttribute("note", note);
			request.getRequestDispatcher("includes/div/Violations.jsp")
					.forward(request, response);
			return;
		default:
			break;
		}
		// response.getWriter().print("dfdfdfdfdfddfdfdf");
	}

	public enum POSTACTION {
		rebuiltViolationsByDate, // 依據「日期」進行違規事件計算
		doPunishingByDeleteBooking, // 刪除未來 14 天的訂位資料。
		doPunished, // 針對某個日期將已經 punishing 14 天的人恢復權限。
		cancelViolation, // 取消某一個 violation.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CurrentUser currentUser = new SessionScope(request).getCurrentUser();
		String action = request.getParameter("action");
		switch (POSTACTION.valueOf(action)) {
		case rebuiltViolationsByDate:
			if (currentUser != null && currentUser.getIsAdmin()) {
				Date date = Date.valueOf(request.getParameter("date"));
				new ViolationService().builtViolationsByDate(date);
			}
			response.sendRedirect("."
					+ new SessionScope(request).getCurrentPage());
			break;
		case doPunishingByDeleteBooking:
			// if (currentUser != null && currentUser.getIsAdmin()) {
			// Date date = Date.valueOf(request.getParameter("date"));
			// new ViolationService().doPunishingByDeleteBooking(date);
			// }
			response.sendRedirect("."
					+ new SessionScope(request).getCurrentPage());
			break;
		case doPunished:
			// if (currentUser != null && currentUser.getIsAdmin()) {
			// Date date = Date.valueOf(request.getParameter("date"));
			// new ViolationService().doFinishPunishByDate(date);
			// }
			// response.sendRedirect("."
			// + new SessionScope(request).getCurrentPage());
			break;
		case cancelViolation:
			int violationid = Integer.parseInt(request
					.getParameter("violationid"));
			String comment = request.getParameter("comment");
			Violation violation = new ViolationService()
					.getViolationById(violationid);
			violation.setComment(comment);
			violation.setStatus(Violation.STATUS.cancel);
			new ViolationService().update(violation);
			response.sendRedirect("."
					+ new SessionScope(request).getCurrentPage());
			break;
		default:
			break;

		}
	}
}
