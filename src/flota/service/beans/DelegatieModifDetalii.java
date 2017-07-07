package flota.service.beans;

import java.util.List;

public class DelegatieModifDetalii {

	private String nrAuto;
	private String dataPlecare;
	private String oraPlecare;
	private String locPlecare;
	private String dataSosire;
	private String locSosire;
	private List<String> ruta;

	public String getNrAuto() {
		return nrAuto;
	}

	public void setNrAuto(String nrAuto) {
		this.nrAuto = nrAuto;
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

	public String getLocPlecare() {
		return locPlecare;
	}

	public void setLocPlecare(String locPlecare) {
		this.locPlecare = locPlecare;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	public String getLocSosire() {
		return locSosire;
	}

	public void setLocSosire(String locSosire) {
		this.locSosire = locSosire;
	}

	public List<String> getRuta() {
		return ruta;
	}

	public void setRuta(List<String> ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		return "DelegatieModifDetalii [nrAuto=" + nrAuto + ", dataPlecare=" + dataPlecare + ", oraPlecare=" + oraPlecare + ", locPlecare=" + locPlecare + ", dataSosire="
				+ dataSosire + ", locSosire=" + locSosire + ", ruta=" + ruta + "]";
	}

}
