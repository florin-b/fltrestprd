package flota.service.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new Date());
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		return dateFormat.format(new Date());

	}

	public static String formatDate(String strDate) {

		String formatted = "";

		try {
			SimpleDateFormat formatFinal = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatFinal.parse(strDate);

			String pattern = "dd-MMM-yyyy";
			SimpleDateFormat formatInit = new SimpleDateFormat(pattern, new Locale("ro"));

			formatted = formatInit.format(date);
		} catch (ParseException p) {

		}

		return formatted;

	}

	public static String formatDateSap(String strDate) {

		String formatted = "";

		try {
			SimpleDateFormat formatFinal = new SimpleDateFormat("dd-mm-yyyy");
			Date date = formatFinal.parse(strDate);

			String pattern = "yyyymmdd";
			SimpleDateFormat formatInit = new SimpleDateFormat(pattern, new Locale("ro"));

			formatted = formatInit.format(date);
		} catch (ParseException p) {

		}

		return formatted;

	}

	public static String formatDateFromSap(String strDate) {

		String formatted = "";

		try {

			String pattern = "yyyymmdd";
			SimpleDateFormat formatInit = new SimpleDateFormat(pattern, new Locale("ro"));
			Date date = formatInit.parse(strDate);

			SimpleDateFormat formatFinal = new SimpleDateFormat("dd-mm-yyyy");

			formatted = formatFinal.format(date);
		} catch (ParseException p) {
			MailOperations.sendMail(p.toString());
		}

		return formatted;

	}

	public static String formatTime(String strTime) {
		return strTime.substring(0, 2) + ":" + strTime.substring(2, 4);
	}

}
