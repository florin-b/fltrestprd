package flota.service.beans;

public class Localitate {

	private String localitate;
	private String judet;

	public String getLocalitate() {
		return localitate;
	}

	public void setLocalitate(String localitate) {
		this.localitate = localitate;
	}

	public String getJudet() {
		return judet;
	}

	public void setJudet(String judet) {
		this.judet = judet;
	}

	@Override
	public String toString() {
		return "Localitate [localitate=" + localitate + ", judet=" + judet + "]";
	}

}
