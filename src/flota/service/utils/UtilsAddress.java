package flota.service.utils;

import flota.service.beans.StandardAddress;

public class UtilsAddress {

	public static StandardAddress getAddress(String strAddress) {

		StandardAddress address = new StandardAddress();

		address.setSector(strAddress.split("/")[0]);
		address.setCity(strAddress.split("/")[1]);

		return address;

	}

}
