package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.sql.Timestamp;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Booking {
	public enum STATUS {
		occupied, // 訂位。也就是占用中
		cancel, // 訂位後又取消。
		release; // 使用後，把位置釋放出來。
	}

	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "userid")
	private Long userid = 0L;
	@Persistent(name = "studentid")
	private String studentid = "";
	@Persistent(name = "seatid")
	private Integer seatid = 0;
	@Persistent(name = "date")
	private Date date = new Date(new java.util.Date().getTime());
	@Persistent(name = "status")
	private STATUS status = STATUS.occupied;
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public int getSeatid() {
		return seatid;
	}

	public void setSeatid(Integer seatid) {
		this.seatid = seatid;
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

	public void setStatus(String status) {
		this.setStatus(STATUS.valueOf(status));
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
