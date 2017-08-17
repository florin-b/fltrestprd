package flota.service.beans;

import java.util.List;

import com.google.maps.model.LatLng;

public class Traseu {

	private List<LatLng> coordonate;
	private List<Oprire> opriri;

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

}
