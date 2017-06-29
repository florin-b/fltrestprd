package flota.service.helpers;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.model.LatLng;

import flota.service.beans.PunctTraseu;
import flota.service.utils.MapUtils;
import flota.service.utils.UtilsAddress;

public class HelperTraseu {

	public static List<LatLng> getCoordonatePuncte(List<String> puncte) {

		List<String> listPuncte = puncte;
		List<LatLng> listCoords = new ArrayList<>();

		listPuncte.remove(0);
		listPuncte.remove(listPuncte.size() - 1);

		for (String punct : listPuncte) {

			LatLng coordStop = MapUtils.geocodeAddress(UtilsAddress.getAddress(punct));
			listCoords.add(coordStop);

		}

		return listCoords;

	}

	public static List<PunctTraseu> loadPuncteTraseu(List<String> puncte) {

		List<PunctTraseu> listPuncte = new ArrayList<>();

		for (int i = 1; i < puncte.size(); i++) {
			PunctTraseu punct = new PunctTraseu();
			punct.setPozitie(i);
			punct.setAdresa(UtilsAddress.getAddress(puncte.get(i)));
			punct.setCoordonate(MapUtils.geocodeAddress(punct.getAdresa()));
			listPuncte.add(punct);

		}

		return listPuncte;

	}

}
