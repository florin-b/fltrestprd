package flota.service.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeanDelegatieGenerata {

	private String dataPlecare;
	private String dataSosire;
	private List<PunctTraseuLite> listOpriri;
	private int distantaCalculata;
	private int distantaEfectuata;
	private int kmCota;
	private String nrAuto;

	public String getDataPlecare() {
		return dataPlecare;
	}

	public void setDataPlecare(String dataPlecare) {
		this.dataPlecare = dataPlecare;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	public List<PunctTraseuLite> getListOpriri() {
		return listOpriri;
	}

	public void setListOpriri(List<PunctTraseuLite> listOpriri) {
		this.listOpriri = listOpriri;
	}

	public int getDistantaCalculata() {
		return distantaCalculata;
	}

	public void setDistantaCalculata(int distantaCalculata) {
		this.distantaCalculata = distantaCalculata;
	}

	public int getDistantaEfectuata() {
		return distantaEfectuata;
	}

	public void setDistantaEfectuata(int distantaEfectuata) {
		this.distantaEfectuata = distantaEfectuata;
	}

	public String getNrAuto() {
		return nrAuto;
	}

	public void setNrAuto(String nrAuto) {
		this.nrAuto = nrAuto;
	}

	public int getKmCota() {
		return kmCota;
	}

	public void setKmCota(int kmCota) {
		this.kmCota = kmCota;
	}

	@Override
	public String toString() {
		return "BeanDelegatieGenerata [dataPlecare=" + dataPlecare + ", dataSosire=" + dataSosire + ", listOpriri=" + listOpriri + ", distantaCalculata="
				+ distantaCalculata + ", distantaEfectuata=" + distantaEfectuata + "]";
	}

}
