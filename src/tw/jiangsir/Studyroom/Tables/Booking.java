package tw.jiangsir.Studyroom.Tables;

import java.sql.Date;
import java.sql.Timestamp;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Utils.Annotations.Persistent;
import tw.jiangsir.Utils.DAOs.UserService;
import tw.jiangsir.Utils.Objects.User;

public class Booking {
	public enum STATUS {
		Booked, // 已經完成訂位。也就是占用中
		// Punish, // 因為違規被取消訂位狀況。
		Cancel, // 訂位後又取消。未來取消訂位就不直接刪除，就在 status 裏面標記 Cancel 尚未使用。！
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
		if (studentid == null) {
			return;
		}
		this.studentid = studentid.trim();
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

	public boolean getIsBooked() {
		return this.getStatus() == Booking.STATUS.Booked;
	}

	// public boolean getIsSignIn() {
	// Attendance attendance = new AttendanceService()
	// .getLastAttendanceByStudentidDate(studentid, date);
	// return attendance.getStatus() == Attendance.STATUS.SignIn;
	// // return this.getStatus() == Booking.STATUS.SignIn;
	// }
	//
	// public boolean getIsSignOut() {
	// return this.getStatus() == Booking.STATUS.SignOut;
	// }

	/**
	 * 取得這個預約學生最後一個簽到記錄。
	 * 
	 * @return
	 */
	public Attendance getAttendance() {
		Attendance attendance = new AttendanceService()
				.getLastAttendanceByStudentidDate(studentid, date);
		// System.out.println(attendance);
		return attendance;
	}

	public User getUser() {
		return new UserService().getUserById(this.getUserid());
	}

}
