package flota.service.queries;

public class SqlQueries {
	public static String getLocalitatiJudet() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select upper(localitate) localitate from sapprd.zlocalitati where bland=? order by localitate");

		return sqlString.toString();
	}

	public static String adaugaAntetDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" insert into sapprd.zdelegatiehead(mandt, id, codangajat, datac, orac, data_plecare, ora_plecare, distcalc, distaprob, idaprob, data_sosire, nrAuto) ");
		sqlString.append(" values ('900',?,?,?,?,?,?,?,0,?,?,?) ");

		return sqlString.toString();
	}

	public static String adaugaOpririDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" insert into sapprd.zdelegatieruta(mandt, id, poz, codjudet, localitate) ");
		sqlString.append(" values ('900',?,?,?,?) ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareHeader() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distaprob, h.data_sosire ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where ");
		sqlString.append(" h.idaprob = (select idaprob from sapprd.zdeltipaprob where tipaprob=?) ");
		sqlString.append(" and ag.filiala =? and h.codangajat = ag.cod ");
		sqlString.append(" and not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('1','6')) ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String afiseazaDelegatii() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distaprob, h.data_sosire ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where h.codangajat = ag.cod and ");
		sqlString.append(" h.codangajat = ? ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareRuta() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select r.codjudet, r.localitate from sapprd.zdelegatieruta r where r.id = ?  order by r.poz  ");
		return sqlString.toString();
	}

	public static String opereazaDelegatie() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" insert into sapprd.zdelstataprob (mandt, iddelegatie, tipaprob, status, dataaprob, oraaprob, codAngajat) values ('900',?,?,?,?,?,?) ");
		return sqlString.toString();
	}

	public static String aprobaKm() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append("update sapprd.zdelegatiehead set distaprob=? where id = ?");
		return sqlString.toString();

	}

	public static String getCoordonateTraseu() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select x.* from (select rownum idt, to_char(gtime,'dd-mm-yyyy HH24:mi') gtime, lat, lon, speed from nexus_gps_data ");
		sqlString.append(" where vcode=? and ");
		sqlString.append(" gtime between to_date(?,'dd-mm-yyyy HH24:mi') and to_date(?,'dd-mm-yyyy HH24:mi') ) x where remainder(x.idt,3) = 0 order by x.gtime ");

		return sqlString.toString();
	}

}
