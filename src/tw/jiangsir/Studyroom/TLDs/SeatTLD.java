package tw.jiangsir.Studyroom.TLDs;

import java.util.HashMap;

import tw.jiangsir.Studyroom.Objects.Attendance;
import tw.jiangsir.Studyroom.Servlets.BookUpServlet;
import tw.jiangsir.Utils.DAOs.AttendanceService;
import tw.jiangsir.Utils.Exceptions.AccessException;

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
		return bookupMap.get(seatid);

		// Attendance attendance = new AttendanceService()
		// .getLastAttendanceTodayByStudentid(studentid);
		// String s = "";
		// if (attendance == null) {
		// s = studentid + "\n 尚未簽到";
		// } else {
		// s = studentid + "\n" + attendance.getStatus();
		// }
		// return s;
	}

	public static boolean canBookup(java.sql.Date date) {
		try {
			if (date == null) {
				new BookUpServlet().AccessFilter(new java.sql.Date(System
						.currentTimeMillis()));
			} else {
				new BookUpServlet().AccessFilter(date);
			}
		} catch (AccessException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

}
