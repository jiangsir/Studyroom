package tw.jiangsir.Studyroom.TLDs;

import java.util.HashMap;

import tw.jiangsir.Studyroom.Servlets.BookUpServlet;

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
	}

	public static boolean canBookup(java.sql.Date date) {
		try {
			return new BookUpServlet().canBookup(date);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
