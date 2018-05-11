package flota.service.beans;

public class CategorieAngajat {

	private String tip;
	private String descriere;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}

	@Override
	public String toString() {
		return "CategorieAprob [tip=" + tip + ", descriere=" + descriere + "]";
	}

}
