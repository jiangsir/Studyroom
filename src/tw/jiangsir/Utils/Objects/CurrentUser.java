package tw.jiangsir.Utils.Objects;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import tw.jiangsir.Utils.Config.ApplicationScope;

public class CurrentUser extends User implements HttpSessionBindingListener,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830679620579860915L;

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().put(event.getSession().getId(), this);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		ApplicationScope.getOnlineUsers().remove(event.getSession().getId());
	}

}
