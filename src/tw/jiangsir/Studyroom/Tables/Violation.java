package tw.jiangsir.Studyroom.Tables;

import java.sql.Date;
import java.sql.Timestamp;
import tw.jiangsir.Utils.Annotations.Persistent;

public class Violation {
	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "date")
	private Date date = new Date(System.currentTimeMillis());
	@Persistent(name = "studentid")
	private String studentid = "";

	public enum REASON {
		abuse("由他人代刷，或用手機刷卡。"), // 用手機、影印本欺騙條碼機
		absent("缺席"); // 缺席
		private final String value;

		private REASON(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	@Persistent(name = "reason")
	private REASON reason = REASON.absent;

	public enum STATUS {
		//punished, // 已經處罰完畢。也就是恢復正常使用。
		// punishing, // 正在停權中。也就是取消未來兩週的訂位。
		// outdated, // 設定為過期，也就是未來可能出現還新學期，就要全部歸零的狀況。
		cancel, // 手動取消該違規。
		enable, // 有效的違規
		disable;// 每學期進行一次，將違規取消。
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
		if (comment == null) {
			return;
		}
		this.comment = comment;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
