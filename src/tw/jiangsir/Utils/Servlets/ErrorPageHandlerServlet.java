package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Exceptions.Alert;
import tw.jiangsir.Utils.Scopes.SessionScope;

/**
 * Servlet implementation class ErrorHandlerServlet
 */
@WebServlet(urlPatterns = { "/ErrorPageHandler" })
public class ErrorPageHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
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

		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");

		if (throwable != null) {
			Alert alert = new Alert(throwable);

			Throwable rootCause = throwable;
			alert.getDebugs().add(
					"由 " + this.getClass().getSimpleName() + " 處理");
			ArrayList<String> list = alert.getList();
			while (rootCause.getCause() != null) {
				list.add(rootCause.getClass().getSimpleName() + ": "
						+ rootCause.getLocalizedMessage());
				rootCause = rootCause.getCause();
			}
			try {
				alert.getUris().put(
						"回前頁",
						new URI(request.getContextPath()
								+ new SessionScope(session).getPreviousPage()));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
			// alert.setSubtitle("由 " + this.getClass().getSimpleName() +
			// " 所處理");
			// alert.setList(list);
			request.setAttribute("alert", alert);
			request.getRequestDispatcher("/Alert.jsp").forward(request,
					response);
			return;
		} else {
			Alert alert = new Alert();
			alert.setType(Alert.TYPE.ERROR);
			alert.setTitle("網頁錯誤！");
			alert.setSubtitle(statusCode + ": " + requestUri);
			request.setAttribute("alert", alert);
			request.getRequestDispatcher("/Alert.jsp").forward(request,
					response);
			return;
		}
	}
}
