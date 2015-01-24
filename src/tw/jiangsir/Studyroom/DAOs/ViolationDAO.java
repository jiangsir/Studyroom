/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Studyroom.Tables.Violation;
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

	// /**
	// * 將某學生設定為處罰中，停權中。
	// *
	// * @param studentid
	// * @return
	// * @throws SQLException
	// */
	// protected synchronized int updatePunishing(String studentid)
	// throws SQLException {
	// String sql = "UPDATE violations SET `status`='"
	// + Violation.STATUS.punishing.name() + "' WHERE `status`='"
	// + Violation.STATUS.enable.name() + "' AND `studentid`=?";
	// int result = -1;
	// PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
	// pstmt.setString(1, studentid);
	// result = this.executeUpdate(pstmt);
	// pstmt.close();
	// return result;
	// }

	/**
	 * 將某學生設定為已經處罰完畢。也就是 14 天已過。
	 * 
	 * @param studentid
	 * @return
	 * @throws SQLException
	 */
	protected synchronized int setEnable2PunishedByStudentid(String studentid)
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

	/**
	 * 統計有違規的所有學生資料。
	 * 
	 * @return
	 */
	protected LinkedHashMap<String, Integer> getHashMapOfStudentidCountByEnableViolations() {
		// SELECT COUNT(studentid) AS count,studentid FROM `violations` WHERE
		// status="enable" GROUP BY studentid ORDER BY count DESC;
		LinkedHashMap<String, Integer> studentids = new LinkedHashMap<String, Integer>();
		String sql = "SELECT COUNT(studentid) AS count,studentid FROM `violations` WHERE status='"
				+ Violation.STATUS.enable.name()
				+ "' GROUP BY studentid ORDER BY count DESC;";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				studentids.put(rs.getString("studentid"), rs.getInt("count"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentids;
	}

	private String status_enable_punished = "(`status`='"
			+ Violation.STATUS.enable.name() + "' OR `status`='"
			+ Violation.STATUS.punished.name() + "')";

	/**
	 * 取得目前有 1 個以上的 violation 的 student
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected ArrayList<String> getStudentidsWithEnableViolation()
			throws SQLException {
		String sql = "SELECT COUNT(studentid) AS count,studentid FROM violations WHERE "
				+ status_enable_punished
				+ " GROUP BY studentid ORDER BY count DESC";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		ArrayList<String> studentids = new ArrayList<String>();
		for (Violation violation : this.executeQuery(pstmt, Violation.class)) {
			studentids.add(violation.getStudentid());
		}
		return studentids;
	}

	/**
	 * 取得某個 studentid 的所有 enable Violations
	 * 
	 * @param studentid
	 * @return
	 * @throws SQLException
	 */
	protected ArrayList<Violation> getEnabledViolations(String studentid)
			throws SQLException {
		String sql = "SELECT * FROM violations WHERE studentid=? AND "
				+ status_enable_punished + " ORDER BY date ASC";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		return this.executeQuery(pstmt, Violation.class);
	}
}
