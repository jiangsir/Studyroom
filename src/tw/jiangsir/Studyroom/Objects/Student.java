package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.util.ArrayList;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Studyroom.DAOs.StudentService;
import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Studyroom.Tables.Attendance;
import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Studyroom.Tables.Attendance.STATUS;
import tw.jiangsir.Utils.DAOs.SuperDAO.ORDER;
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
		this.setViolations(violationService
				.getEnableViolationsByStudentid(studentid, date));
		this.setViolationQueue();
		this.setAttendances(new AttendanceService()
				.getAttendancesByStudentidDate(studentid, date, ORDER.ASC));

		if (date.toString()
				.equals(new Date(System.currentTimeMillis()).toString())) {
			StudentService.putCacheStudent(this);
			System.out.println(
					"todayStudents=" + StudentService.getCacheStudents().size()
							+ ", studentid=" + studentid + ", date=" + date);
		}
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
		if (violationQueue.isFull() && (DateTool.getDayCountBetween(
				violationQueue.getLast().getDate(), date) < 0
				|| DateTool.getDayCountBetween(
						violationQueue.getLast().getDate(),
						date) > ApplicationScope.getAppConfig()
								.getPunishingdays())) {
			violationQueue.clear();
		}
		return violationQueue;
	}

	public void setViolationQueue() {
		ArrayList<Violation> enableViolations = this.getViolations();
		ViolationQueue violationQueue = new ViolationQueue(
				ApplicationScope.getAppConfig().getPunishingthreshold());
		for (Violation violation : enableViolations) {
			if (violationQueue.isFull()) {
				if (DateTool.getDayCountBetween(
						violationQueue.getLast().getDate(),
						violation.getDate()) > ApplicationScope.getAppConfig()
								.getPunishingdays()) {
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
	 * 取得這個預約學生最後一個簽到記錄。
	 * 
	 * @return
	 */
	public Attendance getLastAttendance() {
		ArrayList<Attendance> attendances = this.getAttendances();
		if (attendances.size() == 0) {
			return null;
		} else if (attendances.size() == 1) {
			return attendances.get(0);
		} else {
			int lastindex = attendances.size() - 1;
			if (attendances.get(0).getTimestamp()
					.after(attendances.get(lastindex).getTimestamp())) {
				return attendances.get(0);
			} else {
				return attendances.get(lastindex);
			}
		}
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
	 * 取得停留時間(f分鐘)。
	 * 
	 * @return
	 */
	private int getStayMin() {
		long staytime = 0L;
		long closestSignin = 0L;

		// ArrayList<Attendance> attendances = new AttendanceService()
		// .getAttendancesByStudentidDate(this.getStudentid(),
		// this.getDate(), "ASC");
		for (Attendance attendance : this.getAttendances()) {
			if (attendance.getIsSignIn()) {
				closestSignin = attendance.getTimestamp().getTime();
			}
			if (attendance.getIsSignOut()) {
				staytime += (attendance.getTimestamp().getTime()
						- closestSignin);
				closestSignin = 0;
			}
		}
		return (int) (staytime / 1000L / 60L);
	}

	/**
	 * 入場時間，文字化
	 * 
	 * @return
	 */
	public String getStaytime() {
		int min = this.getStayMin();
		return (min / 60 > 0 ? min / 60 + " 小時" : "") + min % 60 + " 分鐘";
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
				&& DateTool.getDayCountBetween(
						violationQueue.getLast().getDate(), date) > 0
				&& DateTool.getDayCountBetween(
						violationQueue.getLast().getDate(),
						date) <= ApplicationScope.getAppConfig()
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
			if (!this.getViolationQueue().isEmpty()
					&& this.getViolationQueue().getLast().getDate().before(
							o.getViolationQueue().getLast().getDate())) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public String toString() {
		return this.getStudentid() + ":" + this.getDate();
	}

}
