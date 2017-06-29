package flota.service.beans;

public class BeanDelegatieCauta {

	private String dataPlecare;
	private String oraPlecare;
	private String dataSosire;

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

	@Override
	public String toString() {
		return "BeanDelegatieCauta [dataPlecare=" + dataPlecare + ", oraPlecare=" + oraPlecare + ", dataSosire=" + dataSosire + "]";
	}

}
