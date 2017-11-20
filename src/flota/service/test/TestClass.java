package flota.service.test;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.model.OperatiiTraseu;

public class TestClass {

	private static final Logger logger = LogManager.getLogger(TestClass.class);

	public static void main(String[] args) throws SQLException {

		// System.out.println(new
		// OperatiiTraseu().getCoordonateTraseu("17651549879"));

		// System.out.println(new
		// OperatiiDelegatii().getDelegatiiAprobari("DIT", "BU90, GL90", "00"));

		new OperatiiTraseu().determinaSfarsitDelegatie(new DBManager().getProdDataSource().getConnection(), "24396485714");

		// new OperatiiDelegatii().verificaDelegatiiTerminateCompanie();

		// System.out.println(new
		// OperatiiDelegatii().getDelegatiiAprobari("SMG",
		// "PH10", "11"));

		// System.out.println(new
		// OperatiiDelegatii().afiseazaDelegatiiProprii("00002415",
		// "01-07-2017", "11-07-2017"));

		// 721434339, 12-07-2017 07:00, 12-07-2017 23:59

		// System.out.println(DateUtils.formatDateFromSap("20170712"));

		// List<LatLng> listCoords = new
		// OperatiiTraseu().getCoordOpriri("721434339", "12-07-2017 07:00",
		// "12-07-2017 23:59");

		// System.out.println(MapUtils.getAdreseCoordonate(listCoords));

		// int dist = MapUtils.getDistantaTraseu(listCoords);
		// System.out.println("dist = " + dist);

		// new
		// OperatiiTraseu().determinaSfarsitDelegatie(DBManager.getProdInstance().getConnection(),
		// "17400880129");

		// System.out.println(new OperatiiMasina().getMasiniAngajat( "00083307",
		// "14-08-2017"));

		// System.out.println(new OperatiiTraseu().getTraseu("00083315",
		// "27-09-2017", "27-09-2017", "B-68-VIZ"));

		// System.out.println(new
		// OperatiiDelegatii().afiseazaDelegatiiSubord("01-10-2017",
		// "17-10-2017", "DZ", "GL10", "02"));

		// System.out.println(HelperAprobare.getCodAprobare(new
		// DBManager().getProdDataSource().getConnection(),"00086230", "KA1"));

		// new AlertaMail().verificaAlertWeekend("22147680545");

		// System.out.println(HelperAprobare.getCodAprobareConsilieri(DBManager.getTestDataSource().getConnection(),
		// "00074439", "CAG2"));

		// System.out.println(HelperAprobare.getCodAprobare(new
		// DBManager().getProdDataSource().getConnection(), "00077313",
		// "CONS_GED"));

		// new OperatiiDelegatii().genereazaDelegatie("00083306", "17-10-2017",
		// "17-10-2017");

	}

}
