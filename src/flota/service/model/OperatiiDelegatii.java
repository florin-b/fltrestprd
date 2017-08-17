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

import flota.service.beans.BeanDelegatieAprobare;
import flota.service.beans.BeanDelegatieCauta;
import flota.service.beans.DelegatieModifAntet;
import flota.service.beans.DelegatieModifDetalii;
import flota.service.beans.PunctTraseu;
import flota.service.beans.PunctTraseuLite;
import flota.service.database.DBManager;

import flota.service.helpers.HelperDelegatie;
import flota.service.queries.SqlQueries;
import flota.service.utils.DateUtils;
import flota.service.utils.MailOperations;
import flota.service.utils.MapUtils;
import flota.service.utils.Utils;
import flota.service.utils.UtilsAddress;

public class OperatiiDelegatii {

	private static final Logger logger = LogManager.getLogger(OperatiiDelegatii.class);

	public boolean adaugaDelegatie(String codAngajat, String tipAngajat, String dataPlecare, String oraPlecare, String distanta, String opriri,
			String dataSosire, String nrAuto) {

		boolean success = true;

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.adaugaAntetDelegatie(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			String idDelegatieNoua = Utils.getId();

			stmt.setString(1, idDelegatieNoua);
			stmt.setString(2, codAngajat);
			stmt.setString(3, DateUtils.getCurrentDate());
			stmt.setString(4, DateUtils.getCurrentTime());
			stmt.setString(5, DateUtils.formatDateSap(dataPlecare));
			stmt.setString(6, oraPlecare);
			stmt.setDouble(7, (int) Double.parseDouble(distanta));
			stmt.setString(8, codAngajat);
			stmt.setString(9, DateUtils.formatDateSap(dataSosire));
			stmt.setString(10, nrAuto);

			stmt.executeQuery();

			String[] arrayOpriri = opriri.split(",");

			PreparedStatement stmt1 = null;
			int ord = 0;

			for (int i = 0; i < arrayOpriri.length; i++) {
				stmt1 = conn.prepareStatement(SqlQueries.adaugaOpririDelegatie(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				String[] arrayAdresa = arrayOpriri[i].trim().split("/");
				stmt1.setString(1, idDelegatieNoua);

				ord++;
				if (i == arrayOpriri.length - 1)
					ord = 100;

				stmt1.setString(2, String.valueOf(ord));
				stmt1.setString(3, arrayAdresa[1].trim());
				stmt1.setString(4, arrayAdresa[0].trim());
				stmt1.setString(5, "0");

				stmt1.executeQuery();

			}

			if (stmt1 != null)
				stmt1.close();

		} catch (SQLException e) {
			logger.error("Opriri: " + opriri + Utils.getStackTrace(e));
			MailOperations.sendMail(Utils.getStackTrace(e));
			success = false;

		}

		return success;

	}

	public List<BeanDelegatieAprobare> getDelegatiiAprobari(String tipAngajat, String unitLog, String codDepart) {

		boolean isPersVanzari = Utils.isAngajatVanzari(tipAngajat);

		verificaDelegatiiTerminate(tipAngajat, unitLog, codDepart, isPersVanzari);

		List<BeanDelegatieAprobare> listDelegatii = new ArrayList<>();

		String sqlString;

		if (isPersVanzari)
			sqlString = SqlQueries.getDelegatiiAprobareHeaderVanzari();
		else
			sqlString = SqlQueries.getDelegatiiAprobareHeaderNONVanzari();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, tipAngajat);
			stmt.setString(2, unitLog);

			if (isPersVanzari) {
				stmt.setString(3, codDepart);
			}

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			OperatiiAngajat opAngajat = new OperatiiAngajat();

			while (rs.next()) {

				double distReal = rs.getDouble("distreal");
				String statusDel = HelperDelegatie.getStatusDelegatie(conn, rs.getString("id"));

				if (statusDel.equals("6"))
					continue;

				if (distReal == 0) {
					if (statusDel.equals("1"))
						continue;
				} else {
					if (statusDel.equals("2"))
						continue;
				}

				BeanDelegatieAprobare delegatie = new BeanDelegatieAprobare();
				delegatie.setId(rs.getString("id"));
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));
				delegatie.setOraPlecare(DateUtils.formatTime(rs.getString("ora_plecare")));
				delegatie.setNumeAngajat(rs.getString("nume"));
				delegatie.setListOpriri(getOpriri(conn, delegatie.getId()));

				int kmCota = opAngajat.getKmCota(conn, rs.getString("codAngajat"), delegatie.getDataPlecare(), delegatie.getDataSosire());

				delegatie.setDistantaCalculata((int) rs.getDouble("distcalc") + kmCota);
				delegatie.setDistantaRespinsa((int) rs.getDouble("distrespins"));
				delegatie.setDistantaEfectuata((int) rs.getDouble("distreal"));

				delegatie.setMsgAtentionare(HelperDelegatie.getAtentionare(delegatie));
				listDelegatii.add(delegatie);
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listDelegatii;

	}

