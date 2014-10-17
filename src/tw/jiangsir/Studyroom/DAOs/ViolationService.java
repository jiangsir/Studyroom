package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import tw.jiangsir.Studyroom.Objects.Booking;
import tw.jiangsir.Studyroom.Objects.Violation;
import tw.jiangsir.Utils.Exceptions.DataException;

public class ViolationService {

	public int insert(Violation violation) throws DataException {
		try {
			return new ViolationDAO().insert(violation);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	public void update(Violation violation) throws DataException {
		try {
			new ViolationDAO().update(violation);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long id) throws DataException {
		try {
			new ViolationDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public Violation getViolationById(long id) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("id", id);
		for (Violation violation : new ViolationDAO().getViolationsByFields(
				fields, "", 0)) {
			return violation;
		}
		return null;
	}

	public ArrayList<Violation> getViolations(int page) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		return new ViolationDAO().getViolationsByFields(fields,
				"timestamp DESC", page);
	}

	/**
	 * 取得某學生當日最近一次的簽到退記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public ArrayList<Violation> getViolationsTodayByStudentid(String studentid) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("date", new Date(System.currentTimeMillis()));
		fields.put("studentid", studentid);
		return new ViolationDAO().getViolationsByFields(fields,
				"timestamp DESC", 0);
	}

	/**
	 * 計算 某個日期的 violations
	 * 
	 * @param date
	 */
	public void builtViolationsByDate(Date date) {
		try {
			new ViolationDAO().deleteByDate(date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Date
				.valueOf(df.format(new Date(System.currentTimeMillis())));

		if (!date.before(today)) {
			return;
		}
		if (!new RoomstatusService().isOpen(date)) {
			return;
		}
		for (Booking booking : new BookingService().getBookingsByDate(date)) {
			if (booking.getAttendance() == null) {
				Violation violation = new Violation();
				violation.setDate(date);
				violation.setStudentid(booking.getStudentid());
				violation.setReason(Violation.REASON.absent);
				violation.setStatus(Violation.STATUS.enable);
				new ViolationService().insert(violation);
			}
		}
	}

	/**
	 * 判斷是否已經達到違規上限。
	 * 
	 * @param studentid
	 * @return
	 */
	public boolean getIsPunishByStudentid(String studentid) {
		ArrayList<Violation> violations = this
				.getViolationsByStudentid(studentid);
		if (violations.size() >= 3) {
			return true;
		}
		return false;
	}

	/**
	 * 進行違規停權
	 */
	public void doPunishment(Date date) {
		// 1. 已經固定劃位的人，要取消兩週內的劃位。
		// 2. 要進行劃位的人，兩周內無法訂位。
		for (Booking booking : new BookingService().getBookingsByDate(date)) {
			if (this.getIsPunishByStudentid(booking.getStudentid())) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				for (int i = 1; i <= 14; i++) {
					calendar.add(Calendar.DATE, 1);
					// 這裡是把 booking.status 設定為 "Punish"
					// new BookingService().doPunishByStudentidDate(booking
					// .getStudentid(), new Date(calendar.getTime()
					// .getTime()));

					// 這裡是直接將 booking 刪除。
					new BookingService().deleteByStudentidDate(booking
							.getStudentid(), new Date(calendar.getTime()
							.getTime()));
				}
				try {
					new ViolationDAO().updatePunished(booking.getStudentid());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取得某一學生所有的違規記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public ArrayList<Violation> getViolationsByStudentid(String studentid) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("studentid", studentid);
		fields.put("status", Violation.STATUS.enable.name());
		return new ViolationDAO().getViolationsByFields(fields,
				"timestamp DESC", 0);
	}

	/**
	 * 取得某學生當日最近一次的簽到退記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public ArrayList<Violation> getViolationsByStudentidDate(String studentid,
			Date date) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("date", date);
		fields.put("studentid", studentid);
		fields.put("status", Violation.STATUS.enable.name());
		return new ViolationDAO().getViolationsByFields(fields,
				"timestamp DESC", 0);
	}

	public void checkViolationsByStudentid(String studentid) {
		ArrayList<Violation> violations = this
				.getViolationsByStudentid(studentid);

		if (violations.size() >= 3) {
			throw new DataException("您已經累計 3 次的違規囉，暫停訂位中。");
		}
	}

	public LinkedHashMap<String, Integer> getStudentidsByCount() {
		return new ViolationDAO().getStudentidsByCount();
	}

}
