package tw.jiangsir.Studyroom.Tasks;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Logger;
import tw.jiangsir.Studyroom.DAOs.ViolationService;

public class ViolationTask extends TimerTask {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void run() {
		logger.info("Violation Task Running...."
				+ new Timestamp(System.currentTimeMillis()));
		new ViolationService().rebuiltViolationsByDate(new Date(System
				.currentTimeMillis()));
		// new ViolationService().doPunishingByDeleteBooking(new Date(System
		// .currentTimeMillis()));
		// new ViolationService().doFinishPunishByDate(new
		// Date(System.currentTimeMillis()));
	}

}
