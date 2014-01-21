package tw.jiangsir.Utils.GoogleChecker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tw.jiangsir.Utils.Config.SessionScope;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = { "/GooglePopLogin" }, name = "GooglePopLogin", initParams = { @WebInitParam(name = "VIEW", value = "/Login.jsp") })
public class GooglePopLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String[] urlPatterns = GooglePopLoginServlet.class
			.getAnnotation(WebServlet.class).urlPatterns();
	public ServletConfig config;
	public String VIEW = "";

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		this.VIEW = config.getInitParameter("VIEW");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");

		if (this.isGmailAccount(account + "@stu.nknush.kh.edu.tw", passwd)) {
			CurrentUser currentUser = new CurrentUser();
			currentUser.setAccount(account);
			currentUser.setPasswd(passwd);
			currentUser.setName(account);
			currentUser.setRole(User.ROLE.USER);
			currentUser.setSession(session);
			SessionScope sessionScope = new SessionScope(session);

			sessionScope.setCurrentUser(currentUser);
			response.sendRedirect(request.getContextPath()
					+ sessionScope.getHistories().get(0));
			return;
		} else {
			request.getRequestDispatcher(VIEW).forward(request, response);
			return;
		}
	}

	/**
	 * 檢查 gmail(app) 密碼是否正確
	 * 
	 * @return
	 */
	public boolean isGmailAccount(String email, String passwd) {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port", "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");

		Session session = Session.getDefaultInstance(props, null);

		// 請將紅色部分對應替換成你的郵箱帳號和密碼
		URLName urln = new URLName("pop3", "pop.gmail.com", 995, null, email,
				passwd);
		try {
			Store store = session.getStore(urln);
			store.connect();
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			if (e instanceof AuthenticationFailedException) {
				throw new DataException("驗證有誤，帳號密碼可能有誤！");
			}
			throw new DataException(e);
		}
		return true;
	}

}
