package tw.jiangsir.Utils.Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.jiangsir.Utils.Wrappers.EscapeWrapper;

/**
 * Servlet Filter implementation class EscapeFilter
 */
@WebFilter(filterName = "EscapeFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class EscapeFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public EscapeFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		/**
		 * 會導致寫入資料庫的中文變成亂碼。但顯示正常！
		 */
		HttpServletRequest requestWrapper = new EscapeWrapper(req);
		chain.doFilter(requestWrapper, resp);
		// chain.doFilter(req, resp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
