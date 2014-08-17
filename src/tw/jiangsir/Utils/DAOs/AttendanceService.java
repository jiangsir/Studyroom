package tw.jiangsir.Utils.DAOs;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Utils.Exceptions.DataException;

public class AttendanceService {

	public int insert(Attendance attendance) throws DataException {
		try {
			return new AttendanceDAO().insert(attendance);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	public void update(Attendance attendance) throws DataException {
		try {
			new AttendanceDAO().update(attendance);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long id) throws DataException {
		try {
			new AttendanceDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public Attendance getAttendanceById(long id) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("id", id);
		for (Attendance attendance : new AttendanceDAO().getAttendanceByFields(
				fields, "", 0)) {
			return attendance;
		}
		return null;
	}

	public ArrayList<Attendance> getAttendances() {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		return new AttendanceDAO().getAttendanceByFields(fields,
				"timestamp DESC", 0);
	}

	/**
	 * 取得某學生當日最近一次的簽到退記錄。
	 * 
	 * @param studentid
	 * @return
	 */
	public Attendance getLastAttendanceTodayByStudentid(String studentid) {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		fields.put("studentid", studentid);
		fields.put("date", new Date(new java.util.Date().getTime()));
		for (Attendance attendance : new AttendanceDAO().getAttendanceByFields(
				fields, "timestamp DESC", 0)) {
			return attendance;
		}
		return null;
	}

	public void doSignIn(String studentid) {
		Attendance attendance = new Attendance();
		attendance.setStudentid(studentid);
		attendance.setStatus(Attendance.STATUS.SignIn);
		new AttendanceService().insert(attendance);
	}

	public void doSignOut(String studentid) {
		Attendance attendance = new Attendance();
		attendance.setStudentid(studentid);
		attendance.setStatus(Attendance.STATUS.SignOut);
		new AttendanceService().insert(attendance);
	}

}
