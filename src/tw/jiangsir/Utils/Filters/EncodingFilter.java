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
		HttpServletResponse resp = (HttpServletResponse) response;

		req.setCharacterEncoding(ENCODING);
		// resp 也要設定好 ENCODING 否則直接 response.writer 輸出會亂碼。
		resp.setContentType("text/html;charset=" + ENCODING);
		// resp 也要設定好 ENCODING 否則直接 response.writer 輸出會亂碼。
		resp.setCharacterEncoding(ENCODING);

		// if ("GET".equals(req.getMethod())) {
		// req = new EncodingWrapper(req, ENCODING);
		// }
		System.out.println(req.getMethod() + ": ENCODING=" + ENCODING
				+ ", uri=" + req.getRequestURI() + "?" + req.getQueryString());
		chain.doFilter(req, resp);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
