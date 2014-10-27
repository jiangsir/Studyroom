/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package tw.jiangsir.Studyroom.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import tw.jiangsir.Utils.DAOs.SuperDAO;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.AppConfig;

/**
 * @author jiangsir
 * 
 */
public class AppConfigDAO extends SuperDAO<AppConfig> {

	@Override
	protected synchronized int insert(AppConfig appConfig) throws SQLException {
		String sql = "INSERT INTO appconfigs(title, header, author, pagesize, defaultlogin, authdomains, "
				+ "checktype, checkhost, client_id, client_secret, redirect_uri, bookingbegin, bookingend, "
				+ "signinbegin, signinend, punishingthreshold, punishingdays, signinip, workingstudentids, "
				+ "announcement, timestamp) VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,now());";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setString(3, appConfig.getAuthor());
		pstmt.setInt(4, appConfig.getPagesize());
		pstmt.setString(5, appConfig.getDefaultlogin());
		pstmt.setString(6, appConfig.getAuthdomains().toString());
		pstmt.setString(7, appConfig.getChecktype().toString());
		pstmt.setString(8, appConfig.getCheckhost());
		pstmt.setString(9, appConfig.getClient_id());
		pstmt.setString(10, appConfig.getClient_secret());
		pstmt.setString(11, appConfig.getRedirect_uri());
		pstmt.setTime(12, appConfig.getBookingbegin());
		pstmt.setTime(13, appConfig.getBookingend());
		pstmt.setTime(14, appConfig.getSigninbegin());
		pstmt.setTime(15, appConfig.getSigninend());
		pstmt.setInt(16, appConfig.getPunishingthreshold());
		pstmt.setInt(17, appConfig.getPunishingdays());
		pstmt.setString(18, appConfig.getSigninip());
		pstmt.setString(19, appConfig.getWorkingstudentids().toString());
		pstmt.setString(20, appConfig.getAnnouncement());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(AppConfig appConfig) throws SQLException {
		String sql = "UPDATE appconfigs SET title=?, header=?, author=?, pagesize=?, defaultlogin=?, "
				+ "authdomains=?, checktype=?, checkhost=?, client_id=?, client_secret=?, redirect_uri=?, "
				+ "bookingbegin=?, bookingend=?, signinbegin=?, signinend=?, punishingthreshold=?, "
				+ "punishingdays=?, signinip=?, announcement=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, appConfig.getTitle());
		pstmt.setString(2, appConfig.getHeader());
		pstmt.setString(3, appConfig.getAuthor());
		pstmt.setInt(4, appConfig.getPagesize());
		pstmt.setString(5, appConfig.getDefaultlogin());
		pstmt.setString(6, appConfig.getAuthdomains().toString());
		pstmt.setString(7, appConfig.getChecktype().toString());
		pstmt.setString(8, appConfig.getCheckhost());
		pstmt.setString(9, appConfig.getClient_id());
		pstmt.setString(10, appConfig.getClient_secret());
		pstmt.setString(11, appConfig.getRedirect_uri());
		pstmt.setTime(12, appConfig.getBookingbegin());
		pstmt.setTime(13, appConfig.getBookingend());
		pstmt.setTime(14, appConfig.getSigninbegin());
		pstmt.setTime(15, appConfig.getSigninend());
		pstmt.setInt(16, appConfig.getPunishingthreshold());
		pstmt.setInt(17, appConfig.getPunishingdays());
		pstmt.setString(18, appConfig.getSigninip());
		pstmt.setString(19, appConfig.getAnnouncement());
		pstmt.setLong(20, appConfig.getId());
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

	/**
	 * 清空 appconfig
	 * 
	 * @return
	 */
	protected boolean truncate() {
		return this.execute("TRUNCATE TABLE `appconfigs`");
	}

}
