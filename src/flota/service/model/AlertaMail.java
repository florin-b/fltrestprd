package flota.service.model;

import java.util.List;

import flota.service.beans.IntervalDelegatie;
import flota.service.helpers.HelperDelegatie;
import flota.service.utils.DateUtils;
import flota.service.utils.MailOperations;

public class AlertaMail {

	public void verificaAlertWeekend(String idDelegatie) {

		IntervalDelegatie intervalDelegatie = HelperDelegatie.getIntervalDelegatie(idDelegatie);

		boolean hasWeendDays = DateUtils.intervalHasWeekendDays(intervalDelegatie);

		List<String> listAdrese = new AdreseMail().getAdresaDZ(idDelegatie);

		if (hasWeendDays) {
			String mailBody = " Delegatie: " + idDelegatie + ", adrese: " + listAdrese;
			MailOperations.sendMail("Delegatie alerta weekend", mailBody);
		}

	}

}
