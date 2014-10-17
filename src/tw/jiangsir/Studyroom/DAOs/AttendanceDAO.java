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

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Objects.Roomstatus;
import tw.jiangsir.Utils.DAOs.SuperDAO;
import tw.jiangsir.Utils.Exceptions.DataException;

/**
 * @author jiangsir
 * 
 */
public class AttendanceDAO extends SuperDAO<Attendance> {

	@Override
	protected synchronized int insert(Attendance attendance)
			throws SQLException {
		String sql = "INSERT INTO attendances(`studentid`, `date`, `status`, `timestamp`) VALUES (?,?,?,?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, attendance.getStudentid());
		pstmt.setDate(2, attendance.getDate());
		pstmt.setInt(3, attendance.getStatus().ordinal());
		pstmt.setTimestamp(4, attendance.getTimestamp());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(Attendance attendance)
			throws SQLException {
		String sql = "UPDATE attendances SET `studentid`=?, `status`=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, attendance.getStudentid());
		pstmt.setInt(2, attendance.getStatus().ordinal());
		pstmt.setLong(3, attendance.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	public int getAttendCount(Date date) {
		String sql = "SELECT COUNT(*) AS COUNT FROM `attendances` WHERE date='"
				+ date + "' GROUP BY studentid,date";
		if (this.executeQueryId(sql).size() == 0) {
			return 0;
		}
		return this.executeQueryId(sql).size();
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM attendances WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	protected ArrayList<Attendance> getAttendanceByFields(
			TreeMap<String, Object> fields, String orderby, int page) {
		String sql = "SELECT * FROM attendances "
				+ this.makeFields(fields, orderby, page);

		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			int i = 1;
			for (String field : fields.keySet()) {
				pstmt.setObject(i++, fields.get(field));
			}
			return executeQuery(pstmt, Attendance.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

}
