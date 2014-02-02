package tw.jiangsir.Utils.Tools;

import java.util.Calendar;
import java.util.Date;

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

		System.out.println(java.sql.Date.valueOf("2014-01-30"));
		System.out.println(java.sql.Time.valueOf("10:00:00"));
		System.out.println(date.toString() + time.toString());

	}
}
