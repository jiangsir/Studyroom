package tw.jiangsir.Studyroom.Tasks;

import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Logger;
import tw.jiangsir.Studyroom.DAOs.StudentService;

public class CacheStudentsTask extends TimerTask {
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void run() {
		logger.info("Clear CacheStudents Task Running...."
				+ new Timestamp(System.currentTimeMillis()));
		StudentService.clearCacheStudents();
	}

}
