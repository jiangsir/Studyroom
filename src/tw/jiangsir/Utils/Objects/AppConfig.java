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
	@Persistent(name = "workingstudentids")
	private TreeSet<String> workingstudentids = new TreeSet<String>();

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
	private TreeSet<IpAddress> signinip = new TreeSet<IpAddress>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			add(new IpAddress("127.0.0.1"));
		}
	};
	@Property(key = "announcement")
	@Persistent(name = "announcement")
	private String announcement = "";
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	private String[][] seatMatrix = {
			{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
					"20", "21", "22", "23", "24", "25", "26"},
			{"27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44",
					"45", "46", "47", "48", "49", "50", "51", "52"},
			{"53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
					"71", "72", "73", "74", "75", "76", "", ""},
			{"77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94",
					"95", "96", "97", "98", "99", "100", "", ""},
			{"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115",
					"116", "117", "118", "119", "120", "", "", "", "", "", ""},
			{"121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135",
					"136", "137", "138", "139", "140", "", "", "", "", "", ""}};

	// =====================================================================================

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

	public TreeSet<String> getWorkingstudentids() {
		return workingstudentids;
	}

	public void setWorkingstudentids(TreeSet<String> workingstudentids) {
		this.workingstudentids = workingstudentids;
	}

	public void setWorkingstudentids(String workingstudentids) {
		if (workingstudentids == null || "".equals(workingstudentids.trim())) {
			return;
		}
		this.setWorkingstudentids(StringTool.String2TreeSet(workingstudentids));
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

	// public String getSigninip() {
	// return signinip;
	// }
	//
	// public void setSigninip(String signinip) {
	// if (signinip == null) {
	// return;
	// }
	// this.signinip = signinip.trim();
	// }
	//
	//
	public TreeSet<IpAddress> getSigninip() {
		return signinip;
	}

	public void setSigninip(TreeSet<IpAddress> ips) {
		if (ips == null) {
			return;
		}
		this.signinip = ips;
	}

	public void setSigninip(String signinip) {
		if (signinip == null) {
			return;
		}
		TreeSet<IpAddress> ips = new TreeSet<IpAddress>();
		for (String ip : StringTool.String2TreeSet(signinip)) {
			ips.add(new IpAddress(ip));
		}
		this.setSigninip(ips);
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

	public void setPunishingthreshold(Integer punishingthreshold) {
		this.punishingthreshold = punishingthreshold;
	}

	public void setPunishingthreshold(String punishingthreshold) {
		if (punishingthreshold == null) {
			return;
		}
		this.setPunishingthreshold(Integer.valueOf(punishingthreshold));
	}

	public int getPunishingdays() {
		return punishingdays;
	}

	public void setPunishingdays(Integer punishingdays) {
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

	public String[][] getSeatMatrix() {
		return seatMatrix;
	}

	public void setSeatMatrix(String[][] seatMatrix) {
		this.seatMatrix = seatMatrix;
	}

}
