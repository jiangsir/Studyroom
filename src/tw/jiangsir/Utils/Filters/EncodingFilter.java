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

import tw.jiangsir.Utils.Wrappers.EncodingWrapper;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class EncodingFilter implements Filter {
	private String ENCODING = "UTF-8";

	/**
	 * Default constructor.
	 */
	public EncodingFilter() {
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
		if ("GET".equals(req.getMethod())) {
			req = new EncodingWrapper(req, ENCODING);
		} else {
			req.setCharacterEncoding(ENCODING);
		}
		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
