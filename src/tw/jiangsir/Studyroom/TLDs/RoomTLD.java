package tw.jiangsir.Studyroom.TLDs;

import java.sql.Date;
import tw.jiangsir.Utils.DAOs.RoomstatusService;

public class RoomTLD {

	public static boolean isOpen(java.util.Date date) {
		if (date == null) {
			return false;
		}
		return new RoomstatusService().isOpen(new Date(date.getTime()));
	}
}
