package flota.service.beans;

public class FunctieConducere {
	private String numeAngajat;
	private String filiala;
	private String functie;
	private String departament;
	private String mail;

	public String getFiliala() {
		return filiala;
	}

	public void setFiliala(String filiala) {
		this.filiala = filiala;
	}

	public String getFunctie() {
		return functie;
	}

	public void setFunctie(String functie) {
		this.functie = functie;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNumeAngajat() {
		return numeAngajat;
	}

	public void setNumeAngajat(String numeAngajat) {
		this.numeAngajat = numeAngajat;
	}

	@Override
	public String toString() {
		return "FunctieConducere [numeAngajat=" + numeAngajat + ", filiala=" + filiala + ", functie=" + functie + ", departament=" + departament + ", mail="
				+ mail + "]";
	}

}
