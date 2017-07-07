package flota.service.beans;

public class PunctTraseuLite {

	private String adresa;
	private boolean vizitat;

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public boolean isVizitat() {
		return vizitat;
	}

	public void setVizitat(boolean vizitat) {
		this.vizitat = vizitat;
	}

	@Override
	public String toString() {
		return "PunctTraseuLite [adresa=" + adresa + ", vizitat=" + vizitat + "]";
	}

}
