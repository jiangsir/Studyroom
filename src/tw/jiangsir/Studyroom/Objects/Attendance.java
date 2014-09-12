package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.sql.Timestamp;
import tw.jiangsir.Utils.Annotations.Persistent;

public class Attendance {
	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "studentid")
	private String studentid = "";
	@Persistent(name = "date")
	private Date date = new Date(new java.util.Date().getTime());;

	public enum STATUS {
		SignIn, // 簽到
		SignOut;// 簽退
	}

	@Persistent(name = "status")
	private STATUS status = STATUS.SignIn;
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public void setStatus(Integer status) {
		this.status = STATUS.values()[status];
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public boolean getIsSignIn() {
		return this.getStatus() == STATUS.SignIn;
	}

	public boolean getIsSignOut() {
		return this.getStatus() == STATUS.SignOut;
	}

}
