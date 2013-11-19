package tw.jiangsir.Utils.Filters;

import java.io.IOException;
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
import tw.jiangsir.Utils.Config.RequestScope;
import tw.jiangsir.Utils.Exceptions.RoleException;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Services.UserService;
import tw.jiangsir.Utils.Servlets.LoginServlet;

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
	System.out.println("RoleFilter: " + servletPath);
	new RequestScope(request).setReturnPage();
	HttpServlet httpServlet = ApplicationScope.getUrlpatterns().get(
		servletPath);
	if (httpServlet == null
		|| httpServlet.getClass().getAnnotation(RoleSetting.class) == null) {
	    chain.doFilter(request, response);
	    return;
	}

	HttpSession session = request.getSession(false);

	UserService userService = new UserService();
	if (!servletPath.equals(LoginServlet.urlPatterns[0])) {
	    System.out.println("servletPath=" + request.getServletPath());
	    // request.setAttribute("returnPage",
	    // servletPath
	    // + (request.getQueryString() == null ? "" : "?"
	    // + request.getQueryString()));
	    // new RequestScope(request).setReturnPage();
	}
	if (!userService.isOnlineUser(session)) {
	    request.getRequestDispatcher("/Login.jsp").forward(request,
		    response);
	    return;
	}
	User currentUser = userService.getCurrentUser(session);
	if (!this.isUserInRoles(currentUser, httpServlet.getClass()
		.getAnnotation(RoleSetting.class))) {
	    throw new RoleException("您沒有權限瀏覽這個頁面。");
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
