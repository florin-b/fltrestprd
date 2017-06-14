package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.Utils;



public class OperatiiAdresa {

	
	private static final Logger logger = LogManager.getLogger(OperatiiAdresa.class);

	public List<String> getLocalitatiJudet(String codJudet) {

		List<String> listLocalitati = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getLocalitatiJudet(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codJudet);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				listLocalitati.add(rs.getString("localitate"));

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listLocalitati;
	}
	
	
}
