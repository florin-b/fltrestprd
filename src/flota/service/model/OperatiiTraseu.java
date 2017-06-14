package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.beans.BeanDelegatieAprobare;
import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.Utils;

public class OperatiiTraseu {

	private static final Logger logger = LogManager.getLogger(OperatiiTraseu.class);

	public List<String> getCoordonateTraseu(String codMasina, String dataStart, String dataStop) {

		List<String> listCoords = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateTraseu(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codMasina);
			stmt.setString(2, dataStart);
			stmt.setString(3, dataStop);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				listCoords.add(rs.getString("lat") + ":" + rs.getString("lon"));
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listCoords;

	}

}
