package tw.jiangsir.Studyroom.TLDs;

import java.util.HashMap;

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

}
