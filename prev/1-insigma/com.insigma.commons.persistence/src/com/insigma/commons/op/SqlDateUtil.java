package com.insigma.commons.op;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 非SQL Date类使用DateTimeUtil类
 */
@Deprecated
public class SqlDateUtil {

	public static String getChineseDate(java.util.Date d) {
		if (d == null) {
			return new String();
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
			String dtrDate = df.format(d);
			return dtrDate.substring(0, 4) + "年" + Integer.parseInt(dtrDate.substring(4, 6)) + "月"
					+ Integer.parseInt(dtrDate.substring(6, 8)) + "日";
		}
	}

	public static String formatDate(java.util.Date d, String format) {
		if (format == null || format.equals("")) {
			format = "yyyy-mm-dd";
		}
		if (d == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(d);
		}
	}

	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if (dealMonth.length() != 6 || alterMonth.length() != 6) {
			length = -1;
		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				length = -2;
			} else {
				int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
				int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
				int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
				int alterMonthInt = Integer.parseInt(alterMonth.substring(4, 6));
				length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
			}
		}
		return length;
	}

	public static int convertDateToYear(java.util.Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy", new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	public static String convertDateToYearMonth(java.util.Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM", new DateFormatSymbols());
		return df.format(d);
	}

	public static String convertDateToYearMonthDay(java.util.Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		return df.format(d);
	}

	public static int daysBetweenDates(java.util.Date newDate, java.util.Date oldDate) {
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(6);
		int nyear = caln.get(1);
		for (int oyear = calo.get(1); nyear > oyear;) {
			calo.set(2, 11);
			calo.set(5, 31);
			days += calo.get(6);
			oyear++;
			calo.set(1, oyear);
		}

		int nday = caln.get(6);
		days = (days + nday) - oday;
		return days;
	}

	public static java.util.Date getDateBetween(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(5, intBetween);
		return calo.getTime();
	}

	public static String increaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		if (++month <= 12 && month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		if (month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			return (new Integer(year + 1)).toString() + "0" + (new Integer(month - 12)).toString();
	}

	public static String increaseYearMonth(String yearMonth, int addMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month += addMonth;
		year += month / 12;
		month %= 12;
		if (month <= 12 && month >= 10)
			return year + (new Integer(month)).toString();
		else
			return year + "0" + (new Integer(month)).toString();
	}

	public static String descreaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		if (--month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		if (month > 0 && month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			return (new Integer(year - 1)).toString() + (new Integer(month + 12)).toString();
	}

	public static String getCurrentTime_String() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}

	public static java.util.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		java.util.Date d = cal.getTime();
		return d;
	}

	public static java.sql.Date getCurrentSqlDate() {
		return new java.sql.Date(SqlDateUtil.getCurrentDate().getTime());
	}

	public static String getCurrentYearMonth() {
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(1))).toString();
		String currentMonth = null;
		if (cal.get(2) < 9)
			currentMonth = "0" + (new Integer(cal.get(2) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(2) + 1)).toString();
		return currentYear + currentMonth;
	}

	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(1);
		return currentYear;
	}

	public static String dateToString(Timestamp d, String format) {
		return dateToString(d, format);
	}

	public static boolean yearMonthGreatEqual(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);
		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		if (Integer.parseInt(temp1) == Integer.parseInt(temp2))
			return Integer.parseInt(temp3) >= Integer.parseInt(temp4);
		else
			return false;
	}

	public static boolean yearMonthGreater(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);
		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		if (Integer.parseInt(temp1) == Integer.parseInt(temp2))
			return Integer.parseInt(temp3) > Integer.parseInt(temp4);
		else
			return false;
	}

	public static String getLastDay(String term) {
		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));
		String getLastDay = "";
		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0)
				getLastDay = "29";
			else
				getLastDay = "28";
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11)
			getLastDay = "30";
		else
			getLastDay = "31";
		return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月" + getLastDay + "日";
	}

	public static String getMonthBetween(String strDateBegin, String strDateEnd) {
		try {
			String strOut;
			if (strDateBegin.equals("") || strDateEnd.equals("") || strDateBegin.length() != 6
					|| strDateEnd.length() != 6) {
				strOut = "";
			} else {
				int intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4)) * 12
						+ Integer.parseInt(strDateBegin.substring(4, 6));
				int intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12
						+ Integer.parseInt(strDateEnd.substring(4, 6));
				strOut = String.valueOf(intMonthBegin - intMonthEnd);
			}
			return strOut;
		} catch (Exception e) {
			return "0";
		}
	}

	public static String getYearAndMonth(String yearMonth) {
		if (yearMonth == null)
			return "";
		String ym = yearMonth.trim();
		if (6 != ym.length()) {
			return ym;
		} else {
			String year = ym.substring(0, 4);
			String month = ym.substring(4);
			return year + "年" + month + "月";
		}
	}

	public static String getYearMonthByMonth(String month) {
		if (month == null)
			return null;
		String ym = month.trim();
		if (6 != ym.length()) {
			return ym;
		} else {
			String year = ym.substring(0, 4);
			// String month1 = ym.substring(4);
			return year + "年" + month + "月";
		}
	}

	public static java.util.Date increaseMonth(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(2, intBetween);
		return calo.getTime();
	}

	public static java.util.Date increaseYear(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(1, intBetween);
		return calo.getTime();
	}

	/**
	 * 时间转换
	 * 
	 * @param date
	 *            date
	 * @return Timestamp Timestamp
	 */
	public static Timestamp dateToTimestamp(java.util.Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 时间格式转换
	 * 
	 * @param timestamp
	 *            timestamp
	 * @param format
	 *            format
	 * @return String String
	 */
	public static String format(Timestamp timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		return sdf.format(timestamp);
	}

	public static Timestamp toDate(java.util.Date date) {
		return new Timestamp(date.getTime());
	}
}
