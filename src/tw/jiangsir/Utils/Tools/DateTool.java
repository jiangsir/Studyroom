package tw.jiangsir.Utils.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tw.jiangsir.Studyroom.DAOs.BookingService;
import tw.jiangsir.Studyroom.Objects.Booking;

public class DateTool {

	public static boolean isSameday(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
				&& (cal1.get(Calendar.DAY_OF_YEAR) == cal2
						.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 只取目前的時間，轉換成 Time, 實際日期為 1970-01-01
	 * 
	 * @return
	 */
	public static java.sql.Time getNowtime() {
		Calendar now = Calendar.getInstance();
		// sql.Time 的 before after 判斷背後仍有一個日期，如果只有讀入時間，就以 1970-01-01 作為日期進行比較。
		// 因此不能拿目前的完整日期時間去取得 Time 會同時取得目前的日期，而造成比較錯誤。
		return java.sql.Time.valueOf(now.get(Calendar.HOUR_OF_DAY) + ":"
				+ now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));
	}

	public static java.sql.Date getNextDate(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static java.sql.Date getPrevDate(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static java.sql.Date getNextMonth(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	public static java.sql.Date getPrevMonth(java.sql.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return new java.sql.Date(calendar.getTime().getTime());
	}

	/**
	 * 取得 date 所屬的月份的第一天禮拜幾
	 * 
	 * @param date
	 * @return
	 */
	public static int getFirstdayOfMonth(java.sql.Date date) {
		java.util.Calendar currentCal = java.util.Calendar.getInstance();
		currentCal.setTime(date);
		currentCal.set(Calendar.DAY_OF_MONTH, 1);
		// java.util.Date date2 = currentCal.getTime();
		// java.text.SimpleDateFormat format = new
		// java.text.SimpleDateFormat("E");
		// return format.format(date2);
		// System.out.println(currentCal);
		// System.out.println(currentCal.getTime());
		// System.out.println(currentCal.get(Calendar.DAY_OF_WEEK));
		return currentCal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getLastDayOfMonth(java.sql.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 該月份的所有日期集合
	 * 
	 * @param date
	 * @return
	 */
	public static ArrayList<java.util.Date> getDaysOfMonth(java.sql.Date date) {
		ArrayList<java.util.Date> days = new ArrayList<java.util.Date>();
		for (int i = 1; i < DateTool.getFirstdayOfMonth(date); i++) {
			days.add(null);
		}
		for (int i = 1; i <= DateTool.getLastDayOfMonth(date); i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, i);
			days.add(cal.getTime());
		}
		return days;
	}

	/**
	 * 計算兩個日期相隔的天數
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days = d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	// public static Long getDaysBetween(Date startDate, Date endDate) {
	// Calendar fromCalendar = Calendar.getInstance();
	// fromCalendar.setTime(startDate);
	// fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
	// fromCalendar.set(Calendar.MINUTE, 0);
	// fromCalendar.set(Calendar.SECOND, 0);
	// fromCalendar.set(Calendar.MILLISECOND, 0);
	//
	// Calendar toCalendar = Calendar.getInstance();
	// toCalendar.setTime(endDate);
	// toCalendar.set(Calendar.HOUR_OF_DAY, 0);
	// toCalendar.set(Calendar.MINUTE, 0);
	// toCalendar.set(Calendar.SECOND, 0);
	// toCalendar.set(Calendar.MILLISECOND, 0);
	//
	// return (toCalendar.getTime().getTime() - fromCalendar.getTime()
	// .getTime()) / (1000 * 60 * 60 * 24);
	// }

	/**
	 * 計算兩個 sql.date 之間相隔的天數。因為時間都是 00:00:00.0 因此可以直接相減計算。
	 * 
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public static Long getDayCountBetween(java.sql.Date begindate,
			java.sql.Date enddate) {
		return (enddate.getTime() - begindate.getTime())
				/ (1000 * 60 * 60 * 24);
	}

	/**
	 * 取得兩個日期中間的每一天
	 * 
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public static ArrayList<java.sql.Date> getDaysBetween(
			java.sql.Date begindate, java.sql.Date enddate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(begindate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		// Calendar toCalendar = Calendar.getInstance();
		// toCalendar.setTime(enddate);
		// toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		// toCalendar.set(Calendar.MINUTE, 0);
		// toCalendar.set(Calendar.SECOND, 0);
		// toCalendar.set(Calendar.MILLISECOND, 0);

		ArrayList<java.sql.Date> days = new ArrayList<java.sql.Date>();
		for (int day = 0; day <= DateTool
				.getDayCountBetween(begindate, enddate); day++) {
			days.add(new java.sql.Date(fromCalendar.getTime().getTime()));
			fromCalendar.add(Calendar.DATE, 1);
		}
		return days;
	}

	public static void main(String[] args) {
		java.sql.Date date = java.sql.Date.valueOf("2014-01-30");
		java.sql.Time time = java.sql.Time.valueOf("10:00:00");
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(date
				.toString() + " " + time.toString());
		System.out.println("timestamp=" + timestamp);
		java.sql.Time nowtime = new java.sql.Time(System.currentTimeMillis());
		if (nowtime.after(time)) {
			System.out.println(nowtime + " is after " + time);
		} else {
			System.out.println(nowtime + " is not after " + time);
		}

		System.out.println("date="
				+ new java.util.Date(java.sql.Date.valueOf("2014-01-30")
						.getTime()));
		System.out.println(java.sql.Time.valueOf("10:00:00"));
		System.out.println(date.toString() + time.toString());

	}
}
