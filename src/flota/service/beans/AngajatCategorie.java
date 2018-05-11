package flota.service.beans;

public class AngajatCategorie extends Angajat {

	private String categorie;

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	@Override
	public String toString() {
		return "AngajatCategorie [categorie=" + categorie + ", getCod()=" + getCod() + ", getNume()=" + getNume() + "]";
	}
	
	

}
