package flota.service.helpers;

import flota.service.beans.BeanDelegatieAprobare;

public class HelperDelegatie {

	public static String getAtentionare(BeanDelegatieAprobare delegatie) {

		String msgAtentionare = "";

		if (delegatie.getDistantaEfectuata() > delegatie.getDistantaCalculata())
			msgAtentionare = "Distanta efectuata este mai mare decat distanta calculata.";

		for (int i = 1; i < delegatie.getListOpriri().size() - 1; i++) {
			if (!delegatie.getListOpriri().get(i).isVizitat()) {
				msgAtentionare = "Nu au fost vizitate toate punctele din traseu.";
				break;
			}
		}

		return msgAtentionare;

	}

}
