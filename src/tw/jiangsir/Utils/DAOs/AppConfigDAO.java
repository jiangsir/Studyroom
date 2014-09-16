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
import java.util.TreeMap;
import tw.jiangsir.Utils.Config.AppConfig;
import tw.jiangsir.Utils.Exceptions.DataException;

/**
 * @author jiangsir
 * 
 */
public class AppConfigDAO extends SuperDAO<AppConfig> {

	@Override
	protected synchronized int insert(AppConfig appConfig) throws SQLException {
		String sql = "INSERT INTO appconfigs(title, header, author, pagesize, defaultlogin, authdomains, "
				+ "client_id, client_secret, redirect_uri, starttime, deadline, signinip, announcement, "
				+ "timestamp) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,now());";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setString(3, appConfig.getAuthor());
		pstmt.setInt(4, appConfig.getPageSize());
		pstmt.setString(5, appConfig.getDefaultLogin());
		pstmt.setString(6, appConfig.getAuthDomains().toString());
		pstmt.setString(7, appConfig.getClient_id());
		pstmt.setString(8, appConfig.getClient_secret());
		pstmt.setString(9, appConfig.getRedirect_uri());
		pstmt.setTime(10, appConfig.getStarttime());
		pstmt.setTime(11, appConfig.getDeadline());
		pstmt.setString(12, appConfig.getSigninip());
		pstmt.setString(13, appConfig.getAnnouncement());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(AppConfig appConfig) throws SQLException {
		String sql = "UPDATE appconfigs SET title=?, header=?, author=?, pagesize=?, defaultlogin=?, "
				+ "authdomains=?, client_id=?, client_secret=?, redirect_uri=?, starttime=?, deadline=?, "
				+ "signinip=?, announcement=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setString(3, appConfig.getAuthor());
		pstmt.setInt(4, appConfig.getPageSize());
		pstmt.setString(5, appConfig.getDefaultLogin());
		pstmt.setString(6, appConfig.getAuthDomains().toString());
		pstmt.setString(7, appConfig.getClient_id());
		pstmt.setString(8, appConfig.getClient_secret());
		pstmt.setString(9, appConfig.getRedirect_uri());
		pstmt.setTime(10, appConfig.getStarttime());
		pstmt.setTime(11, appConfig.getDeadline());
		pstmt.setString(12, appConfig.getSigninip());
		pstmt.setString(13, appConfig.getAnnouncement());
		pstmt.setLong(14, appConfig.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM appconfigs WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	protected ArrayList<AppConfig> getAppConfigByFields(
			TreeMap<String, Object> fields, String orderby, int page) {
		String sql = "SELECT * FROM appconfigs "
				+ this.makeFields(fields, orderby, page);
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			int i = 1;
			for (String field : fields.keySet()) {
				pstmt.setObject(i++, fields.get(field));
			}
			return executeQuery(pstmt, AppConfig.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

}
