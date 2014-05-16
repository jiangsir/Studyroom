package tw.jiangsir.Utils.Filters;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
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
import javax.servlet.http.HttpSession;
import tw.jiangsir.Utils.Annotations.RoleSetting;
import tw.jiangsir.Utils.Config.ApplicationScope;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Exceptions.AccessException;
import tw.jiangsir.Utils.Exceptions.Cause;
import tw.jiangsir.Utils.Exceptions.RoleException;
import tw.jiangsir.Utils.Interfaces.IAccessFilter;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;

/**
 * Servlet Filter implementation class RoleFilter
 */
@WebFilter(filterName = "RoleFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class RoleFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public RoleFilter() {
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
		String servletPath = request.getServletPath();

		HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
				servletPath);
		if (httpServlet == null
				|| httpServlet.getClass().getAnnotation(RoleSetting.class) == null) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = request.getSession(false);

		UserService userService = new UserService();
		if (!userService.isUserOnline(session)) {
			request.setAttribute("defaultLogin", ApplicationScope
					.getAppConfig().getDefaultLogin());
			request.getRequestDispatcher("/Login.jsp").forward(request,
					response);
			return;
		}
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		if (!this.isUserInRoles(currentUser, httpServlet.getClass()
				.getAnnotation(RoleSetting.class))) {
			throw new RoleException("您沒有權限瀏覽這個頁面。");
		}

		/**
		 * 這裡一定要在使用者確定登入之後才進行判斷。<br>
		 * 如果單獨成為一個 Filter, 則不見得會在 RoleFilter 之前執行，導致沒有登入就判斷 Accessible 一定無權讀取的。
		 * 最後一關，確定該使用者已經具備存取這個頁面之後，才進行判斷。<br>
		 * 由 servlet 獲得的參數來決定是否可以存取。
		 */
		for (Class<?> iface : httpServlet.getClass().getInterfaces()) {
			if (iface == IAccessFilter.class) {
				for (Method method : IAccessFilter.class.getMethods()) {
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
						// if (e.getTargetException() instanceof
						// AccessException) {
						// throw (AccessException) e.getTargetException();
						// }
						throw new AccessException(new Cause(
								e.getTargetException()));
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new AccessException(new Cause(e));
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	private boolean isUserInRoles(User user, RoleSetting servletRole) {
		HashSet<User.ROLE> roleSet = new HashSet<User.ROLE>();

		if (servletRole == null) {
			return false;
		}

		// 加入 高於指定的 role
		for (User.ROLE role : User.ROLE.values()) {
			if (servletRole.allowHigherThen().ordinal() >= role.ordinal()) {
				roleSet.add(role);
			}
		}

		// 加入 個別指定的 role
		for (User.ROLE role : servletRole.allows()) {
			if (user.getRole() == role) {
				roleSet.add(role);
			}
		}

		// 移除 低於指定的 role
		for (User.ROLE role : User.ROLE.values()) {
			if (servletRole.denyLowerThen().ordinal() <= role.ordinal()) {
				roleSet.remove(role);
			}
		}

		// 移除個別指定的 role
		for (User.ROLE role : servletRole.denys()) {
			if (user.getRole() == role) {
				roleSet.remove(role);
			}
		}
		return roleSet.contains(user.getRole());
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
