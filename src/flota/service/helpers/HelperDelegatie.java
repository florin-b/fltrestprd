package flota.service.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.beans.BeanDelegatieAprobare;
import flota.service.beans.IntervalDelegatie;
import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.MailOperations;
import flota.service.utils.Utils;

public class HelperDelegatie {

	private static final Logger logger = LogManager.getLogger(HelperDelegatie.class);

	public static String getAtentionare(BeanDelegatieAprobare delegatie) {

		String msgAtentionare = "";

		if (delegatie.getDistantaEfectuata() > delegatie.getDistantaCalculata())
			msgAtentionare = "Distanta efectuata este mai mare decat distanta calculata.";

		for (int i = 1; i < delegatie.getListOpriri().size() - 1; i++) {
			if (!delegatie.getListOpriri().get(i).isVizitat()) {
				msgAtentionare = "Nu au fost vizitate toate punctele din traseu.";
				break;
			}
		}

		return msgAtentionare;

	}

	public static String getStatusDelegatie(Connection conn, String idDelegatie) {
		String status = "-1";

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelegatieStatus());) {

			stmt.setString(1, idDelegatie);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				status = rs.getString("status");
			}

		}

		catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return status;
	}

	public static IntervalDelegatie getIntervalDelegatie(String idDelegatie) {

		IntervalDelegatie intervalDelegatie = new IntervalDelegatie();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		Date startDate = null;
		Date endDate = null;

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getIntervalDelegatie());) {

			stmt.setString(1, idDelegatie);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				startDate = formatter.parse(rs.getString("plecare"));
				endDate = formatter.parse(rs.getString("sosire"));
			}

			rs.close();

			intervalDelegatie.setStartDate(startDate);
			intervalDelegatie.setEndDate(endDate);

		}

		catch (SQLException | ParseException e) {
			logger.error(e.toString() + " , delegatie: " + idDelegatie);
			MailOperations.sendMail(e.toString() + " , delegatie: " + idDelegatie);
		}

		return intervalDelegatie;

	}

	public static void setDelegatieNeterm(String idDelegatie) {
		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.setDelegatieNeterminata());) {

			stmt.setString(1, idDelegatie);
			stmt.executeQuery();

		}

		catch (SQLException e) {
			logger.error(e.toString() + " , delegatie: " + idDelegatie);
			MailOperations.sendMail(e.toString() + " , delegatie: " + idDelegatie);
		}

	}

}
