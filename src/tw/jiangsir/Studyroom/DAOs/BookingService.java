package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

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
				throw new DataException("您("
						+ booking.getStudentid()
						+ ")在 "
						+ (DateTool.isSameday(otherbooking.getDate(),
								new java.util.Date()) ? "今天"
								: otherbooking.getDate()) + " 已經訂過 "
						+ otherbooking.getSeatid() + "號位置了，不能重複訂位。");
			}

			if (new BookingDAO().getBookingsBySeatidDate(booking.getSeatid(),
					booking.getDate()).size() > 0) {
				throw new DataException(""
						+ (DateTool.isSameday(booking.getDate(),
								new java.util.Date()) ? "今天"
								: booking.getDate()) + " "
						+ booking.getSeatid() + "號位置已經被預定了，您("
						+ booking.getStudentid() + ") 無法再訂這個位置了。");
			}
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

	public void delete(int seatid, Date date) {
		try {
			new BookingDAO().delete(seatid, date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void deleteByStudentidDate(String studentid, Date date) {
		try {
			new BookingDAO().delete(studentid, date);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void doPunishByStudentidDate(String studentid, Date date) {
		try {
			new BookingDAO().updatePunishByStudentidDate(studentid, date);
		} catch (SQLException e) {
			e.printStackTrace();
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

	public ArrayList<Booking> getBookingsByDate(Date date) {
		try {
			return new BookingDAO().getBookingsByDate(date);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Booking>();
		}
	}

	/**
	 * 回傳現在的 bookings 狀態。因為在 JSTL/EL 裏面一律以 long 來處理整數，因此這裡的 HashMap 就必須以 Long 作為
	 * key<br>
	 * 才能以 ${hashBookings[seatid]} 這樣的形式存取。
	 * 
	 * @return
	 */
	public HashMap<String, Booking> getHashBookings(Date date) {
		HashMap<String, Booking> hashBookings = new HashMap<String, Booking>();
		try {
			for (Booking booking : new BookingDAO().getBookingsByDate(date)) {
				hashBookings.put(String.valueOf(booking.getSeatid()), booking);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hashBookings;
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

	/**
	 * 取得當前某學生的訂位記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public Booking getBookingTodayByStudentid(String studentid) {
		try {
			return new BookingDAO().getBookingByStudentidDate(studentid,
					new Date(new java.util.Date().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public Booking getBookingByStudentidDate(Date date, String studentid) {
		try {
			return new BookingDAO().getBookingByStudentidDate(studentid, date);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	// /**
	// * 一次新增多筆預約
	// *
	// * @param userid
	// * @param studentid
	// * @param seatid
	// * @param begindate
	// * @param enddate
	// */
	// public void insertBookings(long userid, String studentid, int seatid,
	// Date begindate, Date enddate) {
	// Calendar date = Calendar.getInstance();
	// date.setTime(begindate);
	// for (int day = 0; day <= DateTool
	// .getDayCountBetween(begindate, enddate); day++) {
	// Booking booking = new Booking();
	// booking.setUserid(userid);
	// booking.setStudentid(studentid);
	// booking.setSeatid(seatid);
	// booking.setDate(new java.sql.Date(date.getTimeInMillis()));
	// this.insert(booking);
	// date.add(Calendar.DATE, 1);
	// }
	// }

	/**
	 * 指定 userid, studentid, seatid, 以及日期以便增加一筆訂位
	 * 
	 * @param userid
	 * @param studentid
	 * @param seatid
	 * @param begindate
	 * @param enddate
	 */
	public void insertBooking(long userid, String studentid, int seatid,
			Date date) {
		Booking booking = new Booking();
		booking.setUserid(userid);
		booking.setStudentid(studentid);
		booking.setSeatid(seatid);
		booking.setDate(new java.sql.Date(date.getTime()));
		this.insert(booking);
	}

}
