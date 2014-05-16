package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Exceptions.Alert;

/**
 * Servlet implementation class Index
 */
@WebServlet(urlPatterns = { "/ShowSessions" }, name = "ShowSessions")
public class ShowSessionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = ShowSessionsServlet.class
			.getAnnotation(WebServlet.class).urlPatterns();

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String text = "";
		if (session == null) {
			text = "目前 session = null";
		} else {
			Enumeration<?> enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement().toString();
				text += name + " = " + session.getAttribute(name) + "<br>";
			}
		}
		if ("".equals(text)) {
			text += "Session 內沒有任何資料";
		}
		Alert alert = new Alert();
		alert.setTitle("顯示 Session 內的資訊。");
		alert.setSubtitle("sessionid=" + session.getId());
		alert.setContent("session 內的 attribute names 如下列：<br>" + text);
		request.setAttribute("alert", alert);
		request.getRequestDispatcher("/Alert.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
