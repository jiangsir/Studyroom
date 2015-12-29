package tw.jiangsir.Studyroom.Tables;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import tw.jiangsir.Utils.Annotations.Persistent;

public class Roomstatus {
	@Persistent(name = "id")
	private Long id = 0L;
	@Persistent(name = "date")
	private Date date = new Date(new java.util.Date().getTime());

	public enum STATUS {
		open, // 開館
		close;// 閉館
	}

	@Persistent(name = "status")
	private STATUS status = STATUS.close;
	@Persistent(name = "reason")
	private String reason = "";
	@Persistent(name = "timestamp")
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		if (reason == null) {
			return;
		}
		this.reason = reason;
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
	// =========================================================
}
