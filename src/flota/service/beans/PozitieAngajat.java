package flota.service.beans;

public class PozitieAngajat {

	private String numeAngajat;
	private PozitieGps pozitie;

	public String getNumeAngajat() {
		return numeAngajat;
	}

	public void setNumeAngajat(String numeAngajat) {
		this.numeAngajat = numeAngajat;
	}

	public PozitieGps getPozitie() {
		return pozitie;
	}

	public void setPozitie(PozitieGps pozitie) {
		this.pozitie = pozitie;
	}

	@Override
	public String toString() {
		return "PozitieAngajat [numeAngajat=" + numeAngajat + ", pozitie=" + pozitie + "]";
	}

}
