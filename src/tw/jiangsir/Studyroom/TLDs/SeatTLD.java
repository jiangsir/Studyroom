package tw.jiangsir.Studyroom.TLDs;

import java.util.HashMap;

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Servlets.BookUpServlet;
import tw.jiangsir.Utils.DAOs.AttendanceService;

public class SeatTLD {
	/**
	 * 給 JSTL 呼叫是用在 View 端的
	 * 
	 * @return
	 */
	public static boolean isOccupied(HashMap<Integer, String> bookupMap,
			Integer seatid) {
		return bookupMap.containsKey(seatid);
	}

	public static String studentid(HashMap<Integer, String> bookupMap,
			Integer seatid) {
		String studentid = bookupMap.get(seatid);
		Attendance attendance = new AttendanceService()
				.getLastAttendanceTodayByStudentid(studentid);
		String s = "";
		if (attendance == null) {
			s = studentid + "\n 尚未簽到";
		} else {
			s = studentid + "\n" + attendance.getStatus();
		}
		return s;
	}

	public static boolean canBookup(java.sql.Date date) {
		try {
			if (date == null) {
				return new BookUpServlet().canBookup(new java.sql.Date(System
						.currentTimeMillis()));
			} else {
				return new BookUpServlet().canBookup(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
