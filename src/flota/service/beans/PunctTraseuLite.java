package flota.service.beans;

public class PunctTraseuLite {

	private String adresa;
	private boolean vizitat;
	private boolean init;

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

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	@Override
	public String toString() {
		return "PunctTraseuLite [adresa=" + adresa + ", vizitat=" + vizitat + "]";
	}

}
