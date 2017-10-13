package flota.service.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.maps.model.LatLng;

import flota.service.beans.BeanDelegatieCauta;
import flota.service.beans.Oprire;
import flota.service.beans.PozitieGps;
import flota.service.beans.PunctTraseu;
import flota.service.beans.Traseu;
import flota.service.database.DBManager;
import flota.service.helpers.HelperDelegatie;
import flota.service.queries.SqlQueries;
import flota.service.utils.DateUtils;
import flota.service.utils.MailOperations;
import flota.service.utils.MapUtils;
import flota.service.utils.Utils;

public class OperatiiTraseu {

	private static final Logger logger = LogManager.getLogger(OperatiiTraseu.class);

	private static final double razaKmSosire = 5;
	private static final int DIST_MIN_STOPS_KM = 4;

	private LatLng coordonatePlecare;
	private LatLng coordonateSosire;
	private String dataPlecare;
	private String dataSosire;

	public List<String> getCoordonateTraseu(String idDelegatie) {

		List<String> listCoords = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateTraseu());) {

			BeanDelegatieCauta delegatie = new OperatiiDelegatii().getDelegatie(conn, idDelegatie);

			String dataPlec = delegatie.getDataPlecare() + " " + delegatie.getOraPlecare().substring(0, 2) + ":" + delegatie.getOraPlecare().substring(2, 4);

			String dataSos = delegatie.getDataSosire() + " " + "23:59";

			stmt.setString(1, idDelegatie);

			stmt.setString(2, dataPlec);
			stmt.setString(3, dataSos);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				listCoords.add(rs.getString("lat") + ":" + rs.getString("lon"));

			}

			rs.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}

		return listCoords;

	}

	public String determinaSfarsitDelegatie(Connection conn, String idDelegatie) {

		double startKm = 0;
		double stopKm = 0;
		String oraSosire = null;

		double lastRecKm = 0;
		String lastRecOra = null;
		double lastLat = 0;
		double lastLon = 0;

		List<PunctTraseu> objPuncte = null;
		BeanDelegatieCauta delegatie = null;

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateTraseu());) {

			delegatie = new OperatiiDelegatii().getDelegatie(conn, idDelegatie);
			delegatie.setId(idDelegatie);

			dataPlecare = delegatie.getDataPlecare() + " " + "00:00";

			dataSosire = delegatie.getDataSosire() + " " + "23:59";

			stmt.setString(1, idDelegatie);

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

				if (distSosire > razaKmSosire)
					startDel = true;

				for (int jj = 1; jj < objPuncte.size() - 1; jj++) {

					double distPunct = MapUtils.distanceXtoY(objPuncte.get(jj).getCoordonate().lat, objPuncte.get(jj).getCoordonate().lng, rs.getDouble("lat"),
							rs.getDouble("lon"), "K");

					if (!objPuncte.get(jj).isVizitat()) {

						if (distPunct < razaKmSosire) {
							objPuncte.get(jj).setVizitat(true);
							break;
						}
					} else {
						if (distPunct > razaKmSosire) {
							objPuncte.get(jj).setParasit(true);

						}
					}

				}

				if (distSosire < razaKmSosire && startDel && isPunctIntermediarParasit(objPuncte)) {
					stopKm = rs.getDouble("km");
					oraSosire = rs.getString("gtime").replace(":", "");
					objPuncte.get(objPuncte.size() - 1).setVizitat(true);
					coordonateSosire = new LatLng(rs.getDouble("lat"), rs.getDouble("lon"));

				} else {
					i++;
				}

				lastRecKm = rs.getDouble("km");
				lastRecOra = rs.getString("gtime").replace(":", "");
				lastLat = rs.getDouble("lat");
				lastLon = rs.getDouble("lon");

			}

			rs.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(e.toString());
		}

		if (stopKm == 0) {
			stopKm = lastRecKm;
			oraSosire = lastRecOra;
			coordonateSosire = new LatLng(lastLat, lastLon);

		}

		if ((stopKm - startKm) > 0) {
			try {
				actualizeazaSfarsitDelegatie(conn, delegatie, oraSosire, stopKm - startKm, objPuncte);
			} catch (SQLException e) {
				MailOperations.sendMail(Utils.getStackTrace(e));
			}
		} else
			HelperDelegatie.setDelegatieNeterm(delegatie.getId());

		return " ";
	}

	private static boolean isPunctIntermediarParasit(List<PunctTraseu> puncteTraseu) {
		PunctTraseu punctSosire = puncteTraseu.get(puncteTraseu.size() - 1);

		List<PunctTraseu> puncteIntermed = puncteTraseu.subList(0, puncteTraseu.size() - 1);

		if (puncteIntermed.contains(punctSosire)) {

			PunctTraseu punct = puncteIntermed.get(puncteIntermed.indexOf(punctSosire));

			if (punct.isVizitat()) {
				return punct.isParasit();
			} else
				return true;

		} else
			return true;

	}

	public void actualizeazaSfarsitDelegatie(Connection conn, BeanDelegatieCauta delegatie, String oraSosire, double distReal, List<PunctTraseu> puncte)
			throws SQLException {

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.setSfarsitDelegatie());) {

			stmt.setString(1, oraSosire);
			stmt.setDouble(2, (int) distReal);
			stmt.setString(3, delegatie.getId());

			stmt.executeQuery();

			try (PreparedStatement stmt1 = conn.prepareStatement(SqlQueries.updatePuncte())) {

				for (PunctTraseu punct : puncte) {

					stmt1.setString(1, punct.isVizitat() ? "1" : "0");
					stmt1.setString(2, delegatie.getId());
					stmt1.setString(3, String.valueOf(punct.getPozitie()));

					stmt1.executeQuery();

				}
			}

			int kmCota = new OperatiiAngajat().getKmCota(conn, delegatie.getAngajatId(), delegatie.getDataPlecare(), delegatie.getDataSosire());

			boolean aprobAutomat = verificaAprobareAutomata(conn, delegatie, distReal, puncte, kmCota);

			if (!aprobAutomat)
				recalculeazaTraseuTeoretic(conn, delegatie, puncte);

		}

	}

	public static boolean verificaAprobareAutomata(Connection conn, BeanDelegatieCauta delegatie, double distReal, List<PunctTraseu> puncte, int kmCota) {

		if (distReal > (delegatie.getDistantaCalculata() + kmCota))
			return false;

		for (int i = 1; i < puncte.size() - 1; i++)
			if (!puncte.get(i).isVizitat())
				return false;

		String statusDel = HelperDelegatie.getStatusDelegatie(conn, delegatie.getId());

		if (statusDel.equals("1")) {
			new OperatiiDelegatii().aprobaAutomatDelegatie(conn, delegatie.getId());
			return true;
		} else
			return false;

	}

	public void recalculeazaTraseuTeoretic(Connection conn, BeanDelegatieCauta delegatie, List<PunctTraseu> puncte) {

		List<LatLng> coordonateOpriri = getCoordOpriri(conn, delegatie.getId(), dataPlecare, dataSosire);
		coordonateOpriri.add(0, coordonatePlecare);
		coordonateOpriri.add(coordonateSosire);

		List<String> adreseOpriri = MapUtils.getAdreseCoordonate(coordonateOpriri);

		int distantaTeoretica = MapUtils.getDistantaTraseuAdrese(adreseOpriri);

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.updateDistantaRecalculata());) {

			stmt.setDouble(1, distantaTeoretica);
			stmt.setString(2, delegatie.getId());

			stmt.executeUpdate();

			if (!adreseOpriri.isEmpty()) {

				int contorOpriri = puncte.size() + 1;

				try (PreparedStatement stmt1 = conn.prepareStatement(SqlQueries.adaugaOpririDelegatie())) {

					for (String adresa : adreseOpriri) {

						if (punctExista(adresa, puncte))
							continue;

						String[] arrayAdresa = adresa.trim().split("/");

						if (arrayAdresa.length == 2) {
							stmt1.setString(1, delegatie.getId());
							stmt1.setString(2, String.valueOf(contorOpriri++));
							stmt1.setString(3, arrayAdresa[1].trim().toUpperCase());
							stmt1.setString(4, arrayAdresa[0].trim().toUpperCase());
							stmt1.setString(5, "1");
							stmt1.setString(6, "0");

							stmt1.executeQuery();
						}

					}

				}

			}

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(Utils.getStackTrace(ex));
		}

	}

	private static boolean punctExista(String punct, List<PunctTraseu> puncte) {

		for (PunctTraseu pTraseu : puncte)
			if (punct.split("/")[0].toLowerCase().contains(pTraseu.getStrAdresa().split("/")[1].toLowerCase().trim()))
				return true;

		return false;

	}

	public List<LatLng> getCoordOpriri(Connection conn, String idDelegatie, String dataStart, String dataStop) {

		List<LatLng> listCoords = new ArrayList<>();

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordonateOpriri());) {

			stmt.setString(1, idDelegatie);
			stmt.setString(2, dataStart);
			stmt.setString(3, dataStop);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			double lastLat = 0;
			double lastLon = 0;
			double distPuncte = 0;

			while (rs.next()) {

				if (lastLat != 0) {
					distPuncte = MapUtils.distanceXtoY(rs.getDouble("lat"), rs.getDouble("lon"), lastLat, lastLon, "K");

					if (distPuncte > DIST_MIN_STOPS_KM || listCoords.isEmpty())
						listCoords.add(new LatLng(rs.getDouble("lat"), rs.getDouble("lon")));
				}

				lastLat = rs.getDouble("lat");
				lastLon = rs.getDouble("lon");

			}

			rs.close();

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(ex.toString());
		}

		return listCoords;
	}

	public Traseu getTraseu(String codAngajat, String dataStart, String dataStop, String nrMasina) {

		Traseu traseu = new Traseu();

		List<LatLng> coordonateTraseu = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getCoordRuta());) {

			stmt.setString(1, nrMasina);
			stmt.setString(2, dataStart);
			stmt.setString(3, dataStop);

			stmt.executeQuery();

			int kmStart = 0;
			int kmStop = 0;
			int speed = 0;
			int avgSpeed = 0;
			int distanta = 0;
			int maxSpeed = 0;
			int i = 0;
			int instantSpeed = 0;

			List<Oprire> listOpriri = new ArrayList<Oprire>();
			Oprire oprire = null;

			Date dataStartOprire = null, dataStopOprire = null, ultimaInreg = null;

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				if (i == 0)
					kmStart = rs.getInt("km");

				kmStop = rs.getInt("km");

				instantSpeed = rs.getInt("speed");

				if (instantSpeed > maxSpeed)
					maxSpeed = rs.getInt("speed");

				if (0 == instantSpeed) {
					if (oprire == null) {
						oprire = new Oprire();
						oprire.setPozitieGps(new PozitieGps(null, rs.getDouble("lat"), rs.getDouble("lon")));
						dataStartOprire = DateUtils.getDate(rs.getString("gtime"));

						oprire.setData(rs.getString("gtime"));

					}

				} else {
					if (dataStartOprire != null) {
						dataStopOprire = DateUtils.getDate(rs.getString("gtime"));

						String durataOprire = DateUtils.dateDiff(dataStartOprire, dataStopOprire);

						if (!durataOprire.trim().isEmpty()) {
							oprire.setDurata(DateUtils.dateDiff(dataStartOprire, dataStopOprire));
							listOpriri.add(oprire);
						}

						oprire = null;
						dataStartOprire = null;
					}
				}

				i++;

				coordonateTraseu.add(new LatLng(rs.getDouble("lat"), rs.getDouble("lon")));

			}

			if (dataStartOprire != null) {
				oprire.setDurata(DateUtils.dateDiff(dataStartOprire, ultimaInreg));
				listOpriri.add(oprire);
			}

			traseu.setCoordonate(coordonateTraseu);
			traseu.setOpriri(listOpriri);
			traseu.setDistanta(kmStop - kmStart);

			rs.close();

		} catch (SQLException ex) {
			logger.error(Utils.getStackTrace(ex));
			MailOperations.sendMail(ex.toString());
		}

		return traseu;

	}

}
