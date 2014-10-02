package tw.jiangsir.Utils.Filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Scopes.ApplicationScope;

/**
 * Servlet Filter implementation class PerformanceFilter
 */
@WebFilter(filterName = "PerformanceFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class PerformanceFilter implements Filter {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Default constructor.
	 */
	public PerformanceFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		long begin = System.currentTimeMillis();
		HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
				request.getServletPath());
		if (httpServlet == null) { // null 代表可能是 .png 圖片及其它檔案。
			chain.doFilter(request, response);
			return;
		}

		request.setAttribute("ms", begin);
		chain.doFilter(request, response);
		logger.info("requestURL="
				+ ((HttpServletRequest) request).getRequestURL() + ", 共耗時 "
				+ (System.currentTimeMillis() - begin) + " ms.");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
