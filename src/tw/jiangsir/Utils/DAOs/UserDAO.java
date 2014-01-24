/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package tw.jiangsir.Utils.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.User;

/**
 * @author jiangsir
 * 
 */
public class UserDAO extends SuperDAO<User> {

	@Override
	protected synchronized int insert(User user) throws SQLException {
		String sql = "INSERT INTO users (account, passwd, name, role) VALUES (?,MD5(?),?, ?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, user.getAccount());
		pstmt.setString(2, user.getPasswd());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getRole().name());
		return this.executeInsert(pstmt);
	}

	/**
	 * 取得所有 user
	 * 
	 * @return
	 */
	protected ArrayList<User> getUsers() {
		String sql = "SELECT * FROM users";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return this.executeQuery(pstmt, User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected synchronized int update(User user) throws SQLException {
		String sql = "UPDATE users SET account=?, passwd=MD5(?), name=?, role=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, user.getAccount());
		pstmt.setString(2, user.getPasswd());
		pstmt.setString(3, user.getName());
		pstmt.setString(4, user.getRole().name());
		pstmt.setLong(5, user.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	protected User getUserById(long userid) {
		String sql = "SELECT * FROM users WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setLong(1, userid);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected User getUserByAccount(String account) {
		String sql = "SELECT * FROM users WHERE account=?";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用帳號密碼取得一個 User
	 * 
	 * @param account
	 * @param passwd
	 * @return
	 */
	protected User getUserByAccountPasswd(String account, String passwd)
			throws DataException {
		String sql = "SELECT * FROM users WHERE account=? AND passwd=MD5(?)";
		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			pstmt.setString(1, account);
			pstmt.setString(2, passwd);
			for (User user : this.executeQuery(pstmt, User.class)) {
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		throw new DataException("帳號密碼有誤！");
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM users WHERE id=?";
		PreparedStatement pstmt;
		pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

}
