package flota.service.beans;

import com.google.maps.model.LatLng;

public class PunctTraseu {

	private int pozitie;
	private LatLng coordonate;
	private StandardAddress adresa;
	private boolean vizitat;

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

	@Override
	public String toString() {
		return "PunctTraseu [pozitie=" + pozitie + ", coordonate=" + coordonate + ", adresa=" + adresa + ", vizitat=" + vizitat + "]";
	}
	
	

}
