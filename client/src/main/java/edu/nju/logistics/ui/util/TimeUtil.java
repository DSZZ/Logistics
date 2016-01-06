package edu.nju.logistics.ui.util;

import java.util.Calendar;

/**
 * 获得系统当前时间的工具类 形式如 2015/10/17 19:15:7
 * 
 * @author 董轶波
 *
 */
public class TimeUtil {

	private TimeUtil() {
	}

	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		if(hour.length() == 1)
			hour = "0" + hour;
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		if(minute.length() == 1)
			minute = "0" + minute;
		String second = String.valueOf(c.get(Calendar.SECOND));
		if(second.length() == 1)
			second = "0" + second;
		String time = hour + ":" + minute + ":" + second;			

		return getCurrentDate() + " " + time;
	}
	
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		if(month.length() == 1)
			month = "0" + month;
		String date = String.valueOf(c.get(Calendar.DATE));
		if(date.length() == 1)
			date = "0" + date;
		String currentDate = year + "/" + month + "/" + date;
		
		return currentDate;
	}
	
	/**
	 * 比较时间一是否比时间二早
	 * @param time1 精确到秒
	 * @param time2 精确到秒
	 * @return
	 */
	public static boolean isFore(String time1, String time2) {
		long a = Long.valueOf(time1.substring(0, 4) + time1.substring(5, 7) + time1.substring(8, 10) + time1.substring(11, 13)
			+time1.substring(14, 16) + time1.substring(17, 19));
		long b = Long.valueOf(time2.substring(0, 4) + time2.substring(5, 7) + time2.substring(8, 10) + time2.substring(11, 13)
			+time2.substring(14, 16) + time2.substring(17, 19));
		return a < b;
	}

	public static void main(String[] args) {
		System.out.println(TimeUtil.getCurrentTime());
		System.out.println(TimeUtil.isFore(TimeUtil.getCurrentTime(), "2015/11/21 15:00:00"));
	}
}
