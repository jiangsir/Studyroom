/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import tw.jiangsir.Studyroom.Objects.Violation;
import tw.jiangsir.Utils.DAOs.SuperDAO;
import tw.jiangsir.Utils.Exceptions.DataException;

/**
 * @author jiangsir
 * 
 */
public class ViolationDAO extends SuperDAO<Violation> {

	@Override
	protected synchronized int insert(Violation violation) throws SQLException {
		String sql = "INSERT INTO violations(`date`, `studentid`, `reason`, `comment`, `status`, "
				+ "`timestamp`) VALUES (?,?,?,?,?,?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setDate(1, violation.getDate());
		pstmt.setString(2, violation.getStudentid());
		pstmt.setString(3, violation.getReason().name());
		pstmt.setString(4, violation.getComment());
		pstmt.setString(5, violation.getStatus().name());
		pstmt.setTimestamp(6, violation.getTimestamp());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(Violation violation) throws SQLException {
		String sql = "UPDATE violations SET `date`=?, `studentid`=?, `reason`=?, `comment`=?, "
				+ "`status`=?, `timestamp`=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, violation.getDate());
		pstmt.setString(2, violation.getStudentid());
		pstmt.setString(3, violation.getReason().name());
		pstmt.setString(4, violation.getComment());
		pstmt.setString(5, violation.getStatus().name());
		pstmt.setTimestamp(6, violation.getTimestamp());
		pstmt.setLong(7, violation.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM violations WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	/**
	 * 將某學生設定為已懲罰。
	 * 
	 * @param studentid
	 * @return
	 * @throws SQLException
	 */
	protected synchronized int updatePunished(String studentid)
			throws SQLException {
		String sql = "UPDATE violations SET `status`='"
				+ Violation.STATUS.punished.name() + "' WHERE `status`='"
				+ Violation.STATUS.enable.name() + "' AND `studentid`=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	/**
	 * 清除某一天的所有違規記錄。
	 * 
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	protected boolean deleteByDate(Date date) throws SQLException {
		String sql = "DELETE FROM violations WHERE date=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, date);
		return this.executeDelete(pstmt);
	}

	protected ArrayList<Violation> getViolationsByFields(
			TreeMap<String, Object> fields, String orderby, int page) {
		String sql = "SELECT * FROM violations "
				+ this.makeFields(fields, orderby, page);

		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			int i = 1;
			for (String field : fields.keySet()) {
				pstmt.setObject(i++, fields.get(field));
			}
			return executeQuery(pstmt, Violation.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

}
