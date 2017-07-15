package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.maps.model.LatLng;

import flota.service.beans.BeanDelegatieCauta;
import flota.service.beans.PunctTraseu;
import flota.service.database.DBManager;

import flota.service.queries.SqlQueries;
import flota.service.utils.MailOperations;
import flota.service.utils.MapUtils;
import flota.service.utils.Utils;

public class OperatiiTraseu {

	private static final Logger logger = LogManager.getLogger(OperatiiTraseu.class);

	private static final double razaKmSosire = 5;

	private LatLng coordonatePlecare;
	private LatLng coordonateSosire;
	private String codDisp;
	private String dataPlecare;
	private String dataSosire;

	public List<String> getCoordonateTraseu(String idDelegatie) {

		List<String> listCoords = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateTraseu(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			String codDisp = new OperatiiMasina().getCodDispGps(conn, idDelegatie);
			BeanDelegatieCauta delegatie = new OperatiiDelegatii().getDelegatie(conn, idDelegatie);

			String dataPlecare = delegatie.getDataPlecare() + " " + delegatie.getOraPlecare().substring(0, 2) + ":" + delegatie.getOraPlecare().substring(2, 4);

			String dataSosire = delegatie.getDataSosire() + " " + "23:59";

			stmt.setString(1, codDisp);

			stmt.setString(2, dataPlecare);
			stmt.setString(3, dataSosire);

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

	public String determinaSfarsitDelegatie(Connection conn, String idDelegatie) {

		double startKm = 0;
		double stopKm = 0;
		String oraSosire = null;

		List<PunctTraseu> objPuncte = null;
		BeanDelegatieCauta delegatie = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateTraseu(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			codDisp = new OperatiiMasina().getCodDispGps(conn, idDelegatie);
			delegatie = new OperatiiDelegatii().getDelegatie(conn, idDelegatie);
			delegatie.setId(idDelegatie);

			dataPlecare = delegatie.getDataPlecare() + " " + delegatie.getOraPlecare().substring(0, 2) + ":" + delegatie.getOraPlecare().substring(2, 4);

			dataSosire = delegatie.getDataSosire() + " " + "23:59";

			stmt.setString(1, codDisp);

			stmt.setString(2, dataPlecare);
			stmt.setString(3, dataSosire);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			objPuncte = new OperatiiDelegatii().getPuncteTraseu(conn, idDelegatie);

			LatLng coordStop = objPuncte.get(objPuncte.size() - 1).getCoordonate();

			int i = 0;
			boolean startDel = false;
			while (rs.next()) {

				if (i == 0) {
					startKm = rs.getDouble("km");
					coordonatePlecare = new LatLng(rs.getDouble("lat"), rs.getDouble("lon"));
				}

				double distSosire = MapUtils.distanceXtoY(coordStop.lat, coordStop.lng, rs.getDouble("lat"), rs.getDouble("lon"), "K");
				
				System.out.println(distSosire);

				if (distSosire > razaKmSosire)
					startDel = true;

				for (int jj = 1; jj < objPuncte.size() - 1; jj++) {

					if (!objPuncte.get(jj).isVizitat()) {

						double distPunct = MapUtils.distanceXtoY(objPuncte.get(jj).getCoordonate().lat, objPuncte.get(jj).getCoordonate().lng,
								rs.getDouble("lat"), rs.getDouble("lon"), "K");

						if (distPunct < razaKmSosire) {
							objPuncte.get(jj).setVizitat(true);
							break;
						}
					}

				}

				if (distSosire < razaKmSosire && startDel) {
					stopKm = rs.getDouble("km");
					oraSosire = rs.getString("gtime").replace(":", "");
					objPuncte.get(objPuncte.size() - 1).setVizitat(true);
					coordonateSosire = new LatLng(rs.getDouble("lat"), rs.getDouble("lon"));
					break;
				} else {
					i++;
				}

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		if ((stopKm - startKm) > 0)
			try {
				actualizeazaSfarsitDelegatie(conn, delegatie, oraSosire, stopKm - startKm, objPuncte);
			} catch (SQLException e) {
				MailOperations.sendMail(e.toString());
			}

		return " ";
	}

	public void actualizeazaSfarsitDelegatie(Connection conn, BeanDelegatieCauta delegatie, String oraSosire, double distReal, List<PunctTraseu> puncte)
			throws SQLException {

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.setSfarsitDelegatie());) {

			stmt.setString(1, oraSosire);
			stmt.setDouble(2, (int) distReal);
			stmt.setString(3, delegatie.getId());

			stmt.executeQuery();

			PreparedStatement stmt1 = null;

			for (PunctTraseu punct : puncte) {

				stmt1 = conn.prepareStatement(SqlQueries.updatePuncte());
				stmt1.setString(1, punct.isVizitat() ? "1" : "0");
				stmt1.setString(2, delegatie.getId());
				stmt1.setString(3, String.valueOf(punct.getPozitie()));

				stmt1.executeQuery();

			}

			if (stmt1 != null) {
				stmt1.close();
				stmt1 = null;
			}

			int kmCota = new OperatiiAngajat().getKmCota(conn, delegatie.getAngajatId(), delegatie.getDataPlecare(), delegatie.getDataSosire());

			boolean aprobAutomat = verificaAprobareAutomataAnte(conn, delegatie, distReal, puncte, kmCota);

			if (!aprobAutomat)
				recalculeazaTraseuTeoretic(conn, delegatie, puncte);

			verificaAprobareAutomataPost(conn, delegatie, distReal, puncte, kmCota);

		}

	}

	public static boolean verificaAprobareAutomataAnte(Connection conn, BeanDelegatieCauta delegatie, double distReal, List<PunctTraseu> puncte, int kmCota) {

		if (distReal > (delegatie.getDistantaCalculata() + kmCota))
			return false;

		for (int i = 1; i < puncte.size() - 1; i++)
			if (!puncte.get(i).isVizitat())
				return false;

		new OperatiiDelegatii().aprobaAutomatDelegatie(conn, delegatie.getId());

		return true;

	}

	public static boolean verificaAprobareAutomataPost(Connection conn, BeanDelegatieCauta delegatie, double distReal, List<PunctTraseu> puncte, int kmCota) {

		if (distReal > (delegatie.getDistantaCalculata() + kmCota))
			return false;

		new OperatiiDelegatii().aprobaAutomatDelegatie(conn, delegatie.getId());

		return true;

	}

	public void recalculeazaTraseuTeoretic(Connection conn, BeanDelegatieCauta delegatie, List<PunctTraseu> puncte) {

		List<LatLng> coordonateOpriri = getCoordOpriri(codDisp, dataPlecare, dataSosire);
		coordonateOpriri.add(0, coordonatePlecare);
		coordonateOpriri.add(coordonateSosire);
		int distantaTeoretica = MapUtils.getDistantaTraseu(coordonateOpriri);

		delegatie.setDistantaCalculata(distantaTeoretica);

		List<String> adreseOpriri = MapUtils.getAdreseCoordonate(coordonateOpriri);

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.updateDistantaCalculata());) {

			stmt.setDouble(1, distantaTeoretica);
			stmt.setString(2, delegatie.getId());

			stmt.executeUpdate();

			if (!adreseOpriri.isEmpty()) {

				stergePuncteTraseu(conn, delegatie.getId());

				PreparedStatement stmt1 = null;

				int contorOpriri = 2;

				for (String adresa : adreseOpriri) {

					if (punctExista(adresa, puncte))
						continue;

					stmt1 = conn.prepareStatement(SqlQueries.adaugaOpririDelegatie());

					String[] arrayAdresa = adresa.trim().split("/");
					stmt1.setString(1, delegatie.getId());
					stmt1.setString(2, String.valueOf(contorOpriri++));
					stmt1.setString(3, arrayAdresa[1].trim().toUpperCase());
					stmt1.setString(4, arrayAdresa[0].trim().toUpperCase());
					stmt1.setString(5, "1");

					stmt1.executeQuery();

				}

				if (stmt1 != null)
					stmt1.close();

			}

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(ex.toString());
		}

	}

	private static boolean punctExista(String punct, List<PunctTraseu> puncte) {

		for (PunctTraseu pTraseu : puncte)
			if (punct.split("/")[0].toLowerCase().contains(pTraseu.getStrAdresa().split("/")[1].toLowerCase().trim()))
				return true;

		return false;

	}

	private static void stergePuncteTraseu(Connection conn, String idDelegatie) {
		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.stergePuncteTraseu());) {

			stmt.setString(1, idDelegatie);

			stmt.executeQuery();

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(ex.toString());
		}
	}

	public List<LatLng> getCoordOpriri(String codDisp, String dataStart, String dataStop) {

		List<LatLng> listCoords = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateOpriri());) {

			stmt.setString(1, codDisp);
			stmt.setString(2, dataStart);
			stmt.setString(3, dataStop);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				listCoords.add(new LatLng(rs.getDouble("lat"), rs.getDouble("lon")));

			}

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(ex.toString());
		}

		return listCoords;
	}

}
