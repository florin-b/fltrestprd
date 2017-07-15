package flota.service.enums;

public enum EnumTipAprob {
	AV(100), ATR(1), DAG(3);

	private int cod;

	EnumTipAprob(int cod) {
		this.cod = cod;
	}

	public int getCod() {
		return cod;
	}

	public static int getCodAprob(TipAnjagat tipAngajat) {
		for (EnumTipAprob tipAprob : EnumTipAprob.values()) {
			if (tipAngajat.toString().equals(tipAprob.toString()))
				return tipAprob.getCod();
		}

		return -1;
	}

	public static int getCodAprob(String tipAngajat) {
		for (EnumTipAprob tipAprob : EnumTipAprob.values()) {
			if (tipAngajat.equals(tipAprob.toString()))
				return tipAprob.getCod();
		}

		return -1;
	}

}
