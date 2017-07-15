package flota.service.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.OverQueryLimitException;
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

	public static List<String> getAdreseCoordonate(List<LatLng> coords) {

		Set<String> setAdrese = new LinkedHashSet<>();

		GeoApiContext context = GoogleContext.getContext();

		for (LatLng coord : coords) {
			try {
				GeocodingResult[] results = GeocodingApi.reverseGeocode(context, coord).await();

				String localitate = Utils.flattenToAscii(results[0].addressComponents[2].shortName);
				String judet = Utils.flattenToAscii(results[0].addressComponents[4].shortName);

				if (judet.equalsIgnoreCase("RO"))
					judet = Utils.flattenToAscii(results[0].addressComponents[3].shortName);

				judet = EnumJudete.getNumeJudet(judet);

				if (!judet.isEmpty())
					setAdrese.add(localitate + " / " + judet);

			} catch (Exception e) {
				MailOperations.sendMail(e.toString());
			}
		}

		List<String> arr = new ArrayList<>();

		arr.addAll(setAdrese);

		return arr;
	}

}
