package tw.jiangsir.Utils.DAOs;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.CurrentUser;
import tw.jiangsir.Utils.Objects.User;
import tw.jiangsir.Utils.Scopes.SessionScope;

public class UserService {

	public boolean isExitedUser(String account, String passwd) {
		if (new UserDAO().getUserByAccountPasswd(account, passwd) == null) {
			return false;
		}
		return true;
	}

	public boolean isExitedAccount(String account) {
		try {
			if (new UserDAO().getUserByAccount(account) == null) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 只判斷這個 session 是否有登入。
	 * 
	 * @param session
	 * @return
	 */
	public boolean isUserOnline(HttpSession session) {
		return new SessionScope(session).getCurrentUser() == null ? false
				: true;
	}

	public User getUserByAccount(String account) {
		try {
			return new UserDAO().getUserByAccount(account);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public ArrayList<User> getUsers() {
		return new UserDAO().getUsers();
	}

	public User getUserById(long userid) {
		try {
			return new UserDAO().getUserById(userid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public User getUserByAccountPasswd(String account, String passwd) {
		return new UserDAO().getUserByAccountPasswd(account, passwd);
	}

	/**
	 * 用 userid 到資料庫中取出 user 製作成 CurrentUser。準備放入 SessionScope 當中。
	 * 
	 * @param userid
	 * @param session
	 * @return
	 */
	public CurrentUser createCurrentUser(long userid, HttpSession session) {
		return new CurrentUserDAO().getCurrentUserById(userid, session);
	}

	public int insert(User user) throws DataException {
		try {
			int userid = new UserDAO().insert(user);
			if (userid == 0) {
				throw new DataException("沒有新增！");
			}
			return userid;
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
