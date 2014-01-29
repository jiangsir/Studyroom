package tw.jiangsir.Studyroom.TLDs;

import java.util.HashSet;

public class BookingTLD {
	/**
	 * 給 JSTL 呼叫是用在 View 端的
	 * 
	 * @return
	 */
	public static boolean isOccupied(HashSet<Integer> seatids, Integer seatid) {
		return seatids.contains(seatid);
	}

}
