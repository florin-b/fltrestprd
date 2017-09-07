package flota.service.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static String getStackTrace(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	public static synchronized String getId() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "01-01-2017";
		Date date;
		Calendar calendar = Calendar.getInstance();

		try {
			date = sdf.parse(dateInString);
			calendar.setTime(date);

		} catch (ParseException e) {

		}

		long millis = System.currentTimeMillis() - calendar.getTimeInMillis();
		return (String.valueOf(millis));
	}

	public static String getUnitLog(String numeFiliala) {
		String fl = "NN10";

		if (numeFiliala.equals("BACAU"))
			fl = "BC10";

		if (numeFiliala.equals("BUZAU"))
			fl = "BZ10";

		if (numeFiliala.equals("GALATI"))
			fl = "GL10";

		if (numeFiliala.equals("PITESTI"))
			fl = "AG10";

		if (numeFiliala.equals("TIMISOARA"))
			fl = "TM10";

		if (numeFiliala.equals("ORADEA"))
			fl = "BH10";

		if (numeFiliala.equals("FOCSANI"))
			fl = "VN10";

		if (numeFiliala.equals("GLINA"))
			fl = "BU10";

		if (numeFiliala.equals("ANDRONACHE"))
			fl = "BU13";

		if (numeFiliala.equals("OTOPENI"))
			fl = "BU12";

		if (numeFiliala.equals("CLUJ"))
			fl = "CJ10";

		if (numeFiliala.equals("BAIA"))
			fl = "MM10";

		if (numeFiliala.equals("MILITARI"))
			fl = "BU11";

		if (numeFiliala.equals("CONSTANTA"))
			fl = "CT10";

		if (numeFiliala.equals("BRASOV"))
			fl = "BV10";

		if (numeFiliala.equals("PLOIESTI"))
			fl = "PH10";

		if (numeFiliala.equals("PIATRA"))
			fl = "NT10";

		if (numeFiliala.equals("MURES"))
			fl = "MS10";

		if (numeFiliala.equals("IASI"))
			fl = "IS10";

		if (numeFiliala.equals("CRAIOVA"))
			fl = "DJ10";

		if (numeFiliala.equals("SIBIU"))
			fl = "SB10";

		if (numeFiliala.equals("DEVA"))
			fl = "HD10";

		return fl;

	}

	public static boolean isAngajatVanzari(String tipAngajat) {

		boolean isVanzari;

		switch (tipAngajat) {
		case "AV":
		case "SD":
		case "KA":
		case "CV":
		case "DV":
		case "DD":
			isVanzari = true;
			break;
		default:
			isVanzari = false;
			break;
		}

		return isVanzari;

	}

	public static String flattenToAscii(String string) {
		StringBuilder sb = new StringBuilder(string.length());
		String localString = Normalizer.normalize(string, Normalizer.Form.NFD);
		for (char c : localString.toCharArray()) {
			if (c <= '\u007F')
				sb.append(c);
		}
		return sb.toString();
	}

	public static String getTipSubordonat(String tipSuperior) {

		if (tipSuperior.equals("SDKA"))
			return "KA";

		return tipSuperior;

	}

}
