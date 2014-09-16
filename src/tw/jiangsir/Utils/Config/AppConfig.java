/**
 * idv.jiangsir.objects - Contest.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package tw.jiangsir.Utils.Config;

import java.sql.Timestamp;
import java.util.TreeSet;

import tw.jiangsir.Utils.Annotations.Persistent;
import tw.jiangsir.Utils.Annotations.Property;
import tw.jiangsir.Utils.Tools.StringTool;

/**
 * @author jiangsir
 * 
 */
public class AppConfig {
	// <entry key="AllowedIP">*</entry>
	// <entry key="Title">高師大附中－晚自習教室訂位系統</entry>
	// <entry key="TitleImage">images/BANNER_IMAGE.png</entry>
	// <entry key="Header">高師大附中－晚自習教室訂位系統</entry>
	// <entry key="Author">Qixun Jiang</entry>
	// <entry key="PageSize">20</entry>
	// <entry key="DefaultLogin">Login</entry>
	// <entry
	// key="AuthDomains">["stu.nknush.kh.edu.tw","tea.nknush.kh.edu.tw"]</entry>
	// <entry
	// key="client_id">953896450978-u82dc8hgj246t9fva61f0bl21v0ek97n.apps.googleusercontent.com</entry>
	// <entry key="client_secret">g_NdXLE9SvStnaYdpiQXcn8g</entry>
	// <entry
	// key="redirect_uri">http://127.0.0.1:8080/Studyroom/oauth2callback</entry>
	// <entry key="starttime">06:00:00</entry>
	// <entry key="deadline">22:00:00</entry>
	// <entry key="signinip">127.0.0.1</entry>

	@Persistent(name = "id")
	private Long id = 0L;
	@Property(key = "Title")
	@Persistent(name = "title")
	private String Title = "A Title for Your Site";
	@Property(key = "Header")
	@Persistent(name = "header")
	private String Header = "Header";
	@Property(key = "Author")
	@Persistent(name = "author")
	private String Author = "Qixun Jiang";
	@Property(key = "PageSize")
	@Persistent(name = "pagesize")
	private int PageSize = 20;
	@Property(key = "DefaultLogin")
	@Persistent(name = "defaultlogin")
	private String DefaultLogin = "Login";
	@Property(key = "AuthDomains")
	@Persistent(name = "authdomains")
	private TreeSet<String> AuthDomains = new TreeSet<String>();
	@Property(key = "client_id")
	@Persistent(name = "client_id")
	private String client_id = "";
	@Property(key = "client_secret")
	@Persistent(name = "client_secret")
	private String client_secret = "";
	@Property(key = "redirect_uri")
	@Persistent(name = "redirect_url")
	private String redirect_uri = "";
	@Property(key = "starttime")
	@Persistent(name = "starttime")
	private java.sql.Time starttime = java.sql.Time.valueOf("06:00:00");
	@Property(key = "deadline")
	@Persistent(name = "deadline")
	private java.sql.Time deadline = java.sql.Time.valueOf("22:00:00");
	@Property(key = "signinip")
	@Persistent(name = "signinip")
	private String signinip = "127.0.0.1";
	@Property(key = "announcement")
	@Persistent(name = "announcement")
	private String announcement = "";
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getHeader() {
		return Header;
	}

	public void setHeader(String header) {
		Header = header;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
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

	public java.sql.Time getStarttime() {
		return starttime;
	}

	public void setStarttime(java.sql.Time starttime) {
		this.starttime = starttime;
	}

	public void setStarttime(String starttime) {
		this.setStarttime(java.sql.Time.valueOf(starttime));
	}

	public java.sql.Time getDeadline() {
		return deadline;
	}

	public void setDeadline(java.sql.Time deadline) {
		this.deadline = deadline;
	}

	public void setDeadline(String deadline) {
		this.setDeadline(java.sql.Time.valueOf(deadline));
	}

	public String getSigninip() {
		return signinip;
	}

	public void setSigninip(String signinip) {
		if (signinip == null) {
			return;
		}
		this.signinip = signinip.trim();
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		if (announcement == null) {
			return;
		}
		this.announcement = announcement.trim();
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
