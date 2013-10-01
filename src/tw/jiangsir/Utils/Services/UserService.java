package tw.jiangsir.Utils.Services;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.DAOs.UserDAO;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.User;

public class UserService {

	public boolean isExitedUser(String account, String passwd) {
		if (new UserDAO().getUserByAccountPasswd(account, passwd) == null) {
			return false;
		}
		return true;
	}

	public boolean isExitedAccount(String account) {
		if (new UserDAO().getUserByAccount(account) == null) {
			return false;
		}
		return true;
	}

	public boolean isOnlineUser(HttpSession session) {
		return this.getCurrentUser(session) == null ? false : true;
	}

	/**
	 * 取得目前該 session 中登入的 user, 未登入的話回傳 null
	 * 
	 * @param session
	 * @return
	 */
	public User getCurrentUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

	public User getUser(String account) {
		return new UserDAO().getUserByAccount(account);
	}

	public User getUser(int userid) {
		return new UserDAO().getUserById(userid);
	}

	public User getUser(String account, String passwd) {
		return new UserDAO().getUserByAccountPasswd(account, passwd);
	}

	public void insert(User user) throws DataException {
		try {
			if (new UserDAO().insert(user) == 0) {
				throw new DataException("沒有新增！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void update(User user) throws DataException {
		try {
			if (new UserDAO().update(user) == 0) {
				throw new DataException("沒有更新！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(int userid) throws DataException {
		try {
			if (!new UserDAO().delete(userid)) {
				throw new DataException("刪除失敗！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

}
