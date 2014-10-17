package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.sql.Timestamp;
import tw.jiangsir.Utils.Annotations.Persistent;

public class Violation {
	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "date")
	private Date date = new Date(new java.util.Date().getTime());;
	@Persistent(name = "studentid")
	private String studentid = "";

	public enum REASON {
		abuse, // 濫用、早退、或未簽退
		absent; // 缺席
	}

	@Persistent(name = "reason")
	private REASON reason = REASON.absent;

	public enum STATUS {
		punished, // 已經取消未來兩週的訂位。
		outdated, // 設定為過期，也就是未來可能出現還新學期，就要全部歸零的狀況。
		disable, // 手動取消該違規。
		enable;// 有效的違規
	}

	@Persistent(name = "status")
	private STATUS status = STATUS.enable;
	@Persistent(name = "comment")
	private String comment = "";

	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public REASON getReason() {
		return reason;
	}

	public void setReason(REASON reason) {
		this.reason = reason;
	}

	public void setReason(String reason) {
		if (reason == null) {
			return;
		}
		this.setReason(REASON.valueOf(reason));
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.setStatus(STATUS.valueOf(status));
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
