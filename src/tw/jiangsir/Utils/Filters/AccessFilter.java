package tw.jiangsir.Utils.Filters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.Cause;
import tw.jiangsir.Utils.Interfaces.IAccessible;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter(filterName = "AccessFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class AccessFilter implements Filter {

    /**
     * Default constructor.
     */
    public AccessFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;

	HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
		request.getServletPath());

	/**
	 * 最後一關，確定該使用者已經具備存取這個頁面之後，才進行判斷。<br>
	 * 由 servlet 獲得的參數來決定是否可以存取。固定寫在 servlet.isAccessible(request);
	 */
	for (Class<?> iface : httpServlet.getClass().getInterfaces()) {
	    if (iface == IAccessible.class) {
		for (Method method : IAccessible.class.getMethods()) {
		    try {
			Method servletMethod = httpServlet.getClass()
				.getDeclaredMethod(method.getName(),
					HttpServletRequest.class);
			servletMethod.invoke(httpServlet.getClass()
				.newInstance(), new Object[] { request });
		    } catch (SecurityException e) {
			e.printStackTrace();
			throw new AccessException(new Cause(e));
		    } catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new AccessException(new Cause(e));
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new AccessException(new Cause(e));
		    } catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new AccessException(new Cause(e));
		    } catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof AccessException) {
			    throw (AccessException) e.getTargetException();
			}
			throw new AccessException(new Cause(e));
		    } catch (InstantiationException e) {
			e.printStackTrace();
			throw new AccessException(new Cause(e));
		    }
		}
	    }
	}

	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
