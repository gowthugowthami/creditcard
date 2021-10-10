package app.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

	public static int getNumber(int max){
		Random r = new Random();
		return r.nextInt(max);
	}

	public static boolean containsSpecialCharacters(String str) {
		Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		return m.find();
	}


	private static String getExtension(final String path) {
		String result = null;
		if (path != null) {
			result = "";
			if (path.lastIndexOf('.') != -1) {
				result = path.substring(path.lastIndexOf('.'));
				if (result.startsWith(".")) {
					result = result.substring(1);
				}
			}
		}
		return result;
	}


	public static String getString(int n) {
		String CHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
		StringBuilder uuid = new StringBuilder();
		Random rnd = new Random();
		while (uuid.length() < n) {
			int index = (int) (rnd.nextFloat() * CHARS.length());
			uuid.append(CHARS.charAt(index));
		}
		return uuid.toString();
	}

	public static String getBing(){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(cal.getTime());
		return date;
	}

	public static long getToday(){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(Constants.ZERO_TIME_FORMAT);
		String date = df.format(cal.getTime());
		return Long.parseLong(date);
	}

	public static long getDate() {
		Calendar cal = Calendar.getInstance();
		long date = getDateFormatted(cal);
		return date;
	}

	public static long getYesterday(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -day);
		long date = getDateFormatted(cal);
		return date;
	}

	private static long getDateFormatted(Calendar cal) {
		DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
		String dateStr = df.format(cal.getTime());
		long date = Long.parseLong(dateStr);
		return date;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static Calendar clearTime(Calendar cal){
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.AM_PM);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal;
	}

	public static String getPretty(Long date){
		String dateString = "";
		try {
			SimpleDateFormat parser = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date d = parser.parse(Long.toString(date));

			SimpleDateFormat sdf2 = new SimpleDateFormat(Constants.DATE_PRETTY);
			dateString = sdf2.format(d);
		}catch(Exception ex){}
		return dateString;
	}

}