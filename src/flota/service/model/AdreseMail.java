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

public class AdreseMail {

	private static final Logger logger = LogManager.getLogger(AdreseMail.class);

	public List<String> getAdresaDZ(String idDelegatie) {

		List<String> listAdrese = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getMailDZFiliala());) {

			stmt.setString(1, idDelegatie);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				listAdrese.add(rs.getString("mail"));

			}

			rs.close();

		}

		catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listAdrese;
	}

}
