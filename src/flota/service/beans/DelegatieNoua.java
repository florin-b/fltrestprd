package flota.service.beans;

import javax.ws.rs.FormParam;

public class DelegatieNoua {

	@FormParam("codAngajat")
	private String codAngajat;

	@FormParam("tipAngajat")
	private String tipAngajat;

	@FormParam("dataP")
	private String dataP;

	@FormParam("oraP")
	private String oraP;

	@FormParam("dataS")
	private String dataS;

	@FormParam("distcalc")
	private String distcalc;

	@FormParam("stops")
	private String stops;

	@FormParam("nrAuto")
	private String nrAuto;

	@FormParam("distreal")
	private String distreal;

	@FormParam("unitlog")
	private String unitLog;

	@FormParam("id")
	private String id;

	public String getCodAngajat() {
		return codAngajat;
	}

	public void setCodAngajat(String codAngajat) {
		this.codAngajat = codAngajat;
	}

	public String getTipAngajat() {
		return tipAngajat;
	}

	public void setTipAngajat(String tipAngajat) {
		this.tipAngajat = tipAngajat;
	}

	public String getDataP() {
		return dataP;
	}

	public void setDataP(String dataP) {
		this.dataP = dataP;
	}

	public String getOraP() {
		return oraP;
	}

	public void setOraP(String oraP) {
		this.oraP = oraP;
	}

	public String getDataS() {
		return dataS;
	}

	public void setDataS(String dataS) {
		this.dataS = dataS;
	}

	public String getDistcalc() {
		return distcalc;
	}

	public void setDistcalc(String distcalc) {
		this.distcalc = distcalc;
	}

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

	public String getNrAuto() {
		return nrAuto;
	}

	public void setNrAuto(String nrAuto) {
		this.nrAuto = nrAuto;
	}

	public String getDistreal() {
		return distreal;
	}

	public void setDistreal(String distreal) {
		this.distreal = distreal;
	}

	public String getUnitLog() {
		return unitLog;
	}

	public void setUnitLog(String unitLog) {
		this.unitLog = unitLog;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DelegatieNoua [codAngajat=" + codAngajat + ", tipAngajat=" + tipAngajat + ", dataP=" + dataP + ", oraP=" + oraP + ", dataS=" + dataS
				+ ", distcalc=" + distcalc + ", stops=" + stops + ", nrAuto=" + nrAuto + ", distreal=" + distreal + ", unitLog=" + unitLog + ", id=" + id + "]";
	}

}