	public void aprobaDelegatie(String idDelegatie, String tipAngajat, String kmRespinsi, String codAngajat, String tipAprobare) {
		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.opereazaDelegatie(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			int dKmRespinsi = Integer.parseInt(kmRespinsi);

			stmt.setString(1, idDelegatie);
			stmt.setString(2, tipAngajat);
			stmt.setString(3, getCodAprobare(tipAprobare));
			stmt.setString(4, DateUtils.getCurrentDate());
			stmt.setString(5, DateUtils.getCurrentTime());
			stmt.setString(6, codAngajat);

			stmt.executeQuery();

			if (dKmRespinsi > 0) {
				PreparedStatement stmt1 = conn.prepareStatement(SqlQueries.setKmRespinsi(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt1.setDouble(1, (int) dKmRespinsi);
				stmt1.setString(2, idDelegatie);

				stmt1.executeQuery();

				stmt1.close();
			}

		} catch (Exception ex) {
			logger.error(Utils.getStackTrace(ex));
		}

	}

	public void aprobaAutomatDelegatie(Connection conn, String idDelegatie) {
		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.aprobaAutomatDelegatie());) {

			stmt.setString(1, idDelegatie);
			stmt.setString(2, DateUtils.getCurrentDate());
			stmt.setString(3, DateUtils.getCurrentTime());

			stmt.executeQuery();

		} catch (Exception ex) {
			logger.error(Utils.getStackTrace(ex));
		}

	}

