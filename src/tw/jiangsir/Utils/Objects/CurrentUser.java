package tw.jiangsir.Utils.Objects;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Scopes.SessionScope;

public class CurrentUser extends User implements HttpSessionBindingListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830679620579860915L;
	private boolean isGoogleUser = false;
	private HttpSession session = null;

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().put(event.getSession().getId(), this);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().remove(event.getSession().getId());
	}

	// public HttpSession getSession() {
	// return session;
	// }
	//
	// public void setSession(HttpSession session) {
	// this.session = session;
	// }

	public void doLogout() {
		if (session != null) {
			session.invalidate();
		}
	}

	public void setIsGoogleUser(boolean isGoogleUser) {
		this.isGoogleUser = isGoogleUser;
	}

	public boolean getIsGoogleUser() {
		return isGoogleUser;
	}

	public boolean getIsAdmin() {
		return getRole().ordinal() == 0;
	}

	@Override
	public String toString() {
		return "account=" + this.getAccount() + "(" + this.getName() + "), role=" + this.getRole();
	}

}
