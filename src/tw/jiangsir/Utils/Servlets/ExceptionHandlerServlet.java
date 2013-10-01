package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Objects.Alert;

/**
 * Servlet implementation class ErrorHandlerServlet
 */
@WebServlet(urlPatterns = { "/ExceptionHandler" })
public class ExceptionHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
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

		Alert alert = new Alert(throwable);
		alert.setContent(statusCode + ": uri=" + requestUri);
		request.setAttribute("alert", alert);
		request.getRequestDispatcher("/Alert.jsp").forward(request, response);
	}

}
