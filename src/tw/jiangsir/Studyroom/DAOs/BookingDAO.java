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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Utils.DAOs.SuperDAO;

/**
 * @author jiangsir
 * 
 */
public class BookingDAO extends SuperDAO<Booking> {

	@Override
	protected synchronized int insert(Booking booking) throws SQLException {
		String sql = "INSERT INTO bookings(userid, studentid, seatid, `date`, `status`, `timestamp`) VALUES (?,?,?,?,?,?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, booking.getUserid());
		pstmt.setString(2, booking.getStudentid());
		pstmt.setInt(3, booking.getSeatid());
		pstmt.setDate(4, booking.getDate());
		pstmt.setString(5, booking.getStatus().toString());
		pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(Booking booking) throws SQLException {
		String sql = "UPDATE bookings SET userid=?, studentid=?, seatid=?, `date`=?, `status`=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, booking.getUserid());
		pstmt.setString(2, booking.getStudentid());
		pstmt.setInt(3, booking.getSeatid());
		pstmt.setDate(4, booking.getDate());
		pstmt.setString(5, booking.getStatus().toString());
		pstmt.setLong(6, booking.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	// protected synchronized int updatePunishByStudentidDate(String studentid,
	// Date date) throws SQLException {
	// String sql =
	// "UPDATE bookings SET `status`=? WHERE studentid=? AND `date`=?";
	// int result = -1;
	// PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
	// pstmt.setString(1, Booking.STATUS.Punish.toString());
	// pstmt.setString(2, studentid);
	// pstmt.setDate(3, date);
	// result = this.executeUpdate(pstmt);
	// pstmt.close();
	// return result;
	// }

	protected Booking getBookingById(long bookingid) throws SQLException {
		String sql = "SELECT * FROM bookings WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, bookingid);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			return booking;
		}
		return null;
	}

	/**
	 * 用 studentid 及 date 獲取劃位紀錄。 但由於 OverBooking 現象，若 studentid AND date
	 * 當天的座位已經被 OverBooking, 應該取得最後一個 booking
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected Booking getAvailableBookingByStudentid_Date(String studentid, Date date) throws SQLException {
		// SELECT * FROM (SELECT * FROM bookings WHERE seatid=(SELECT seatid
		// FROM bookings WHERE studentid='410224' AND date='2015-12-02') AND
		// date='2015-12-02') as temp GROUP BY seatid,date
		// 找到該生所劃的 seatid 再判斷是否被 overBooking
		String sql = "SELECT * FROM (SELECT * FROM bookings WHERE seatid=(SELECT seatid FROM bookings WHERE studentid=? AND date=?) AND date=?) as temp GROUP BY seatid,date";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		pstmt.setDate(2, date);
		pstmt.setDate(3, date);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			if (booking.getStudentid().equals(studentid)) {
				return booking;
			}
		}
		return null;
	}

	/**
	 * @param studentid
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	protected Booking getBookingByStudentidDate(String studentid, Date date) throws SQLException {
		// FIXME 考慮一種情況：某固定劃位 studentid 由於違規位置被佔。但申訴後解除，要再度劃當天的位置，這種情況這裡就會變成 2
		// 筆。若位置被佔，回報 null , 但這個 sql 還是會回報這個固定劃位資料。
		String sql = "SELECT * FROM bookings WHERE studentid=? AND `date`=? ORDER BY id DESC;";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		pstmt.setDate(2, date);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			return booking;
		}
		return null;
	}

	protected ArrayList<Booking> getBookingsByStudentid(String studentid) throws SQLException {
		String sql = "SELECT * FROM (SELECT * FROM bookings WHERE studentid=? ORDER BY id DESC) as temp GROUP BY seatid,date";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		return this.executeQuery(pstmt, Booking.class);
	}

	/**
	 * 每天每個座位取出最後一筆 booking
	 * 
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	protected ArrayList<Booking> getBookingsByDate(Date date) throws SQLException {
		// 由於允許 overBooking 的存在。因此這裡必須改成取得最後一個 booking 的資料，要用 GROUP BY 處理
		// SELECT * FROM (SELECT * FROM bookings WHERE date='2015-02-03' ORDER
		// BY id DESC) as temp
		// GROUP BY seatid,date
		String sql = "SELECT * FROM (SELECT * FROM bookings WHERE date=? ORDER BY id DESC) as temp "
				+ "GROUP BY seatid,date";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, date);
		return this.executeQuery(pstmt, Booking.class);
	}

	/**
	 * 取得某日某座位的 booking, 因為開放 OverBooking 的原因，因此要用 GROUP BY 取得最後訂位者。
	 * 
	 * @param seatid
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	protected Booking getBookingsBySeatidDate(int seatid, Date date) throws SQLException {
		String sql = "SELECT * FROM (SELECT * FROM bookings WHERE seatid=? AND date=? AND `status`=? "
				+ "ORDER BY id DESC) as temp GROUP BY seatid,date";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setInt(1, seatid);
		pstmt.setDate(2, date);
		pstmt.setString(3, Booking.STATUS.Booked.name());
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			return booking;
		}
		return null;
	}

	protected HashMap<Integer, String> getBookupMapByDate(Date date) throws SQLException {
		HashMap<Integer, String> bookupmap = new HashMap<Integer, String>();
		String sql = "SELECT seatid, studentid FROM bookings WHERE date=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, date);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			bookupmap.put(booking.getSeatid(), booking.getStudentid());
		}
		return bookupmap;
	}

	@Override
	protected boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM bookings WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	protected boolean delete(int seatid, Date date) throws SQLException {
		// DELETE FROM bookings WHERE id=(SELECT id FROM (SELECT * FROM bookings
		// WHERE seatid=18 AND date='2015-10-19' ORDER BY id DESC) as temp GROUP
		// BY seatid,date);
		String sql2 = "DELETE FROM bookings WHERE id=(SELECT id FROM (SELECT * FROM bookings WHERE seatid=? AND date=? ORDER BY id DESC) as temp GROUP BY seatid,date);";
		// 刪掉 OverBooking! 的第一筆。不應全部刪掉。
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql2);
		pstmt.setInt(1, seatid);
		pstmt.setDate(2, date);
		return this.executeDelete(pstmt);
	}

	protected boolean delete(String studentid, Date date) throws SQLException {
		String sql = "DELETE FROM bookings WHERE studentid=? AND date=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		pstmt.setDate(2, date);
		return this.executeDelete(pstmt);
	}

}
