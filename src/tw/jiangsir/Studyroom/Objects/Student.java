package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.util.ArrayList;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Studyroom.Tables.Attendance;
import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Studyroom.Tables.Attendance.STATUS;
import tw.jiangsir.Utils.Scopes.ApplicationScope;
import tw.jiangsir.Utils.Tools.DateTool;

public class Student implements Comparable<Student> {
	private String studentid = "";
	private Date date = new Date(System.currentTimeMillis());
	private ArrayList<Violation> violations;
	private ViolationQueue violationQueue;
	private ArrayList<Attendance> attendances;

	public Student(String studentid, Date date) {
		this.date = date;
		ViolationService violationService = new ViolationService();
		this.setStudentid(studentid);
		setViolations(violationService.getEnableViolationsByStudentid(
				studentid, date));
		setViolationQueue();
		this.setAttendances(new AttendanceService()
				.getAttendancesByStudentidDate(studentid, date));
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public ArrayList<Violation> getViolations() {
		return violations;
	}

	public void setViolations(ArrayList<Violation> violations) {
		this.violations = violations;
	}

	public ViolationQueue getViolationQueue() {
		if (violationQueue.isFull() && !this.getIsStopBooking()) {
			violationQueue.clear();
		}
		return violationQueue;
	}

	public void setViolationQueue() {
		ArrayList<Violation> enableViolations = this.getViolations();
		ViolationQueue violationQueue = new ViolationQueue(ApplicationScope
				.getAppConfig().getPunishingthreshold());
		for (Violation violation : enableViolations) {
			if (violationQueue.isFull()) {
				if (DateTool.getDayCountBetween(violationQueue.getLast()
						.getDate(), violation.getDate()) > ApplicationScope
						.getAppConfig().getPunishingdays()) {
					// 已經超過 14 天，就清除舊的違規。
					violationQueue.clear();
					violationQueue.add(violation);
				} else {
					violationQueue.remove();
					violationQueue.add(violation);
				}
			} else {
				violationQueue.add(violation);
			}
		}
		this.violationQueue = violationQueue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(ArrayList<Attendance> attendances) {
		this.attendances = attendances;
	}

	/**
	 * =========================================================================
	 */

	/**
	 * 取得該學生當天入場時間。
	 * 
	 * @return
	 */
	public long getMs() {
		long ms = 0L;
		long prev_SignOut = 0;

		for (Attendance attendance : attendances) {
			if (attendance.getStatus() == STATUS.SignOut) {
				prev_SignOut = attendance.getTimestamp().getTime();
			} else if (attendance.getStatus() == STATUS.SignIn
					&& prev_SignOut > 0) {
				ms += prev_SignOut - attendance.getTimestamp().getTime();
			}
		}
		return ms;
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
	public boolean getIsStopBooking() {
		// System.out.println("last date=" + violationQueue.getLast().getDate()
		// + ", thisdate=" + date);
		if (violationQueue.isEmpty()) {
			return false;
		}
		if (violationQueue.isFull()
				&& DateTool.getDayCountBetween(violationQueue.getLast()
						.getDate(), date) > 0
				&& DateTool.getDayCountBetween(violationQueue.getLast()
						.getDate(), date) <= ApplicationScope.getAppConfig()
						.getPunishingdays()) {
			return true;
		}
		return false;
	}

	/**
	 * 物件排序， ORDER BY QueueSize DESC, LastDate DESC
	 */
	@Override
	public int compareTo(Student o) {
		if (this.getViolationQueue().size() < o.getViolationQueue().size()) {
			return 1;
		} else if (this.getViolationQueue().size() > o.getViolationQueue()
				.size()) {
			return -1;
		} else {
			if (this.getViolationQueue().getLast().getDate()
					.before(o.getViolationQueue().getLast().getDate())) {
				return 1;
			} else {
				return -1;
			}
		}

	}
}
