package tw.jiangsir.Utils.Objects;

import java.sql.Date;
import java.sql.Timestamp;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Booking {
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
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
