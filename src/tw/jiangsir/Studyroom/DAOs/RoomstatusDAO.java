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

import tw.jiangsir.Studyroom.Tables.Roomstatus;
import tw.jiangsir.Utils.DAOs.SuperDAO;
import tw.jiangsir.Utils.Exceptions.DataException;

/**
 * @author jiangsir
 * 
 */
public class RoomstatusDAO extends SuperDAO<Roomstatus> {

	@Override
	protected synchronized int insert(Roomstatus roomstatus) throws SQLException {
		String sql = "INSERT INTO roomstatuss(`date`, `status`, `reason`, `timestamp`) VALUES (?,?,?,?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setDate(1, roomstatus.getDate());
		pstmt.setInt(2, roomstatus.getStatus().ordinal());
		pstmt.setString(3, roomstatus.getReason());
		pstmt.setTimestamp(4, roomstatus.getTimestamp());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(Roomstatus roomstatus) throws SQLException {
		String sql = "UPDATE roomstatuss SET `date`=?, `status`=?, `reason`=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, roomstatus.getDate());
		pstmt.setInt(2, roomstatus.getStatus().ordinal());
		pstmt.setString(3, roomstatus.getReason());
		pstmt.setLong(4, roomstatus.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM roomstatuss WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	protected ArrayList<Roomstatus> getRoomstatusByFields(TreeMap<String, Object> fields, String orderby, int page) {
		String sql = "SELECT * FROM roomstatuss " + this.makeFields(fields, orderby, page);

		try {
			PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
			int i = 1;
			for (String field : fields.keySet()) {
				pstmt.setObject(i++, fields.get(field));
			}
			return executeQuery(pstmt, Roomstatus.class);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}

	}

}
