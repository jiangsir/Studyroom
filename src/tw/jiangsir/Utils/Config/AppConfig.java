/**
 * idv.jiangsir.objects - Contest.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package tw.jiangsir.Utils.Config;

import java.util.TreeSet;

import tw.jiangsir.Utils.Annotations.Property;
import tw.jiangsir.Utils.Tools.StringTool;

/**
 * @author jiangsir
 * 
 */
public class AppConfig {

	@Property(key = "ManagerIP")
	private String ManagerIP = "*";
	@Property(key = "Title")
	private String Title = "A Title for Your Site";
	@Property(key = "Author")
	private String Author = "Qixun Jiang";
	@Property(key = "AuthorEmail")
	private String AuthorEmail = "jiangsir@zerojudge.tw";
	@Property(key = "AllowedIP")
	private String AllowedIP = "*";
	@Property(key = "PageSize")
	private int PageSize = 20;
	@Property(key = "DefaultLogin")
	private String DefaultLogin = "Login";
	@Property(key = "AuthDomains")
	private TreeSet<String> AuthDomains = new TreeSet<String>();
	@Property(key = "client_id")
	private String client_id = "";
	@Property(key = "client_secret")
	private String client_secret = "";
	@Property(key = "redirect_uri")
	private String redirect_uri = "";

	public String getManagerIP() {
		return ManagerIP;
	}

	public void setManagerIP(String managerIP) {
		ManagerIP = managerIP;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getAuthorEmail() {
		return AuthorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		AuthorEmail = authorEmail;
	}

	public String getAllowedIP() {
		return AllowedIP;
	}

	public void setAllowedIP(String allowedIP) {
		AllowedIP = allowedIP;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public void setPageSize(String pageSize) {
		this.setPageSize(Integer.parseInt(pageSize));
	}

	public String getDefaultLogin() {
		return DefaultLogin;
	}

	public void setDefaultLogin(String defaultLogin) {
		DefaultLogin = defaultLogin;
	}

	public TreeSet<String> getAuthDomains() {
		return AuthDomains;
	}

	public void setAuthDomains(TreeSet<String> authDomains) {
		AuthDomains = authDomains;
	}

	public void setAuthDomains(String authDomains) {
		if (authDomains == null || "".equals(authDomains)) {
			return;
		}
		this.setAuthDomains(StringTool.String2TreeSet(authDomains));
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

}