	public void respingeDelegatie(String idDelegatie, String tipAngajat, String codAngajat) {

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.opereazaDelegatie(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, idDelegatie);
			stmt.setString(2, tipAngajat);
			stmt.setString(3, "6");
			stmt.setString(4, DateUtils.getCurrentDate());
			stmt.setString(5, DateUtils.getCurrentTime());
			stmt.setString(6, codAngajat);

			stmt.executeQuery();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			logger.error(Utils.getStackTrace(ex));
		}

	}

	public List<String> getLocOpriri(Connection conn, String idDelegatie) {
		List<String> listLocalitati = new ArrayList<>();

		try {
			PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelegatiiAprobareRuta(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, idDelegatie);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				listLocalitati.add(rs.getString("judet") + " / " + rs.getString("localitate"));
			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listLocalitati;
	}

	public List<PunctTraseu> getPuncteTraseu(Connection conn, String idDelegatie) {
		List<PunctTraseu> listLocalitati = new ArrayList<>();

		try {
			PreparedStatement stmt = conn.prepareStatement(SqlQueries.getPuncteTraseu());

			stmt.setString(1, idDelegatie);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				PunctTraseu punct = new PunctTraseu();
				punct.setStrAdresa(rs.getString("judet") + " / " + rs.getString("localitate"));
				punct.setPozitie(Integer.valueOf(rs.getString("poz")));

				if (!rs.getString("lat").equals("-1")) {
					punct.setCoordonate(new LatLng(Double.valueOf(rs.getString("lat")), Double.valueOf(rs.getString("lon"))));
				} else {

					LatLng coordPunct = MapUtils.geocodeAddress(UtilsAddress.getAddress(punct.getStrAdresa()));
					punct.setCoordonate(new LatLng(coordPunct.lat, coordPunct.lng));
					salveazaCoordonatePunct(conn, punct.getStrAdresa(), coordPunct);

				}

				listLocalitati.add(punct);

			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listLocalitati;
	}

	private void salveazaCoordonatePunct(Connection conn, String adresa, LatLng coordonate) {

		try {
			PreparedStatement stmt = conn.prepareStatement(SqlQueries.adaugaCoordonate());

			stmt.setString(1, adresa.split("/")[0]);
			stmt.setString(2, adresa.split("/")[1]);
			stmt.setString(3, String.valueOf(coordonate.lat));
			stmt.setString(4, String.valueOf(coordonate.lng));

			stmt.executeQuery();

			stmt.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

	}

	public List<PunctTraseuLite> getOpriri(Connection conn, String idDelegatie) {

		List<PunctTraseuLite> listOpriri = new ArrayList<>();

		try {
			PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelegatiiAprobareRuta(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, idDelegatie);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				PunctTraseuLite punct = new PunctTraseuLite();
				punct.setAdresa(rs.getString("judet") + " / " + rs.getString("localitate"));
				punct.setVizitat(rs.getString("vizitat").equals("1") ? true : false);

				listOpriri.add(punct);

			}

			rs.close();
			stmt.close();

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listOpriri;
	}

	public List<BeanDelegatieAprobare> afiseazaDelegatiiProprii(String codAngajat, String dataStart, String dataStop) {

		List<BeanDelegatieAprobare> listDelegatii = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.afiseazaDelegatiiProprii(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codAngajat);
			stmt.setString(2, DateUtils.formatDateSap(dataStart));
			stmt.setString(3, DateUtils.formatDateSap(dataStop));
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			OperatiiAngajat opAngajat = new OperatiiAngajat();

			while (rs.next()) {
				BeanDelegatieAprobare delegatie = new BeanDelegatieAprobare();
				delegatie.setId(rs.getString("id"));
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));
				delegatie.setOraPlecare(DateUtils.formatTime(rs.getString("ora_plecare")));
				delegatie.setNumeAngajat(" ");
				delegatie.setListOpriri(getOpriri(conn, delegatie.getId()));

				int kmCota = opAngajat.getKmCota(conn, rs.getString("codAngajat"), delegatie.getDataPlecare(), delegatie.getDataSosire());

				delegatie.setDistantaCalculata((int) rs.getDouble("distcalc") + kmCota);

				delegatie.setDistantaRespinsa((int) rs.getDouble("distrespins"));
				delegatie.setDistantaEfectuata((int) rs.getDouble("distreal"));
				delegatie.setStatusCode(rs.getString("status"));
				listDelegatii.add(delegatie);
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listDelegatii;

	}

	public List<BeanDelegatieAprobare> afiseazaDelegatiiSubord(String dataStart, String dataStop, String tipAngajat, String unitLog, String codDepart) {

		List<BeanDelegatieAprobare> listDelegatii = new ArrayList<>();

		boolean isPersVanzari = Utils.isAngajatVanzari(tipAngajat);

		String sqlString = "";

		if (isPersVanzari)
			sqlString = SqlQueries.afiseazaDelegatiiSubordVanzari();
		else
			sqlString = SqlQueries.afiseazaDelegatiiSubordNONVanzari();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			if (isPersVanzari) {
				stmt.setString(1, unitLog);
				stmt.setString(2, codDepart);
				stmt.setString(3, DateUtils.formatDateSap(dataStart));
				stmt.setString(4, DateUtils.formatDateSap(dataStop));
			} else {
				stmt.setString(1, tipAngajat);
				stmt.setString(2, unitLog);
				stmt.setString(3, DateUtils.formatDateSap(dataStart));
				stmt.setString(4, DateUtils.formatDateSap(dataStop));
			}

			stmt.executeQuery();

			OperatiiAngajat opAngajat = new OperatiiAngajat();
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				BeanDelegatieAprobare delegatie = new BeanDelegatieAprobare();
				delegatie.setId(rs.getString("id"));
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));
				delegatie.setOraPlecare(DateUtils.formatTime(rs.getString("ora_plecare")));
				delegatie.setNumeAngajat(rs.getString("nume"));
				delegatie.setListOpriri(getOpriri(conn, delegatie.getId()));

				int kmCota = opAngajat.getKmCota(conn, rs.getString("codAngajat"), delegatie.getDataPlecare(), delegatie.getDataSosire());

				delegatie.setDistantaCalculata((int) rs.getDouble("distcalc") + kmCota);

				delegatie.setDistantaRespinsa((int) rs.getDouble("distrespins"));
				delegatie.setDistantaEfectuata((int) rs.getDouble("distreal"));
				delegatie.setStatusCode(HelperDelegatie.getStatusDelegatie(conn, delegatie.getId()));

				listDelegatii.add(delegatie);
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listDelegatii;

	}

	public BeanDelegatieCauta getDelegatie(Connection conn, String idDelegatie) {

		BeanDelegatieCauta delegatie = new BeanDelegatieCauta();

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelegatieCauta(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, idDelegatie);
			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setOraPlecare(rs.getString("ora_plecare"));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));
				delegatie.setDistantaCalculata((int) rs.getDouble("distcalc"));
				delegatie.setAngajatId(rs.getString("codangajat"));
			}

		}

		catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return delegatie;

	}

	private static String getCodAprobare(String codAprobare) {
		if (codAprobare.equals("0"))
			return "1";

		return "2";
	}

	public void verificaDelegatiiTerminate(String tipAngajat, String unitLog, String depart, boolean isPersVanzari) {

		String sqlString;

		if (isPersVanzari)
			sqlString = SqlQueries.getDelegatiiTerminateVanzari();
		else
			sqlString = SqlQueries.getDelegatiiTerminateNONVanzari();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			if (isPersVanzari) {
				stmt.setString(1, tipAngajat);
				stmt.setString(2, unitLog);
				stmt.setString(3, depart);
			} else {
				stmt.setString(1, tipAngajat);
				stmt.setString(2, unitLog);
			}

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			OperatiiTraseu opTraseu = new OperatiiTraseu();

			while (rs.next()) {
				opTraseu.determinaSfarsitDelegatie(conn, rs.getString("id"));

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
			MailOperations.sendMail(Utils.getStackTrace(e));
		}

		return;

	}

	public List<DelegatieModifAntet> getDelegatiiModificare(String codAngajat) {

		List<DelegatieModifAntet> listDelegatii = new ArrayList<>();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelModifHeader(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, codAngajat);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				DelegatieModifAntet delegatie = new DelegatieModifAntet();

				delegatie.setId(rs.getString("id"));
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));

				String startStopD = getStartStopDelegatie(conn, delegatie.getId());

				delegatie.setLocalitateStart(startStopD.split("#")[0]);
				delegatie.setLocalitateStop(startStopD.split("#")[1]);
				listDelegatii.add(delegatie);

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return listDelegatii;

	}

	private String getStartStopDelegatie(Connection conn, String idDelegatie) {

		String startLoc = "";
		String stopLoc = "";

		try (PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelModifStartStop());) {

			stmt.setString(1, idDelegatie);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			int i = 0;
			while (rs.next()) {

				if (i == 0)
					startLoc = rs.getString("punct");

				stopLoc = rs.getString("punct");

				i++;

			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return startLoc + "#" + stopLoc;

	}

	public DelegatieModifDetalii getDelegatieModif(String delegatieId) {

		DelegatieModifDetalii delegatie = new DelegatieModifDetalii();

		try (Connection conn = DBManager.getProdInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlQueries.getDelegatieModif(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, delegatieId);

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {

				delegatie.setNrAuto(rs.getString("nrauto"));
				delegatie.setDataPlecare(DateUtils.formatDateFromSap(rs.getString("data_plecare")));
				delegatie.setOraPlecare(rs.getString("ora_plecare"));
				delegatie.setDataSosire(DateUtils.formatDateFromSap(rs.getString("data_sosire")));

				List<PunctTraseuLite> ruta = getOpriri(conn, delegatieId);

				delegatie.setLocPlecare(ruta.get(0).getAdresa());
				delegatie.setLocSosire(ruta.get(ruta.size() - 1).getAdresa());

				ruta.remove(0);
				ruta.remove(ruta.size() - 1);

				List<String> puncte = new ArrayList<>();

				for (PunctTraseuLite punct : ruta)
					puncte.add(punct.getAdresa());

				delegatie.setRuta(puncte);
			}

		} catch (SQLException e) {
			logger.error(Utils.getStackTrace(e));
		}

		return delegatie;

	}

}
