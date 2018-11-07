package com.favccxx.report.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtil {
	public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	

	/**
	 * 得到某月份的第一天（yyyy-MM-dd）。如参数为空，则得到当前月份的第一天
	 * 
	 * @param dealDate
	 * @return
	 */
	public static String getMonthFirstDay(Date dealDate) {
		Calendar calendar = Calendar.getInstance();
		if (dealDate != null) {
			calendar.setTime(dealDate);
		}
		calendar.set(Calendar.DATE, 1);
		return df.format(calendar.getTime());
	}

	/**
	 * 得到某月份的最后一天（yyyy-MM-dd）。如参数为空，则得到当前月份的最后一天
	 * 
	 * @param dealDate
	 * @return
	 */
	public static String getMonthLastDay(Date dealDate) {
		Calendar calendar = Calendar.getInstance();
		if (dealDate != null) {
			calendar.setTime(dealDate);
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return df.format(calendar.getTime());
	}

	/**
	 * 得到某个日期之后的几个月后的日期
	 * 
	 * @param lastDealDate
	 *            上次处理的日期
	 * @param intervalMonths
	 *            间隔的月份
	 * @return
	 */
	public static String getNextDealDate(Date lastDealDate, int intervalMonths) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(lastDealDate);
		cal.add(Calendar.MONTH, intervalMonths);
		return df.format(cal.getTime());
	}

	/**
	 * 获取某个日期间隔几天后的日期
	 * 
	 * @param curentDate
	 * @param intervalDays
	 * @return
	 * @throws ParseException
	 */
	public static String getFutureDate(Date curentDate, int intervalDays) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curentDate);
		cal.add(Calendar.DAY_OF_MONTH, intervalDays);
		return df.format(cal.getTime());
	}

	/**
	 * 将String类型的日期转换成Date类型
	 * 
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date toDate(String strDate) throws Exception {
		if (StringUtils.isEmpty(strDate)) {
			throw new Exception("格式化日期出错!");
		}
		return df.parse(strDate);
	}

	public static String getToday() throws Exception {
		return df.format(new Date());
	}

	/**
	 * 将Date型日期转为String类型
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String dateToString(Date date) throws Exception {
		return df.format(date);
	}

	public static void main(String[] args) {
		// String result = getNextDealDate(new Date(), 5);
		// System.out.println("The result is: " + getNoSepStrDate());
		try {
			System.out.println(getFutureDate(new Date(), 5));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date());
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			DateFormat df = new SimpleDateFormat(pattern[0].toString());
			formatDate = df.format(date);
		} else {
			formatDate = df.format(date);
		}
		return formatDate;
	}

	public static String getNoSepStrDate() {
		String tempDate = formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		return tempDate.replace("-", "").replace(":", "").replace(" ", "");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return df.parse(str.toString());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	public static String date2String(java.util.Date date, String pattern) {
		if (date == null) {
			throw new java.lang.IllegalArgumentException("timestamp null illegal");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_STANDARD;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date string2Date(String strDate, String pattern) {
		if (strDate == null || strDate.equals("")) {
			throw new RuntimeException("str date null");
		}
		if (pattern == null || pattern.equals("")) {
			pattern = PATTERN_DATE;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;

		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return date;
	}

	public static Date getStartTimeOfDate(Date currentDate) {
		String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 00:00:00";
		return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
	}

	public static Date getEndTimeOfDate(Date currentDate) {
		String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 23:59:59";
		return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
	}

	public static String getStartTimeStringOfDate(Date currentDate) {
		return date2String(currentDate, "yyyy-MM-dd") + " 00:00:00";
	}

	public static String getEndTimeStringOfDate(Date currentDate) {
		return date2String(currentDate, "yyyy-MM-dd") + " 23:59:59";
	}

	public static Date getFirstDayOfMonth(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = 1;
		c.set(year, month, day, 0, 0, 0);
		return c.getTime();
	}

	public static Date getLastDayOfMonth(Calendar c) {
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = 1;
		if (month > 11) {
			month = 0;
			year = year + 1;
		}
		c.set(year, month, day - 1, 0, 0, 0);
		return c.getTime();
	}
}
