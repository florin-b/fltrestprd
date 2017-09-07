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
import flota.service.utils.DateUtils;
import flota.service.utils.MailOperations;
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
			MailOperations.sendMail(e.toString());
		}

		return codDisp;

	}

	public List<String> getCodDispGps(String codAngajat, String dataStart) {

		List<String> listDisp = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCodDispGpsData())) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, DateUtils.formatDateSap(dataStart));

			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				listDisp.add(rs.getString("vcode"));
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}
		return listDisp;

	}

	public String getNrAuto(String nrDelegatie) {

		String nrAuto = null;

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
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
			MailOperations.sendMail(e.toString());
		}

		return nrAuto;

	}

	public List<String> getMasiniAngajat(String codAngajat, String dataStart) {

		List<String> nrAuto = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getMasiniAngajat());) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, DateUtils.formatDateSap(dataStart));

			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				nrAuto.add(rs.getString("ktext"));
			}

		}

		catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}

		return nrAuto;

	}

}
