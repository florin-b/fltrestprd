package flota.service.beans;

import java.util.List;

import com.google.maps.model.LatLng;

public class Traseu {

	private List<LatLng> coordonate;
	private List<Oprire> opriri;
	private int distanta;

	public List<LatLng> getCoordonate() {
		return coordonate;
	}

	public void setCoordonate(List<LatLng> coordonate) {
		this.coordonate = coordonate;
	}

	public List<Oprire> getOpriri() {
		return opriri;
	}

	public void setOpriri(List<Oprire> opriri) {
		this.opriri = opriri;
	}

	public int getDistanta() {
		return distanta;
	}

	public void setDistanta(int distanta) {
		this.distanta = distanta;
	}

}
