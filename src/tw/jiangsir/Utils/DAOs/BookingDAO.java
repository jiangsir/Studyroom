/**
 * idv.jiangsir.DAOs - UserDAO.java
 * 2008/4/29 下午 05:46:51
 * jiangsir
 */
package tw.jiangsir.Utils.DAOs;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import tw.jiangsir.Utils.Objects.Booking;

/**
 * @author jiangsir
 * 
 */
public class BookingDAO extends SuperDAO<Booking> {

	@Override
	protected synchronized int insert(Booking booking) throws SQLException {
		String sql = "INSERT INTO bookings(userid, studentid, seatid, `date`, `timestamp`) VALUES (?,?,?,?,?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setLong(1, booking.getUserid());
		pstmt.setString(2, booking.getStudentid());
		pstmt.setInt(3, booking.getSeatid());
		pstmt.setDate(4, booking.getDate());
		pstmt.setTimestamp(5, booking.getTimestamp());
		return this.executeInsert(pstmt);
	}

	protected synchronized int update(Booking booking) throws SQLException {
		String sql = "UPDATE bookings SET userid=?, studentid=?, seatid=?, `date`=? WHERE id=?";
		int result = -1;
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, booking.getUserid());
		pstmt.setString(2, booking.getStudentid());
		pstmt.setInt(3, booking.getSeatid());
		pstmt.setDate(4, booking.getDate());
		pstmt.setLong(5, booking.getId());
		result = this.executeUpdate(pstmt);
		pstmt.close();
		return result;
	}

	protected Booking getBookingById(long bookingid) throws SQLException {
		String sql = "SELECT * FROM bookings WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, bookingid);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			return booking;
		}
		return null;
	}

	protected Booking getBookingByStudentidDate(String studentid, Date date)
			throws SQLException {
		String sql = "SELECT * FROM bookings WHERE studentid=? AND `date`=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		pstmt.setDate(2, date);
		for (Booking booking : this.executeQuery(pstmt, Booking.class)) {
			return booking;
		}
		return null;
	}

	protected ArrayList<Booking> getBookingsByStudentid(String studentid)
			throws SQLException {
		String sql = "SELECT * FROM bookings WHERE studentid=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setString(1, studentid);
		return this.executeQuery(pstmt, Booking.class);
	}

	protected ArrayList<Booking> getBookingsByDate(Date date)
			throws SQLException {
		String sql = "SELECT * FROM bookings WHERE date=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setDate(1, date);
		return this.executeQuery(pstmt, Booking.class);
	}

	protected HashMap<Integer, String> getBookupMapByDate(Date date)
			throws SQLException {
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
		PreparedStatement pstmt;
		pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

}
