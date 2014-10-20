/**
 * idv.jiangsir.objects - Contest.java
 * 2008/2/19 下午 04:32:20
 * jiangsir
 */
package tw.jiangsir.Utils.Objects;

import java.sql.Time;
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
	private int pagesize = 20;
	@Property(key = "DefaultLogin")
	@Persistent(name = "defaultlogin")
	private String defaultlogin = "Login";
	@Property(key = "AuthDomains")
	@Persistent(name = "authdomains")
	private TreeSet<String> authdomains = new TreeSet<String>();
	@Property(key = "client_id")
	@Persistent(name = "client_id")
	private String client_id = "";
	@Property(key = "client_secret")
	@Persistent(name = "client_secret")
	private String client_secret = "";
	@Property(key = "redirect_uri")
	@Persistent(name = "redirect_url")
	private String redirect_uri = "";

	@Persistent(name = "checkhost")
	private String checkhost = "127.0.0.1";

	public enum CHECKTYPE {
		POP;
	}

	@Persistent(name = "checktype")
	private CHECKTYPE checktype = CHECKTYPE.POP;
	@Persistent(name = "workingstudents")
	private TreeSet<String> workingstudents = new TreeSet<String>();

	@Persistent(name = "bookingbegin")
	private java.sql.Time bookingbegin = java.sql.Time.valueOf("06:00:00");
	@Persistent(name = "bookingend")
	private java.sql.Time bookingend = java.sql.Time.valueOf("22:00:00");

	@Persistent(name = "signinbegin")
	private java.sql.Time signinbegin = java.sql.Time.valueOf("18:00:00");
	@Persistent(name = "signinend")
	private java.sql.Time signinend = java.sql.Time.valueOf("22:00:00");

	@Persistent(name = "punishingthreshold")
	private int punishingthreshold = 3;
	@Persistent(name = "punishingdays")
	private int punishingdays = 14;

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

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pageSize) {
		pagesize = pageSize;
	}

	public void setPagesize(String pageSize) {
		this.setPagesize(Integer.parseInt(pageSize));
	}

	public String getDefaultlogin() {
		return defaultlogin;
	}

	public void setDefaultlogin(String defaultLogin) {
		defaultlogin = defaultLogin;
	}

	public TreeSet<String> getAuthdomains() {
		return authdomains;
	}

	public void setAuthdomains(TreeSet<String> authDomains) {
		authdomains = authDomains;
	}

	public void setAuthdomains(String authDomains) {
		if (authDomains == null || "".equals(authDomains)) {
			return;
		}
		this.setAuthdomains(StringTool.String2TreeSet(authDomains));
	}

	public TreeSet<String> getWorkingstudents() {
		return workingstudents;
	}

	public void setWorkingstudents(TreeSet<String> workingstudents) {
		this.workingstudents = workingstudents;
	}

	public void setWorkingstudents(String workingstudents) {
		if (workingstudents == null || "".equals(workingstudents.trim())) {
			return;
		}
		this.setWorkingstudents(StringTool.String2TreeSet(workingstudents));
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

	public java.sql.Time getSigninbegin() {
		return signinbegin;
	}

	public int getPunishingthreshold() {
		return punishingthreshold;
	}

	public void setPunishingthreshold(int punishingthreshold) {
		this.punishingthreshold = punishingthreshold;
	}

	public void setPunishingthreshold(String punishingthreshold) {
		if (punishingthreshold == null) {
			return;
		}
		this.setPunishingthreshold(String.valueOf(punishingthreshold));
	}

	public int getPunishingdays() {
		return punishingdays;
	}

	public void setPunishingdays(int punishingdays) {
		this.punishingdays = punishingdays;
	}

	public void setPunishingdays(String punishingdays) {
		if (punishingdays == null) {
			return;
		}
		this.setPunishingdays(Integer.parseInt(punishingdays));
	}

	public void setSigninbegin(String signinbegin) {
		this.setSigninbegin(Time.valueOf(signinbegin));
	}

	public void setSigninbegin(java.sql.Time signinbegin) {
		this.signinbegin = signinbegin;
	}

	public java.sql.Time getSigninend() {
		return signinend;
	}

	public void setSigninend(java.sql.Time signinend) {
		this.signinend = signinend;
	}

	public void setSigninend(String signinend) {
		this.setSigninend(Time.valueOf(signinend));
	}

	public java.sql.Time getBookingbegin() {
		return bookingbegin;
	}

	public void setBookingbegin(String bookingbegin) {
		this.setBookingbegin(Time.valueOf(bookingbegin));
	}

	public void setBookingbegin(java.sql.Time bookingbegin) {
		this.bookingbegin = bookingbegin;
	}

	public java.sql.Time getBookingend() {
		return bookingend;
	}

	public void setBookingend(String bookingend) {
		this.setBookingend(Time.valueOf(bookingend));
	}

	public void setBookingend(java.sql.Time bookingend) {
		this.bookingend = bookingend;
	}

	public String getCheckhost() {
		return checkhost;
	}

	public void setCheckhost(String checkhost) {
		if (checkhost == null) {
			return;
		}
		this.checkhost = checkhost.trim();
	}

	public CHECKTYPE getChecktype() {
		return checktype;
	}

	public void setChecktype(CHECKTYPE checktype) {
		if (checktype == null) {
			return;
		}
		this.checktype = checktype;
	}

	public void setChecktype(String checktype) {
		if (checktype == null) {
			return;
		}
		this.setChecktype(CHECKTYPE.valueOf(checktype));
	}

}
