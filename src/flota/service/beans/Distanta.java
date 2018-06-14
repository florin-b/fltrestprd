package flota.service.beans;

public class Distanta {

	private String codDisp;
	private int distanta;
	private String codAngajat;

	public String getCodDisp() {
		return codDisp;
	}

	public void setCodDisp(String codDisp) {
		this.codDisp = codDisp;
	}

	public int getDistanta() {
		return distanta;
	}

	public void setDistanta(int distanta) {
		this.distanta = distanta;
	}

	public String getCodAngajat() {
		return codAngajat;
	}

	public void setCodAngajat(String codAngajat) {
		this.codAngajat = codAngajat;
	}

	@Override
	public String toString() {
		return "Distanta [codDisp=" + codDisp + ", distanta=" + distanta + ", codAngajat=" + codAngajat + "]";
	}

	

}
