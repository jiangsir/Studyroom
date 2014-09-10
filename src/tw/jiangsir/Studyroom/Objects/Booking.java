package tw.jiangsir.Studyroom.Objects;

import java.sql.Date;
import java.sql.Timestamp;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Booking {
	public enum STATUS {
		Booked, // 已經完成訂位。也就是占用中
		SignIn, // 簽到
		SignOut, // 簽退
		Cancel, // 訂位後又取消。未來取消訂位就不直接刪除，就在 status 裏面標記 Cancel
		Release; // 使用後，把位置釋放出來。尚未開放使用。
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
	private STATUS status = STATUS.Booked;
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
