package flota.service.queries;

public class SqlQueries {
	public static String getLocalitatiJudet() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select upper(localitate) localitate from sapprd.zlocalitati where bland=? order by localitate");

		return sqlString.toString();
	}

	public static String adaugaAntetDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" insert into sapprd.zdelegatiehead(mandt, id, codangajat, datac, orac, data_plecare, ora_plecare, distcalc, distrespins, idaprob, data_sosire, nrAuto) ");
		sqlString.append(" values ('900',?,?,?,?,?,?,?,0,?,?,?) ");

		return sqlString.toString();
	}

	public static String adaugaOpririDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" insert into sapprd.zdelegatieruta(mandt, id, poz, codjudet, localitate, vizitat) ");
		sqlString.append(" values ('900',?,?,?,?,'0') ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareHeader() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where ");
		sqlString.append(" h.idaprob = (select idaprob from sapprd.zdeltipaprob where tipaprob=?) ");
		sqlString.append(" and ag.filiala =? and h.codangajat = ag.cod ");
		sqlString.append(" and (not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('1','2','6')) ");
		sqlString.append("  ) ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatiiTerminate() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where ");
		sqlString.append(" h.idaprob = (select idaprob from sapprd.zdeltipaprob where tipaprob=?) ");
		sqlString.append(" and ag.filiala =? and h.codangajat = ag.cod ");
		sqlString.append(" and not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('1','2','6') ");
		sqlString.append(" and h.data_sosire < to_date(sysdate,'dd-mm-yyyy') ) ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();

	}

	public static String afiseazaDelegatii() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal, ");
		sqlString.append(" nvl((select status from sapprd.zdelstataprob where iddelegatie = h.id and rownum=1),'-1') status ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where h.codangajat = ag.cod and ");
		sqlString.append(" h.codangajat = ? and h.datac between ? and ? ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareRuta() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select r.codjudet, r.localitate, r.vizitat from sapprd.zdelegatieruta r where r.id = ?  order by r.poz  ");
		return sqlString.toString();
	}

	public static String opereazaDelegatie() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" insert into sapprd.zdelstataprob (mandt, iddelegatie, tipaprob, status, dataaprob, oraaprob, codAngajat) values ('900',?,?,?,?,?,?) ");
		return sqlString.toString();
	}

	public static String setKmRespinsi() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append("update sapprd.zdelegatiehead set distrespins=? where id = ?");
		return sqlString.toString();

	}

	public static String getCoordonateTraseu() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select x.* from (select rownum idt, to_char(gtime,'HH24:mi') gtime, lat, lon, speed, km from nexus_gps_data ");
		sqlString.append(" where vcode=? and ");
		sqlString.append(" gtime between to_date(?,'dd-mm-yyyy HH24:mi') and to_date(?,'dd-mm-yyyy HH24:mi') ) x where remainder(x.idt,3) = 0 order by x.gtime ");

		return sqlString.toString();
	}

	public static String getCodDispGps() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select vcode from nexus_vehicles where trim(regexp_replace(car_number,'-| ','')) =? ");
		return sqlString.toString();

	}

	public static String getNrAuto() {

		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select nrauto from sapprd.zdelegatiehead where id =? ");
		return sqlString.toString();

	}

	public static String getDelegatieCauta() {

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select data_plecare, ora_plecare, data_sosire from sapprd.zdelegatiehead where id = ?");
		return sqlString.toString();

	}

	public static String setSfarsitDelegatie() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" update sapprd.zdelegatiehead set ora_sosire=?, distreal=? where id=? ");
		return sqlString.toString();
	}

	public static String updatePuncte() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" update sapprd.zdelegatieruta set vizitat = ? where id = ? and poz=? ");
		return sqlString.toString();

	}

	public static String getKmCota() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select cotakm from sapprd.pa9001 where mandt='900' ");
		sqlString.append(" and pernr =? and begda <=? and endda >=? ");

		return sqlString.toString();
	}

}
