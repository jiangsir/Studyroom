package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import tw.jiangsir.Studyroom.Objects.StudentViolations;
import tw.jiangsir.Studyroom.Objects.ViolationQueue;
import tw.jiangsir.Studyroom.Tables.Booking;
import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Utils.Exceptions.DataException;
import tw.jiangsir.Utils.Objects.AppConfig;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Tools.DateTool;

public class ViolationService {
	Logger logger = Logger.getLogger(this.getClass().getName());

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
		return new ViolationDAO().getViolationsByFields(fields, "date DESC",
				page);
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
	 * 建立 某個日期的 violations
	 * 
	 * @param date
	 */
	public void builtViolationsByDate(Date date) {
		try {
			new ViolationDAO().deleteByDate(date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Date today = Date
		// .valueOf(df.format(new Date(System.currentTimeMillis())));

		// if (!date.before(today)) {
		// return;
		// }
		AppConfig appConfig = ApplicationScope.getAppConfig();
		Timestamp signinend = Timestamp.valueOf(date.toString() + " "
				+ appConfig.getSigninend());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out.println("signinend=" + signinend);
		System.out.println("now=" + now);
		if (!signinend.before(now)) {
			return;
		}
		if (!new RoomstatusService().isOpen(date)) {
			return;
		}
		for (Booking booking : new BookingService().getBookingsByDate(date)) {
			System.out.println("appConfig.getWorkingstudentids()="
					+ appConfig.getWorkingstudentids() + " seatid="
					+ booking.getSeatid());
			if (booking.getAttendance() == null
					&& !appConfig.getWorkingstudentids().contains(
							booking.getStudentid())) {
				// 沒有出席記錄， 且不是工讀生。
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
	 * 取得學生的 『違規佇列』
	 * 
	 * @param studentid
	 * @return
	 */
	public ViolationQueue getViolateQueue(String studentid) {
		ArrayList<Violation> enableViolations = this
				.getEnableViolationsByStudentid(studentid);
		ViolationQueue queue = new ViolationQueue(ApplicationScope
				.getAppConfig().getPunishingthreshold());
		for (Violation violation : enableViolations) {
			if (queue.isFull()) {
				if (DateTool.getDayCountBetween(queue.getLast().getDate(),
						violation.getDate()) > ApplicationScope.getAppConfig()
						.getPunishingdays()) { // 已經超過 14 天，就清除舊的違規。
					queue.clear();
					queue.add(violation);
				} else {
					queue.remove();
					queue.add(violation);
				}
			} else {
				queue.add(violation);
			}
		}
		return queue;
	}

	/**
	 * 判斷是否已經達到違規上限。<br>
	 * 20150113 任何時刻只要有 3 個 enalbe 就不能訂位，座位表也不顯示。<br>
	 * 20150123 改成 enable violation 都不清除(只在學期初清除)，用 ViolationQueue(由
	 * PriorityQueue 實作) 來判斷是否有違規。<br>
	 * 
	 * @param studentid
	 * @return
	 */
	public boolean getIsStopBookingByStudentid(String studentid, Date date) {
		ViolationQueue queue = this.getViolateQueue(studentid);
		if (queue.isFull()
				&& DateTool.getDayCountBetween(queue.getLast().getDate(), date) <= ApplicationScope
						.getAppConfig().getPunishingdays()) {
			return true;
		}
		return false;
	}

	/**
	 * 判斷某人是否在停權期間？
	 * 
	 * @param studentid
	 * @return
	 */
	// public boolean getIsSuspending(String studentid, Date date) {
	// try {
	// new BookingService().checkHasBookingRight(studentid, date);
	// return false;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return true;
	// }
	// }

	/**
	 * 進行違規停權，把未來 14 天內該使用者的所有訂位刪除。
	 */
	private void doPunishingByDeleteBooking(Date date) {
		// 1. 已經固定劃位的人，要取消兩週內的劃位。
		// 2. 要進行劃位的人，兩周內無法訂位。
		AppConfig appConfig = ApplicationScope.getAppConfig();
		for (Booking booking : new BookingService().getBookingsByDate(date)) {
			if (this.getIsStopBookingByStudentid(booking.getStudentid(),
					booking.getDate())) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				for (int i = 1; i <= appConfig.getPunishingdays(); i++) {
					calendar.add(Calendar.DATE, 1);

					// 這裡是把 booking.status 設定為 "Punish"
					// new BookingService().doPunishByStudentidDate(booking
					// .getStudentid(), new Date(calendar.getTime()
					// .getTime()));

					// 這裡是直接將 booking 刪除。
					// 20150110 違規後，暫不直接刪除訂位，而是用學生的違規狀態來決定是否可以訂位。
					new BookingService().deleteByStudentidDate(booking
							.getStudentid(), new Date(calendar.getTime()
							.getTime()));
				}
			}
		}
	}

	/**
	 * 取得目前處在 停權中 的 studentid，也就是 enable violation >=3 的人。
	 * 
	 * @return
	 * @deprecated
	 */
	private TreeSet<String> getSuspendingStudentids(Date date) {
		AppConfig appConfig = ApplicationScope.getAppConfig();
		TreeSet<String> studentids = new TreeSet<String>();
		LinkedHashMap<String, Integer> ss = new ViolationDAO()
				.getHashMapOfStudentidCountByEnableViolations();
		for (String studentid : ss.keySet()) {
			if (ss.get(studentid).intValue() >= appConfig
					.getPunishingthreshold()) {
				studentids.add(studentid);
			}
		}
		return studentids;
		// TreeSet<String> studentids = new TreeSet<String>();
		// TreeMap<String, Object> fields = new TreeMap<String, Object>();
		// fields.put("status", Violation.STATUS.punishing.name());
		// for (Violation violation : new ViolationDAO().getViolationsByFields(
		// fields, "date DESC", 0)) {
		// studentids.add(violation.getStudentid());
		// }
		// return studentids;
	}

	/**
	 * 分析某日若有人停權期滿，應該將它恢復。
	 * 
	 * @param date
	 * @deprecated
	 */
	private void doFinishPunishByDate(Date date) {
		for (String studentid : this.getSuspendingStudentids(date)) {
			try {
				new ViolationDAO().setEnable2PunishedByStudentid(studentid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取得某一學生 enable 的違規記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public ArrayList<Violation> getEnableViolationsByStudentid(String studentid) {
		// TreeMap<String, Object> fields = new TreeMap<String, Object>();
		// fields.put("studentid", studentid);
		// fields.put("status", Violation.STATUS.enable.name());
		// return new ViolationDAO().getViolationsByFields(fields, "date ASC",
		// 0);
		try {
			return new ViolationDAO().getEnabledViolations(studentid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * 取得某一學生“停權”的 violations
	// *
	// * @param studentid
	// * @return
	// */
	// public ArrayList<Violation> getPunishingViolationsByStudentid(
	// String studentid) {
	// TreeMap<String, Object> fields = new TreeMap<String, Object>();
	// fields.put("studentid", studentid);
	// fields.put("status", Violation.STATUS.punishing.name());
	// return new ViolationDAO().getViolationsByFields(fields, "date DESC", 0);
	// }

	/**
	 * 取得某一學生『最後一筆』違規記錄。可用來計算停權開始的日期。
	 * 
	 * @param studentid
	 * @return
	 */
	private Violation getLastEnableViolationByStudentid(String studentid) {

		// if (this.getIsReachPunishingByStudentid(studentid)) {
		// for (Violation violation : this
		// .getEnableViolationsByStudentid(studentid)) {
		// return violation;
		// }
		// }
		return null;
	}

	/**
	 * 取得某學生某日有效的違規記錄。
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
		return new ViolationDAO().getViolationsByFields(fields, "date DESC", 0);
	}

	/**
	 * 取得所有違規學生清單。
	 * 
	 * @return
	 */
	public ArrayList<StudentViolations> getStudentidViolations() {
		ArrayList<StudentViolations> violations = new ArrayList<StudentViolations>();
		try {
			for (String studentid : new ViolationDAO()
					.getStudentidsWithEnableViolation()) {
				StudentViolations studentViolations = new StudentViolations();
				studentViolations.setStudentid(studentid);
				studentViolations.setViolations(this
						.getEnableViolationsByStudentid(studentid));
				studentViolations.setViolationQueue(new ViolationService()
						.getViolateQueue(studentid));
				violations.add(studentViolations);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(violations);
		return violations;
	}

	// /**
	// * 取得所有正在“停權” punishing 的學生名單以及 violations
	// *
	// * @return
	// */
	// public LinkedHashMap<String, ArrayList<Violation>>
	// getPunishingStudentidsViolations() {
	// LinkedHashMap<String, ArrayList<Violation>> violationMap = new
	// LinkedHashMap<String, ArrayList<Violation>>();
	// for (String studentid : this.getPunishingStudentids()) {
	// violationMap.put(studentid,
	// this.getPunishingViolationsByStudentid(studentid));
	// }
	// return violationMap;
	// }

	/**
	 * 判斷某人某天是否有 Booking 權限。
	 * 
	 * @param studentid
	 * @param date
	 */
	public boolean checkBookingRight(String studentid, Date date)
			throws DataException {

		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Date today = Date
		// .valueOf(df.format(new Date(System.currentTimeMillis())));
		AppConfig appConfig = ApplicationScope.getAppConfig();

		ArrayList<Violation> violations = new ViolationService()
				.getEnableViolationsByStudentid(studentid);
		if (violations.size() < appConfig.getPunishingthreshold()) {
			return true;
		}
		Violation lastViolation = violations.get(0);
		// Violation violation = new ViolationService()
		// .getLastEnableViolationByStudentid(studentid);
		// if (violation == null) {
		// return;
		// }
		Calendar suspendstart = Calendar.getInstance();
		suspendstart.setTime(lastViolation.getDate());
		suspendstart.add(Calendar.DATE, 1);
		if (DateTool.getDayCountBetween(
				new Date(suspendstart.getTimeInMillis()), date) < appConfig
				.getPunishingdays()) {
			Calendar suspendingend = Calendar.getInstance();
			suspendingend.setTime(lastViolation.getDate());
			suspendingend.add(Calendar.DATE, appConfig.getPunishingdays());
			throw new DataException("您(" + studentid + ")目前仍在停權中。您的停權時間為 "
					+ new Date(suspendstart.getTimeInMillis()) + " 到"
					+ new Date(suspendingend.getTimeInMillis()));
		}
		return true;
	}

}
