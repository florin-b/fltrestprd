package flota.service.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.queries.SqlQueries;
import flota.service.utils.MailOperations;
import flota.service.utils.Utils;

public class HelperAprobare {

	private static final Logger logger = LogManager.getLogger(HelperAprobare.class);

	public static String getCodAprobare(Connection conn, String codAngajat, String tipAngajat) {

		String codAprobare;

		if (tipAngajat.toUpperCase().startsWith("KA"))
			codAprobare = getCodAprobareKA(conn, codAngajat, tipAngajat);
		else if (tipAngajat.toUpperCase().startsWith("CAG") || tipAngajat.toUpperCase().startsWith("CONS"))
			codAprobare = getCodAprobareConsilieri(conn, codAngajat, tipAngajat);
		else
			codAprobare = getCodAprobareGeneral(conn, codAngajat);

		return codAprobare;

	}

	public static String getCodAprobareGeneral(Connection conn, String codAngajat) {

		String codAprobare = "-1";

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobare());) {

			stmt.setString(1, codAngajat);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				codAprobare = rs.getString("fid");

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(Utils.getStackTrace(e));

		}

		return codAprobare;

	}

	public static String getCodAprobareConsilieri(Connection conn, String codConsilier, String tipConsilier) {

		String codAprobare = null;
		String codSM = null;
		String codSDCVA = null;
		String codDZ = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobareConsilieri());) {

			stmt.setString(1, codConsilier);
			stmt.setString(2, tipConsilier);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				if (rs.getString("aprobat").equalsIgnoreCase("SM"))
					codSM = rs.getString("fid");
				else if (rs.getString("aprobat").equalsIgnoreCase("SDCVA"))
					codSDCVA = rs.getString("fid");
				else if (rs.getString("aprobat").equalsIgnoreCase("DZ"))
					codDZ = rs.getString("fid");

			}

			if (codSM != null)
				codAprobare = codSM;

			if (codSDCVA != null)
				codAprobare = codSDCVA;

			if (codSM == null && codSDCVA == null)
				codAprobare = codDZ;

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
			logger.error(Utils.getStackTrace(e));
		}

		return codAprobare;

	}

	public static String getCodAprobareKA(Connection conn, String codAngajat, String tipKA) {

		String codAprobare = null;
		String codSDKA = null;
		String codDZ = null;

		if (tipKA.equals("KA08"))
			return getCodAprobareKA08(conn);

		if (tipKA.equals("KA05"))
			return getCodAprobareKA05(conn);

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobareKA());) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, tipKA);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				if (rs.getString("aprobat").equalsIgnoreCase("SDKA"))
					codSDKA = rs.getString("fid");
				else if (rs.getString("aprobat").equalsIgnoreCase("DZ"))
					codDZ = rs.getString("fid");

			}

			if (codSDKA != null)
				codAprobare = codSDKA;
			else
				codAprobare = codDZ;

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
			logger.error(Utils.getStackTrace(e));
		}

		return codAprobare;

	}

	public static String getCodAprobareKA08(Connection conn) {

		String codAprobare = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobareKA08());) {

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				codAprobare = rs.getString("fid");

			}

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
			logger.error(Utils.getStackTrace(e));
		}

		return codAprobare;

	}

	public static String getCodAprobareKA05(Connection conn) {

		String codAprobare = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodAprobareKA05());) {

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				codAprobare = rs.getString("fid");

			}

		} catch (SQLException e) {
			MailOperations.sendMail(e.toString());
			logger.error(Utils.getStackTrace(e));
		}

		return codAprobare;

	}

}
