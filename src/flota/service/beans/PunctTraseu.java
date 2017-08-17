package flota.service.beans;

import com.google.maps.model.LatLng;

public class PunctTraseu {

	private int pozitie;
	private LatLng coordonate;
	private StandardAddress adresa;
	private String strAdresa;
	private boolean vizitat;
	private boolean parasit;

	public int getPozitie() {
		return pozitie;
	}

	public void setPozitie(int pozitie) {
		this.pozitie = pozitie;
	}

	public LatLng getCoordonate() {
		return coordonate;
	}

	public void setCoordonate(LatLng coordonate) {
		this.coordonate = coordonate;
	}

	public StandardAddress getAdresa() {
		return adresa;
	}

	public void setAdresa(StandardAddress adresa) {
		this.adresa = adresa;
	}

	public boolean isVizitat() {
		return vizitat;
	}

	public void setVizitat(boolean vizitat) {
		this.vizitat = vizitat;
	}

	public String getStrAdresa() {
		return strAdresa;
	}

	public void setStrAdresa(String strAdresa) {
		this.strAdresa = strAdresa;
	}

	public boolean isParasit() {
		return parasit;
	}

	public void setParasit(boolean parasit) {
		this.parasit = parasit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strAdresa == null) ? 0 : strAdresa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PunctTraseu other = (PunctTraseu) obj;
		if (strAdresa == null) {
			if (other.strAdresa != null)
				return false;
		} else if (!strAdresa.equals(other.strAdresa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PunctTraseu [pozitie=" + pozitie + ", coordonate=" + coordonate + ", adresa=" + adresa + ", strAdresa=" + strAdresa + ", vizitat=" + vizitat
				+ "]";
	}
}
