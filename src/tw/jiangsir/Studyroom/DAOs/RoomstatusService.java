package tw.jiangsir.Studyroom.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TreeMap;

import tw.jiangsir.Studyroom.Tables.Roomstatus;
import tw.jiangsir.Utils.Exceptions.DataException;

public class RoomstatusService {

	public int insert(Roomstatus roomstatus) throws DataException {
		try {
			return new RoomstatusDAO().insert(roomstatus);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	public void update(Roomstatus roomstatus) throws DataException {
		try {
			new RoomstatusDAO().update(roomstatus);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long id) throws DataException {
		try {
			new RoomstatusDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public Roomstatus getRoomstatusById(long id) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("id", id);
		for (Roomstatus roomstatus : new RoomstatusDAO().getRoomstatusByFields(fields, "", 0)) {
			return roomstatus;
		}
		return null;
	}

	public Roomstatus getRoomstatusByDate(Date date) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("date", date);
		for (Roomstatus roomstatus : new RoomstatusDAO().getRoomstatusByFields(fields, "", 0)) {
			return roomstatus;
		}
		return null;
	}

	public void doChangeStatus(Date date, String reason) {
		if (this.isOpen(date)) {
			this.doClose(date, reason);
		} else {
			this.doOpen(date);
		}
	}

	public void doOpen(Date date) {
		Roomstatus roomstatus = this.getRoomstatusByDate(date);
		if (roomstatus == null) {
			roomstatus = new Roomstatus();
			roomstatus.setDate(date);
			roomstatus.setStatus(Roomstatus.STATUS.open);
			this.insert(roomstatus);
			return;
		} else {
			roomstatus.setStatus(Roomstatus.STATUS.open);
			this.update(roomstatus);
		}
	}

	/**
	 * 設定為『關閉』
	 * 
	 * @param date
	 * @param reason
	 */
	public void doClose(Date date, String reason) {
		Roomstatus roomstatus = this.getRoomstatusByDate(date);
		if (roomstatus == null) {
			roomstatus = new Roomstatus();
			roomstatus.setDate(date);
			roomstatus.setStatus(Roomstatus.STATUS.close);
			roomstatus.setReason(reason);
			this.insert(roomstatus);
			return;
		} else {
			roomstatus.setStatus(Roomstatus.STATUS.close);
			roomstatus.setReason(reason);
			this.update(roomstatus);
		}
	}

	public boolean isOpen(Date date) {
		Roomstatus roomstatus = this.getRoomstatusByDate(date);
		if (roomstatus != null) {
			return roomstatus.getStatus() == Roomstatus.STATUS.open;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal == null || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return false;
		}
		return true;
	}

}
