package tw.jiangsir.Studyroom.Tasks;

import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Logger;

public class TestTask extends TimerTask {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void run() {
		logger.info("Test Task Running...." + new Timestamp(System.currentTimeMillis()));
	}

}
