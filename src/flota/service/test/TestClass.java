package flota.service.test;

import java.sql.SQLException;

import flota.service.database.DBManager;
import flota.service.model.OperatiiTraseu;

public class TestClass {

	public static void main(String[] args) throws SQLException {

		// new OperatiiTraseu().getCoordonateTraseu("15256796182");

		// new OperatiiDelegatii().getDelegatiiAprobari("SD", "GL10");

		 
		 
		 new OperatiiTraseu().determinaSfarsitDelegatie(DBManager.getProdInstance().getConnection(), "19662236306");

		//System.out.println(new OperatiiDelegatii().getDelegatiiAprobari("SD", "GL10", "02"));

		// System.out.println(new
		// OperatiiDelegatii().afiseazaDelegatiiProprii("00002415", "01-07-2017"
		// , "11-07-2017"));

		// 721434339, 12-07-2017 07:00, 12-07-2017 23:59

		// System.out.println(DateUtils.formatDateFromSap("20170712"));

		//List<LatLng> listCoords = new OperatiiTraseu().getCoordOpriri("721434339", "12-07-2017 07:00", "12-07-2017 23:59");

		//System.out.println(MapUtils.getAdreseCoordonate(listCoords));

		//int dist = MapUtils.getDistantaTraseu(listCoords);
		//System.out.println("dist = " + dist);
		
		
		
		//new OperatiiTraseu().determinaSfarsitDelegatie(DBManager.getProdInstance().getConnection(), "17400880129");
					
		
		//System.out.println(new OperatiiMasina().getMasiniAngajat( "00083307", "14-08-2017"));
		
		
		//System.out.println(new OperatiiTraseu().getTraseu("00083045", "13-08-2017", "13-08-2017"));
		
		
		

	}

}
