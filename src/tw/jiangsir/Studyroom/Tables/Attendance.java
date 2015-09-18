package tw.jiangsir.Studyroom.Tables;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import tw.jiangsir.Studyroom.DAOs.AttendanceService;
import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Utils.Annotations.Persistent;

public class Attendance {
	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "studentid")
	private String studentid = "";
	@Persistent(name = "date")
	private Date date = new Date(System.currentTimeMillis());;

	public enum STATUS {
		SignIn, // 簽到
		SignOut;// 簽退
	}

	@Persistent(name = "status")
	private STATUS status = STATUS.SignIn;
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

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
		this.setStatus(STATUS.values()[status]);
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

	public int getSeatid() {
		return new BookingService()
				.getBookingByStudentidDate(this.getDate(), this.getStudentid())
				.getSeatid();
	}

	/**
	 * 取得停留時間。
	 * 
	 * @return
	 */
	public int getStaytime() {
		if (this.getIsSignOut()) {
			long staytime = 0L;
			long closestSignin = 0L;
			ArrayList<Attendance> attendances = new AttendanceService()
					.getAttendancesByStudentidDate(this.getStudentid(),
							this.getDate(), "ASC");
			for (Attendance attendance : attendances) {
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
		} else {
			return 0;
		}
	}

}
