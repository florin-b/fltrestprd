package flota.service.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import flota.service.database.DBManager;
import flota.service.helpers.HelperAprobare;
import flota.service.model.OperatiiAngajat;
import flota.service.model.OperatiiDelegatii;
import flota.service.model.ServiceDelegatii;

public class TestClass {

	private static final Logger logger = LogManager.getLogger(TestClass.class);

	public static void main(String[] args) throws SQLException {

		 new OperatiiDelegatii().recalculeazaDelegatie("39526194887");

		//new ServiceDelegatii().calculeazaKmSfarsitLuna();
		 
		 
		//new OperatiiDelegatii().getDelegatiiAprobari("DZ", "AG10", "11");
		 
		 int kmcota = new OperatiiAngajat().getKmCota(new DBManager().getProdDataSource().getConnection(), "00002143", "04-04-2018", "04-04-2018");
		 
		 System.out.println("km cota : " + kmcota);
		

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

}
