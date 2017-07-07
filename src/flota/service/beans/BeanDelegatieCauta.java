package flota.service.beans;

public class BeanDelegatieCauta {

	private String id;
	private String angajatId;
	private String dataPlecare;
	private String oraPlecare;
	private String dataSosire;
	private int distantaCalculata;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAngajatId() {
		return angajatId;
	}

	public void setAngajatId(String angajatId) {
		this.angajatId = angajatId;
	}

	public String getDataPlecare() {
		return dataPlecare;
	}

	public void setDataPlecare(String dataPlecare) {
		this.dataPlecare = dataPlecare;
	}

	public String getOraPlecare() {
		return oraPlecare;
	}

	public void setOraPlecare(String oraPlecare) {
		this.oraPlecare = oraPlecare;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	public int getDistantaCalculata() {
		return distantaCalculata;
	}

	public void setDistantaCalculata(int distantaCalculata) {
		this.distantaCalculata = distantaCalculata;
	}

	@Override
	public String toString() {
		return "BeanDelegatieCauta [id=" + id + ", angajatId=" + angajatId + ", dataPlecare=" + dataPlecare + ", oraPlecare=" + oraPlecare + ", dataSosire=" + dataSosire
				+ ", distantaCalculata=" + distantaCalculata + "]";
	}

	

}
