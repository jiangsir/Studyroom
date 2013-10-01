package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AsyncNumServlet
 */
@WebServlet(urlPatterns = { "/AsyncNum" }, asyncSupported = true)
public class AsyncNumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<AsyncContext> asyncContexts;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		asyncContexts = (List<AsyncContext>) this.getServletContext()
				.getAttribute("asyncContexts");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AsyncContext asyncContext = request.startAsync();
		synchronized (asyncContexts) {
			asyncContexts.add(asyncContext);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
