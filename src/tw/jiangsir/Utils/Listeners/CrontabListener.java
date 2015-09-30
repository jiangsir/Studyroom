package tw.jiangsir.Utils.Listeners;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import tw.jiangsir.Studyroom.Tasks.CacheStudentsTask;
import tw.jiangsir.Studyroom.Tasks.ViolationTask;

/**
 * Application Lifecycle Listener implementation class MyRequestListener
 * 
 */
@WebListener
public class CrontabListener implements ServletContextListener {
	Logger logger = Logger.getLogger(this.getClass().getName());
	Timer timer = new Timer();

	/**
	 * Default constructor.
	 */
	public CrontabListener() {
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel(); // 销毁定时器
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.doViolationTask();
		this.doClearCacheStudents();
	}

	private void doViolationTask() {
		long period = 24 * 60 * 60 * 1000;

		/*** 定制每日23:00执行方法 ***/
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		// 第一次执行定时任务的时间
		Date firstTime = calendar.getTime();
		timer = new java.util.Timer(true); // 启动定时器
		timer.schedule(new ViolationTask(), firstTime, period); // 启动和间隔时间 间隔1天

	}

	private void doClearCacheStudents() {
		long period = 24 * 60 * 60 * 1000; // 每隔多久執行一次。

		/*** 定制每日23:00执行方法 ***/
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		// 第一次执行定时任务的时间
		Date firstTime = calendar.getTime();
		timer = new java.util.Timer(true); // 启动定时器
		timer.schedule(new CacheStudentsTask(), firstTime, period); // 启动和间隔时间
																	// 间隔1天

	}

}
