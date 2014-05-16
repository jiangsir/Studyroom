package tw.jiangsir.Utils.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Tools.DateTool;

public class BookingService {

	public int insert(Booking booking) throws DataException {
		BookingDAO bookingDao = new BookingDAO();
		// 已經對資料庫的 studentid, date 兩個欄位設定為 UNIQUE 因此不需要在這邊作判斷。

		try {
			Booking otherbooking = bookingDao.getBookingByStudentidDate(
					booking.getStudentid(), booking.getDate());
			if (otherbooking != null) {
				throw new DataException("您在 "
						+ (DateTool.isSameday(otherbooking.getDate(),
								new java.util.Date()) ? "今天"
								: otherbooking.getDate()) + " 已經訂過 "
						+ otherbooking.getSeatid() + "號位置了，不能重複訂位。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
		try {
			return bookingDao.insert(booking);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
				throw new DataException("您在 " + booking.getDate()
						+ " 已經訂過位置了，不能重複訂位。");
			}
			throw new DataException(e);
		}
	}

	public void update(Booking booking) throws DataException {
		try {
			new BookingDAO().update(booking);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long bookingid) throws DataException {
		try {
			new BookingDAO().delete(bookingid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public ArrayList<Booking> getBookingsToday() {
		try {
			return new BookingDAO().getBookingsByDate(new Date(
					new java.util.Date().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public HashMap<Integer, String> getBookupMapToday() {
		try {
			return new BookingDAO().getBookupMapByDate(new Date(
					new java.util.Date().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public HashMap<Integer, String> getBookupMapByDate(java.sql.Date date) {
		try {
			return new BookingDAO().getBookupMapByDate(date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public Booking getBookingByStudentidToday(String studentid) {
		try {
			return new BookingDAO().getBookingByStudentidDate(studentid,
					new Date(new java.util.Date().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	/**
	 * 一次新增多筆預約
	 * 
	 * @param userid
	 * @param studentid
	 * @param seatid
	 * @param begindate
	 * @param enddate
	 */
	public void insertBookings(long userid, String studentid, int seatid,
			Date begindate, Date enddate) {
		Calendar date = Calendar.getInstance();
		date.setTime(begindate);
		for (int day = 0; day <= DateTool.getDaysBetween(begindate, enddate); day++) {
			Booking booking = new Booking();
			booking.setUserid(userid);
			booking.setStudentid(studentid);
			booking.setSeatid(seatid);
			booking.setDate(new java.sql.Date(date.getTimeInMillis()));
			new BookingService().insert(booking);
			date.add(Calendar.DATE, 1);
		}
	}

}
