package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.util.ArrayList;

import tw.jiangsir.Studyroom.DAOs.ViolationService;
import tw.jiangsir.Studyroom.Tables.Violation;
import tw.jiangsir.Utils.Tools.FixedSizeQueue;

public class StudentViolations implements Comparable<StudentViolations> {
	private String studentid = "";
	private ArrayList<Violation> violations;
	private ViolationQueue violationQueue;

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
		return violationQueue;
	}

	public void setViolationQueue(ViolationQueue violationQueue) {
		this.violationQueue = violationQueue;
	}

	// =========================================================================
	public boolean getIsStopBooking() {
		return new ViolationService().getIsStopBookingByStudentid(studentid,
				new Date(System.currentTimeMillis()));
	}

	/**
	 * 物件排序， ORDER BY QueueSize DESC, LastDate DESC
	 */
	@Override
	public int compareTo(StudentViolations o) {
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
