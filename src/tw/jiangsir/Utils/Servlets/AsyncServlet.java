package tw.jiangsir.Utils.Servlets;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Async.AsyncRequest;

/**
 * Servlet implementation class AsyncServlet
 */
@WebServlet(urlPatterns = { "/Async" }, asyncSupported = true)
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AsyncContext asyncContext = request.startAsync();
		executorService.submit(new AsyncRequest(asyncContext));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	public void destroy() {
		executorService.shutdown();
	}

}
