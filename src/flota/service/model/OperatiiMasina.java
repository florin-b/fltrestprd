package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.Utils;

public class OperatiiMasina {

	private static final Logger logger = LogManager.getLogger(OperatiiMasina.class);

	public String getCodDispGps(Connection conn, String nrDelegatie) {

		String codDisp = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodDispGps(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			// String nrAuto = getNrAuto(nrDelegatie).replace("-", "").replace("
			// ", "");

			// stmt.setString(1, nrAuto);
			stmt.setString(1, nrDelegatie);

			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				codDisp = rs.getString("vcode");
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return codDisp;

	}

	public String getNrAuto(String nrDelegatie) {

		String nrAuto = null;

		try (Connection conn = DBManager.getTestInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getNrAuto(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, nrDelegatie);

			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				nrAuto = rs.getString("nrauto");
			}

		}

		catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return nrAuto;

	}

}
