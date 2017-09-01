package flota.service.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeanDelegatieAprobare {

	private String id;
	private String codAngajat;
	private String numeAngajat;
	private String dataPlecare;
	private String oraPlecare;
	private String dataSosire;
	private List<PunctTraseuLite> listOpriri;
	private int distantaCalculata;
	private int distantaRecalculata;
	private int distantaEfectuata;
	private int distantaRespinsa;
	private String statusCode;
	private String msgAtentionare;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodAngajat() {
		return codAngajat;
	}

	public void setCodAngajat(String codAngajat) {
		this.codAngajat = codAngajat;
	}

	public String getNumeAngajat() {
		return numeAngajat;
	}

	public void setNumeAngajat(String numeAngajat) {
		this.numeAngajat = numeAngajat;
	}

	public String getDataPlecare() {
		return dataPlecare;
	}

	public void setDataPlecare(String dataPlecare) {
		this.dataPlecare = dataPlecare;
	}

	public String getOraPlecare() {
		return oraPlecare;
	}

	public void setOraPlecare(String oraPlecare) {
		this.oraPlecare = oraPlecare;
	}

	public List<PunctTraseuLite> getListOpriri() {
		return listOpriri;
	}

	public void setListOpriri(List<PunctTraseuLite> listOpriri) {
		this.listOpriri = listOpriri;
	}

	public double getDistantaCalculata() {
		return distantaCalculata;
	}

	public void setDistantaCalculata(int distantaCalculata) {
		this.distantaCalculata = distantaCalculata;
	}

	public double getDistantaEfectuata() {
		return distantaEfectuata;
	}

	public void setDistantaEfectuata(int distantaEfectuata) {
		this.distantaEfectuata = distantaEfectuata;
	}

	public int getDistantaRespinsa() {
		return distantaRespinsa;
	}

	public void setDistantaRespinsa(int distantaRespinsa) {
		this.distantaRespinsa = distantaRespinsa;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsgAtentionare() {
		return msgAtentionare;
	}

	public void setMsgAtentionare(String msgAtentionare) {
		this.msgAtentionare = msgAtentionare;
	}

	public int getDistantaRecalculata() {
		return distantaRecalculata;
	}

	public void setDistantaRecalculata(int distantaRecalculata) {
		this.distantaRecalculata = distantaRecalculata;
	}

	@Override
	public String toString() {
		return "BeanDelegatieAprobare [id=" + id + ", codAngajat=" + codAngajat + ", numeAngajat=" + numeAngajat + ", dataPlecare=" + dataPlecare
				+ ", oraPlecare=" + oraPlecare + ", dataSosire=" + dataSosire + ", listOpriri=" + listOpriri + ", distantaCalculata=" + distantaCalculata
				+ ", distantaEfectuata=" + distantaEfectuata + ", distantaRespinsa=" + distantaRespinsa + ", statusCode=" + statusCode + ", msgAtentionare="
				+ msgAtentionare + "]";
	}

}
