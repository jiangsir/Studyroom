package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Exceptions.Alert;

/**
 * Servlet implementation class ErrorHandlerServlet
 */
@WebServlet(urlPatterns = { "/ErrorPageHandler" })
public class ErrorPageHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		String servletName = (String) request
				.getAttribute("javax.servlet.error.servlet_name");
		if (servletName == null) {
			servletName = "Unknown";
		}
		String requestUri = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		Alert alert = new Alert();
		alert.setType(Alert.TYPE.ERROR);
		alert.setTitle("網頁錯誤！");
		alert.setSubtitle(statusCode + ": " + requestUri);
		request.setAttribute("alert", alert);
		request.getRequestDispatcher("/Alert.jsp").forward(request, response);
	}
}
