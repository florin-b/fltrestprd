package flota.service.queries;

public class SqlQueries {
	public static String getLocalitatiJudet() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select upper(localitate) localitate from sapprd.zlocalitati where bland=? order by localitate");

		return sqlString.toString();
	}

	public static String getListLocalitati() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append("select upper(localitate) localitate, bland from sapprd.zlocalitati where lower(localitate) like '?%' order by localitate");

		return sqlString.toString();
	}

	public static String adaugaAntetDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(
				" insert into sapprd.zdelegatiehead(mandt, id, codangajat, datac, orac, data_plecare, ora_plecare, distcalc, distrespins, idaprob, data_sosire, nrAuto) ");
		sqlString.append(
				" values ('900',?,?,?,?,?,?,?,0,(select nvl(f.fid,'-1') from personal p, functii_non_vanzari f where p.cod =? and p.functie = f.cod),?,?) ");

		return sqlString.toString();
	}

	public static String adaugaOpririDelegatie() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" insert into sapprd.zdelegatieruta(mandt, id, poz, judet, localitate, vizitat) ");
		sqlString.append(" values ('900',?,?,?,?,?) ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareHeaderVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id, h.distreal, h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where ");
		sqlString.append(" h.idaprob = (select fid from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and ag.filiala =? and ag.divizie = ? and h.codangajat = ag.cod ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareHeaderNONVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id, h.distreal, h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal ");
		sqlString.append(" from sapprd.zdelegatiehead h, personal ag where ");
		sqlString.append(" h.idaprob in (select fid from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and ag.filiala =? and h.codangajat = ag.cod ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatiiTerminateVanzari() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id ");
		sqlString.append(" from sapprd.zdelegatiehead h, agenti ag where ");
		sqlString.append(" h.idaprob in (select fid from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and ag.filiala =? and ag.divizie=? and h.codangajat = ag.cod ");
		sqlString.append(" and not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('2','6')) ");
		sqlString.append(" and h.data_sosire < to_char(sysdate,'yyyymmdd') and distreal = 0  ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();

	}

	public static String getDelegatiiTerminateNONVanzari() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id ");
		sqlString.append(" from sapprd.zdelegatiehead h, personal ag where ");
		sqlString.append(" h.idaprob in (select fid from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and ag.filiala =? and h.codangajat = ag.cod ");
		sqlString.append(" and not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('2','6')) ");
		sqlString.append(" and h.data_sosire < to_char(sysdate,'yyyymmdd') and distreal = 0   ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();

	}

	public static String afiseazaDelegatiiProprii() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, h.distcalc, h.distrespins, h.data_sosire, h.distreal, ");
		sqlString.append(" nvl((select status from sapprd.zdelstataprob where iddelegatie = h.id and rownum=1),'-1') status ");
		sqlString.append(" from sapprd.zdelegatiehead h, personal ag where h.codangajat = ag.cod and ");
		sqlString.append(" h.codangajat = ? and h.datac between ? and ? ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String afiseazaDelegatiiSubordVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal ");
		sqlString.append(" from sapprd.zdelegatiehead h, personal ag where h.codangajat = ag.cod  ");
		sqlString.append(" and ag.filiala =? and ag.departament =?  and h.datac between ? and ? ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String afiseazaDelegatiiSubordNONVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select h.id,  h.codangajat, h.data_plecare, h.ora_plecare, ag.nume, h.distcalc, h.distrespins, h.data_sosire, h.distreal ");
		sqlString.append(" from sapprd.zdelegatiehead h, personal ag where h.codangajat = ag.cod and ");
		sqlString.append(" h.idaprob in (select fid from functii_non_vanzari where aprobat=?)  and ag.filiala =?  and h.datac between ? and ? ");
		sqlString.append(" order by h.id ");

		return sqlString.toString();
	}

	public static String getDelegatieStatus() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select x.status from ( ");
		sqlString.append(" select status from sapprd.zdelstataprob where iddelegatie=? order by to_date(dataaprob||' '||oraaprob,'yyyymmdd hh24miss') desc ");
		sqlString.append(" ) x where rownum=1  ");

		return sqlString.toString();
	}

	public static String getDelegatiiAprobareRuta() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select r.judet, r.localitate, r.vizitat from sapprd.zdelegatieruta r where r.id = ?  order by to_number(r.poz)  ");
		return sqlString.toString();
	}

	public static String getPuncteTraseu() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select r.judet, r.localitate, r.vizitat, nvl(l.latitudine,'-1') lat, nvl(l.longitudine,'-1') lon, r.poz from ");
		sqlString.append(" sapprd.zdelegatieruta r, sapprd.zcoordlocalitati l where r.id = ? ");
		sqlString.append(" and trim(r.judet) = trim(l.judet(+)) and trim(r.localitate) = trim(l.localitate(+)) ");
		sqlString.append(" order by to_number(r.poz) ");

		return sqlString.toString();

	}

	public static String opereazaDelegatie() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(
				" insert into sapprd.zdelstataprob (mandt, iddelegatie, tipaprob, status, dataaprob, oraaprob, codAngajat) values ('900',?,?,?,?,?,?) ");
		return sqlString.toString();
	}

	public static String aprobaAutomatDelegatie() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" insert into sapprd.zdelstataprob (mandt, iddelegatie, tipaprob, status, dataaprob, oraaprob) values ");
		sqlString.append(" ('900',?,'AUTO','2',?,?) ");
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
		sqlString.append(
				" gtime between to_date(?,'dd-mm-yyyy HH24:mi') and to_date(?,'dd-mm-yyyy HH24:mi') and speed > 0 ) x where remainder(x.idt,2) = 0 order by x.gtime ");

		return sqlString.toString();
	}

	public static String getCoordRuta(String codDisp) {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(
				" select x.* from (select rownum idt, to_char(d.gtime,'dd-mm-yyyy HH24:mi') gtime, d.lat, d.lon, d.speed, d.km from nexus_gps_data d, ");
		sqlString.append(" nexus_vehicles n ");
		sqlString.append(" where d.vcode = n.vcode and ");
		sqlString.append(" trim(regexp_replace(n.car_number,'-| ','')) = trim(regexp_replace(?,'-| ','')) and ");
		sqlString.append(" trunc(d.gtime) between to_date(?,'dd-mm-yyyy HH24:mi') and ");
		sqlString.append(" to_date(?,'dd-mm-yyyy HH24:mi')  ) x where remainder(x.idt,1) = 0 order by x.gtime ");

		return sqlString.toString();

	}

	public static String getCodDispGps() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select vcode from nexus_vehicles n, sapprd.zdelegatiehead z where ");
		sqlString.append(" trim(regexp_replace(n.car_number,'-| ','')) = trim(regexp_replace(z.nrauto,'-| ','')) and z.id =?  ");

		return sqlString.toString();

	}

	public static String getCodDispGpsData() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select distinct vcode from nexus_vehicles n where ");
		sqlString.append(" trim(regexp_replace(n.car_number,'-| ','')) in ");
		sqlString.append(" ( select  trim(regexp_replace(c.ktext,'-| ','')) nrauto ");
		sqlString.append(" from sapprd.anlz a join sapprd.anla b on b.anln1 = a.anln1 and b.anln2 = a.anln2 and b.mandt=a.mandt ");
		sqlString.append(" join sapprd.aufk c on c.aufnr = a.caufn and c.mandt=a.mandt ");
		sqlString.append(" where a.pernr =? ");
		sqlString.append(" and a.bdatu >= ? and b.deakt = '00000000' and a.mandt='900') ");

		return sqlString.toString();
	}

	public static String getNrAuto() {

		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" select nrauto from sapprd.zdelegatiehead where id =? ");
		return sqlString.toString();

	}

	public static String getDelegatieCauta() {

		StringBuilder sqlString = new StringBuilder();
		sqlString.append("select data_plecare, ora_plecare, data_sosire, distcalc, codangajat from sapprd.zdelegatiehead where id = ?");
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
		sqlString.append(" and pernr =? and to_date(begda,'yyyymmdd') <=to_date(?,'yyyymmdd') and to_date(endda,'yyyymmdd') >=to_date(?,'yyyymmdd') ");

		return sqlString.toString();
	}

	public static String getDelModifHeader() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select id, data_plecare, data_sosire from sapprd.zdelegatiehead h where h.codangajat =? ");
		sqlString.append(" and (not exists (select 1 from sapprd.zdelstataprob b where b.iddelegatie = h.id and status in ('2','6'))) order by id");

		return sqlString.toString();
	}

	public static String getDelModifStartStop() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select localitate||' / '||judet punct from sapprd.zdelegatieruta where id =? order by to_number(poz) ");

		return sqlString.toString();
	}

	public static String getDelegatieModif() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select nrauto, data_plecare, ora_plecare, data_sosire from sapprd.zdelegatiehead where id =? ");

		return sqlString.toString();
	}

	public static String adaugaCoordonate() {

		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" insert into sapprd.zcoordlocalitati (mandt, judet, localitate, latitudine, longitudine) values ('900',?,?,?,?) ");
		return sqlString.toString();

	}

	public static String getCoordonateOpriri() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select  min(lat) lat, min(lon) lon, km from nexus_gps_data where ");
		sqlString.append(" vcode =? and gtime BETWEEN to_date(?, 'dd-mm-yyyy hh24:mi:ss') and ");
		sqlString.append(" to_date(?, 'dd-mm-yyyy hh24:mi:ss')  and speed = 0 ");
		sqlString.append(" group by km having count(speed) > 3 order by km ");

		return sqlString.toString();
	}

	public static String updateDistantaCalculata() {
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" update sapprd.zdelegatiehead set  distcalc=? where id=? ");
		return sqlString.toString();
	}

	public static String stergePuncteTraseu() {

		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" delete from sapprd.zdelegatieruta where  id =? and to_number(poz) > 1 and to_number(poz) < 100 ");

		return sqlString.toString();

	}

	public static String getSubordVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select cod, nume from personal where functie in ");
		sqlString.append(" (select cod from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and filiala=? and substr(departament,0,2)=? order by nume");

		return sqlString.toString();
	}

	public static String getSubordNonVanzari() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select cod, nume from personal where functie in ");
		sqlString.append(" (select cod from functii_non_vanzari where aprobat=?) ");
		sqlString.append(" and filiala=? order by nume");

		return sqlString.toString();
	}

	public static String getMasiniAngajat() {
		StringBuilder sqlString = new StringBuilder();

		sqlString.append(" select distinct c.ktext,a.adatu ");
		sqlString.append(" from sapprd.anlz a join sapprd.anla b on b.anln1 = a.anln1 and b.anln2 = a.anln2 and b.mandt=a.mandt ");
		sqlString.append(" join sapprd.aufk c on c.aufnr = a.caufn and c.mandt=a.mandt ");
		sqlString.append(" where a.pernr =? ");
		sqlString.append(" and a.bdatu >= ? and b.deakt = '00000000' and a.mandt='900' ");
		sqlString.append(" order by a.adatu desc ");

		return sqlString.toString();

	}

}
