package tw.jiangsir.Utils.Config;

import java.io.Serializable;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import tw.jiangsir.Utils.Servlets.LoginServlet;
import tw.jiangsir.Utils.Servlets.LogoutServlet;
import tw.jiangsir.Utils.Servlets.ShowSessionsServlet;

public class RequestScope implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HttpServletRequest request = null;
    private ArrayList<String> returnPages = new ArrayList<String>() {
	private static final long serialVersionUID = 1L;
	{
	    add("");
	    add("");
	}
    };

    @SuppressWarnings("unchecked")
    public RequestScope(HttpServletRequest request) {
	this.request = request;
	this.setReturnPages((ArrayList<String>) request
		.getAttribute("returnPages"));
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getReturnPages() {
	if (request.getAttribute("returnPages") != null) {
	    this.returnPages = (ArrayList<String>) request
		    .getAttribute("returnPages");
	}
	return this.returnPages;
    }

    public void setReturnPages(ArrayList<String> returnPages) {
	if (returnPages == null) {
	    return;
	}
	this.returnPages = returnPages;
	request.setAttribute("returnPages", returnPages);
    }

    public void setReturnPage() {
	String servletPath = this.request.getServletPath();
	String querystring = this.request.getQueryString();
	if (servletPath.startsWith(LoginServlet.class.getAnnotation(
		WebServlet.class).urlPatterns()[0])
		|| servletPath.startsWith(LogoutServlet.class.getAnnotation(
			WebServlet.class).urlPatterns()[0])
		|| servletPath.startsWith(ShowSessionsServlet.class
			.getAnnotation(WebServlet.class).urlPatterns()[0])
		|| servletPath.startsWith("/Update")
		|| servletPath.startsWith("/Insert")
		|| servletPath.startsWith("/api/")
		|| servletPath.endsWith(".ajax")
		|| servletPath.endsWith(".api")) {
	    ArrayList<String> returnPages = this.getReturnPages();
	    this.setReturnPages(returnPages);
	    return;
	}
	ArrayList<String> returnPages = this.getReturnPages();
	String returnPage = servletPath
		+ (querystring == null ? "" : "?" + querystring);
	System.out.println("1returnPages=" + returnPages);
	if (!returnPage.equals(returnPages.get(0))) {
	    returnPages.remove(returnPages.size() - 1);
	    System.out.println("2returnPages=" + returnPages);
	    returnPages.add(0, servletPath
		    + (querystring == null ? "" : "?" + querystring));
	    System.out.println("3returnPages=" + returnPages);
	    this.setReturnPages(returnPages);
	}
    }

}
