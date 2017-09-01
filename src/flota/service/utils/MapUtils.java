package flota.service.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import flota.service.beans.GoogleContext;
import flota.service.beans.StandardAddress;
import flota.service.enums.EnumJudete;

public class MapUtils {

	public static double distanceXtoY(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static LatLng geocodeAddress(StandardAddress address) {
		LatLng coordonateGps = new LatLng(0, 0);

		try {

			StringBuilder strAddress = new StringBuilder();

			if (address.getStreet() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getStreet());
				strAddress.append(",");
			}

			if (address.getNumber() != null && !address.getStreet().equals("")) {
				strAddress.append(address.getNumber());
				strAddress.append(",");
			}

			if (address.getSector() != null && !address.getSector().equals("")) {
				strAddress.append(address.getSector());
				strAddress.append(",");
			}

			if (address.getCity() != null && !address.getCity().equals("")) {
				strAddress.append(address.getCity());
				strAddress.append(",");
			}

			strAddress.append(address.getCountry());

			GeoApiContext context = GoogleContext.getContext();

			GeocodingResult[] results;

			results = GeocodingApi.geocode(context, strAddress.toString()).await();

			double latitude = results[0].geometry.location.lat;
			double longitude = results[0].geometry.location.lng;

			coordonateGps.lat = latitude;
			coordonateGps.lng = longitude;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return coordonateGps;
	}

	public static int getDistantaTraseu(List<LatLng> listCoords) {

		int distanta = 0;
		DirectionsRoute[] routes = null;

		try {

			List<String> strList = new ArrayList<>();

			for (LatLng coord : listCoords)
				strList.add(coord.lat + " , " + coord.lng);

			String[] arrayPoints = strList.toArray(new String[strList.size()]);

			GeoApiContext context = GoogleContext.getContext();

			LatLng start = new LatLng(listCoords.get(0).lat, listCoords.get(0).lng);

			LatLng stop = new LatLng(listCoords.get(listCoords.size() - 1).lat, listCoords.get(listCoords.size() - 1).lng);

			routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).waypoints(arrayPoints).mode(TravelMode.DRIVING)
					.optimizeWaypoints(false).await();

			for (int i = 0; i < routes[0].legs.length; i++) {
				distanta += routes[0].legs[i].distance.inMeters;

			}

		} catch (OverQueryLimitException q) {
			MailOperations.sendMail("traseuBorderou: " + q.toString());
		} catch (Exception ex) {
			MailOperations.sendMail("traseuBorderou: " + ex.toString());
		}

		return distanta / 1000;

	}

	public static int getDistantaTraseuAdrese(List<String> listAdrese) {

		int distanta = 0;
		DirectionsRoute[] routes = null;

		try {

			List<String> strList = new ArrayList<>();

			for (String adresa : listAdrese) {

				strList.add("Romania, " + adresa.split("/")[1] + " , " + adresa.split("/")[0]);
			}

			String[] arrayPoints = strList.toArray(new String[strList.size()]);

			GeoApiContext context = GoogleContext.getContext();

			String start = "Romania, " + listAdrese.get(0).split("/")[1] + " , " + listAdrese.get(0).split("/")[0];

			String stop = "Romania, " + listAdrese.get(listAdrese.size() - 1).split("/")[1] + " , " + listAdrese.get(listAdrese.size() - 1).split("/")[0];

			routes = DirectionsApi.newRequest(context).mode(TravelMode.DRIVING).origin(start).destination(stop).waypoints(arrayPoints).mode(TravelMode.DRIVING)
					.optimizeWaypoints(false).await();

			for (int i = 0; i < routes[0].legs.length; i++) {
				distanta += routes[0].legs[i].distance.inMeters;

			}

		} catch (OverQueryLimitException q) {
			MailOperations.sendMail("traseuBorderou: " + q.toString());
		} catch (Exception ex) {
			MailOperations.sendMail("traseuBorderou: " + ex.toString());
		}

		return distanta / 1000;

	}

	public static List<String> getAdreseCoordonate(List<LatLng> coords) {

		Set<String> setAdrese = new LinkedHashSet<>();

		GeoApiContext context = GoogleContext.getContext();
		String adresaStart = "";
		String adresaStop = "";

		String localitate;
		String judet;

		for (int i = 0; i < coords.size(); i++) {

			try {
				GeocodingResult[] results = GeocodingApi.reverseGeocode(context, coords.get(i)).await();

				localitate = "";
				judet = "";

				for (int j = 0; j < results[0].addressComponents.length; j++) {

					AddressComponentType[] adrComponentType = results[0].addressComponents[j].types;

					for (int k = 0; k < adrComponentType.length; k++) {
						if (adrComponentType[k] == AddressComponentType.LOCALITY) {
							localitate = Utils.flattenToAscii(results[0].addressComponents[j].shortName);
							break;
						}

						if (adrComponentType[k] == AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1) {
							judet = Utils.flattenToAscii(results[0].addressComponents[j].shortName);
							break;
						}

					}

					if (!localitate.isEmpty() && !judet.isEmpty()) {

						judet = EnumJudete.getNumeJudet(judet);

						if (!judet.isEmpty() && i > 0 && i < coords.size() - 1) {
							setAdrese.add(localitate + " / " + judet);

						}

						if (i == 0)
							adresaStart = localitate + " / " + judet;

						if (i == coords.size() - 1)
							adresaStop = localitate + " / " + judet;

						localitate = "";
						judet = "";
					}

				}

			} catch (Exception e) {
				MailOperations.sendMail(e.toString());
			}
		}

		List<String> arr = new ArrayList<>();

		arr.addAll(setAdrese);

		if (!adresaStart.isEmpty())
			arr.add(0, adresaStart);

		if (!adresaStop.isEmpty())
			arr.add(arr.size(), adresaStop);
		
		
		System.out.println(arr);

		return arr;
	}

	public static String getAdresaCoordonate(double lat, double lng) {

		String adresa = "";

		GeoApiContext context = GoogleContext.getContext();

		try {
			GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(lat, lng)).await();

			String localitate = Utils.flattenToAscii(results[0].addressComponents[2].shortName);
			String judet = Utils.flattenToAscii(results[0].addressComponents[4].shortName);

			if (judet.equalsIgnoreCase("RO"))
				judet = Utils.flattenToAscii(results[0].addressComponents[3].shortName);

			judet = EnumJudete.getNumeJudet(judet);

			if (!judet.isEmpty())
				adresa = localitate + " / " + judet;

		} catch (Exception e) {
			MailOperations.sendMail(e.toString());
		}

		return adresa;
	}

}
