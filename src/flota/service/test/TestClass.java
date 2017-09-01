package flota.service.test;

import java.sql.SQLException;

import flota.service.model.OperatiiDelegatii;

public class TestClass {

	public static void main(String[] args) throws SQLException {

		// System.out.println(new
		// OperatiiTraseu().getCoordonateTraseu("17651549879"));

		// new OperatiiDelegatii().getDelegatiiAprobari("DD", "BU90","06");

		// new
		// OperatiiTraseu().determinaSfarsitDelegatie(DBManager.getProdInstance().getConnection(),
		// "17651549879");

		// System.out.println(new OperatiiDelegatii().getDelegatiiAprobari("SD",
		// "NT10", "04"));

		// System.out.println(new
		// OperatiiDelegatii().afiseazaDelegatiiProprii("00002415", "01-07-2017"
		// , "11-07-2017"));

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

		// System.out.println(new OperatiiTraseu().getTraseu("00083045",
		// "13-08-2017", "13-08-2017"));

		//System.out.println(new OperatiiAngajat().getAngajati("DZ", "DJ10", "01"));

		 System.out.println(new OperatiiDelegatii().afiseazaDelegatiiSubord("01-08-2017",
		 "01-09-2017", "SDKA", "BV10", "00"));
		
		
		
		//System.out.println(new OperatiiDelegatii().getDelegatiiModificare("00002185"));

	}

}
