package flota.service.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.maps.model.LatLng;

import flota.service.database.DBManager;
import flota.service.model.OperatiiDelegatii;
import flota.service.model.OperatiiTraseu;
import flota.service.utils.MapUtils;

public class TestClass {

	private static final Logger logger = LogManager.getLogger(TestClass.class);

	public static void main(String[] args) throws SQLException {

		// new OperatiiDelegatii().recalculeazaDelegatie("63973643174");

		//System.out.println(new OperatiiAngajat().getAngajatCategorie("MS10",
		//		" ARC;ATR;AV;CAG;CAG1;CAG2;CAG3;CJ;CONS-GED;CVG;CVR;CVW;DADMIN;DZ;GD;GS;IHR;IOFR08;IOFR09;ISSM;KA;KA08;KA1;KA1;KA2;KA3;MAC;MM;MMPROD;OC;OIVPD;RGEST;SBA;SBAL;SBL;SD;SDCVA;SDKA;SHR;SM;SMG;SMR;SMW;SOF;SSFC;SSPROD;STIV; ",
		//		""));

		// new OperatiiDelegatii().getDelegatiiAprobari("DD" , "BU90" , "08");

		// new ServiceDelegatii().calculeazaKmSfarsitLuna();
				
		//	new TestClass().accident();
		
		
		
		
				

	}

	private static void recalculeazaDelegatii() {

		String sqlString = "select id, distcalc, distreal, distreal/distcalc from sapprd.zdelegatiehead "
				+ " where data_sosire >= '20171120' and data_sosire < '20171128' and distcalc > 0 and distreal/distcalc >=0.7 and "
				+ " distreal/distcalc <=0.9 and rownum<300 ";

		try (Connection conn = new DBManager().getProdDataSource().getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlString)) {

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			OperatiiDelegatii opDelegatii = new OperatiiDelegatii();

			while (rs.next()) {
				System.out.println("Calculare delegatie " + rs.getString("id"));

				opDelegatii.recalculeazaDelegatie(rs.getString("id"));
			}

		} catch (SQLException e) {

			System.out.println(e);
		}

	}

	private void accident() {

		String sqlString = "   select * from nexus_gps_data where trunc(gtime) ='06-02-2019' and " +        
						   " vcode in (select  v.vcode " + 
						   " from sapprd.anlz a join sapprd.anla b on b.anln1 = a.anln1 and b.anln2 = a.anln2 and b.mandt=a.mandt " +  
						   " join sapprd.aufk c on c.aufnr = a.caufn and c.mandt=a.mandt " + 
						   " join nexus_vehicles v on replace(replace(v.car_number,' ',''),'-','') = replace(replace(c.ktext,' ',''),'-','') " + 
						   " where a.pernr in " +  
						   " (select cod from personal where filiala like 'BV%') " + 
						   " and a.bdatu >= (select to_char(sysdate-5,'YYYYMMDD') from dual) and " +  
						   " b.deakt = '00000000' and a.mandt='900' and c.auart = '2001' ) ";

		Set<String> setCodes = new TreeSet<>();  
		
		List<String> vCodes = new ArrayList<>();

		try (Connection conn = new DBManager().getProdDataSource().getConnection(); PreparedStatement stmt = conn.prepareStatement(sqlString)) {

			stmt.executeQuery();

			ResultSet rs = stmt.getResultSet();

			LatLng strada = new LatLng(45.663970, 25.601354);
			
			while (rs.next()) {
				
				
				LatLng punct = new LatLng(Double.valueOf(rs.getString("lat")), Double.valueOf(rs.getString("lon")));
				
				
				double distStrada = MapUtils.distanceXtoY(punct.lat, punct.lng, strada.lat, strada.lng, "K");
				
				
				if (distStrada < 0.1)
					setCodes.add(rs.getString("vcode"));

				
				
			}

			
			System.out.println(setCodes);
			
		} catch (SQLException e) {

			System.out.println(e);
		}

	}

}
