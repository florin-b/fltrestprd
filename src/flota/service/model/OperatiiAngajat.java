package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.Utils;

public class OperatiiAngajat {

	private static final Logger logger = LogManager.getLogger(OperatiiAngajat.class);

	public int getKmCota(String codAngajat, String dataStart, String dataStop) {

		int kmCota = 0;

		try (Connection conn = DBManager.getTestInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getKmCota());) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, dataStart);
			stmt.setString(3, dataStart);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				kmCota = Integer.parseInt(rs.getString("cotakm"));
			}

			if (kmCota > 0)
				kmCota = getKmCotaInterval(kmCota, dataStart, dataStop);

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
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

}
