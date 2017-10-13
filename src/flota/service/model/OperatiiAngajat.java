package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.beans.Angajat;
import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.DateUtils;
import flota.service.utils.MailOperations;
import flota.service.utils.Utils;

public class OperatiiAngajat {

	private static final Logger logger = LogManager.getLogger(OperatiiAngajat.class);

	public int getKmCota(Connection conn, String codAngajat, String dataStart, String dataStop) {

		int kmCota = 0;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getKmCota());) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, DateUtils.formatDateSap(dataStart));
			stmt.setString(3, DateUtils.formatDateSap(dataStart));
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				kmCota = Integer.parseInt(rs.getString("cotakm"));
			}

			if (kmCota > 0)
				kmCota = getKmCotaInterval(kmCota, dataStart, dataStop);

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}

		return kmCota;

	}

	private int getKmCotaInterval(int kmCota, String startDate, String stopDate) {

		int totalKm = 0;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(stopDate, formatter);

		LocalDate next = start.minusDays(1);
		while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
			if (next.getDayOfWeek() != DayOfWeek.SUNDAY)
				totalKm += kmCota;

		}

		return totalKm;

	}

	public List<Angajat> getAngajati(String tipAngajat, String unitLog, String codDepart) {

		List<Angajat> listAngajati = new ArrayList<>();

		boolean isPersVanzari = Utils.isAngajatVanzari(tipAngajat);

		String sqlString;

		String unitLogQs = Utils.generateQs(unitLog);
		String departQs = Utils.generateQs(codDepart);

		if (isPersVanzari) {
			sqlString = SqlQueries.getSubordVanzari(unitLogQs, departQs);
		} else
			sqlString = SqlQueries.getSubordNonVanzari(unitLogQs);

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			int pos = 2;
			String[] unitLogs = unitLog.split(",");
			String[] departs = codDepart.split(",");

			stmt.setString(1, tipAngajat);

			for (int ii = 0; ii < unitLogs.length; ii++)
				stmt.setString(pos++, unitLogs[ii]);

			if (isPersVanzari) {

				for (int ii = 0; ii < departs.length; ii++)
					stmt.setString(pos++, departs[ii]);
			}

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				Angajat angajat = new Angajat();

				angajat.setCod(rs.getString("cod"));
				angajat.setNume(rs.getString("nume"));

				listAngajati.add(angajat);
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}

		return listAngajati;
	}

}
