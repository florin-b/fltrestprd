package flota.service.beans;

import com.google.maps.model.LatLng;

public class AdresaOprire {

	private String adresa;
	private LatLng coordonate;

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public LatLng getCoordonate() {
		return coordonate;
	}

	public void setCoordonate(LatLng coordonate) {
		this.coordonate = coordonate;
	}

	@Override
	public String toString() {
		return "AdresaOprire [adresa=" + adresa + ", coordonate=" + coordonate + "]";
	}

}
