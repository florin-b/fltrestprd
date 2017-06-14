package flota.service.beans;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeanDelegatieAprobare {

	private String id;
	private String codAngajat;
	private String numeAngajat;
	private String dataPlecare;
	private String oraPlecare;
	private String dataSosire;
	private LinkedHashSet<String> listOpriri;
	private double distantaCalculata;
	private double distantaEfectuata;
	private double distantaAprobata;

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

	public LinkedHashSet<String> getListOpriri() {
		return listOpriri;
	}

	public void setListOpriri(LinkedHashSet<String> listOpriri) {
		this.listOpriri = listOpriri;
	}

	public double getDistantaCalculata() {
		return distantaCalculata;
	}

	public void setDistantaCalculata(double distantaCalculata) {
		this.distantaCalculata = distantaCalculata;
	}

	public double getDistantaEfectuata() {
		return distantaEfectuata;
	}

	public void setDistantaEfectuata(double distantaEfectuata) {
		this.distantaEfectuata = distantaEfectuata;
	}

	public double getDistantaAprobata() {
		return distantaAprobata;
	}

	public void setDistantaAprobata(double distantaAprobata) {
		this.distantaAprobata = distantaAprobata;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	@Override
	public String toString() {
		return "BeanDelegatieAprobare [id=" + id + ", codAngajat=" + codAngajat + ", numeAngajat=" + numeAngajat + ", dataPlecare=" + dataPlecare + ", oraPlecare=" + oraPlecare
				+ ", dataSosire=" + dataSosire + ", listOpriri=" + listOpriri + ", distantaCalculata=" + distantaCalculata + ", distantaEfectuata=" + distantaEfectuata
				+ ", distantaAprobata=" + distantaAprobata + "]";
	}

}
