package flota.service.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.queries.SqlQueries;
import flota.service.utils.MailOperations;
import flota.service.utils.Utils;

public class HelperAprobare {

	private static final Logger logger = LogManager.getLogger(HelperAprobare.class);

	public static String getCodAprobare(Connection conn, String codAngajat, String tipAngajat) {

		String codAprobare = "-1";

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobare());) {

			stmt.setString(1, codAngajat);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			String codAprobareSDKA = "";
			String codAprobareSDCVA = "";

			while (rs.next()) {

				if (tipAngajat.toUpperCase().startsWith("KA"))
					codAprobareSDKA = getCodAprobareExceptie(conn, codAngajat, "SDKA", tipAngajat);

				if (tipAngajat.toUpperCase().startsWith("CAG"))
					codAprobareSDCVA = getCodAprobareExceptie(conn, codAngajat, "SDCVA", tipAngajat);

				if (!rs.getString("aprobat").equalsIgnoreCase("SDKA") && !rs.getString("aprobat").equalsIgnoreCase("SDCVA"))
					codAprobare = rs.getString("fid");

			}

			codAprobare = codAprobareSDKA.isEmpty() ? (codAprobareSDCVA.isEmpty() ? codAprobare : codAprobareSDCVA) : codAprobareSDKA;

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(Utils.getStackTrace(e));

		}

		return codAprobare;

	}

	public static String getCodAprobareExceptie(Connection conn, String codAngajat, String tipAprobare, String tipAngajat) {
		String codAprobare = "";

		try {
			PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobareExceptie());

			stmt.setString(1, codAngajat);
			stmt.setString(2, tipAprobare);
			stmt.setString(3, tipAngajat);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				codAprobare = rs.getString("fid");
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
			logger.error(Utils.getStackTrace(e));
		}

		return codAprobare;
	}

}
