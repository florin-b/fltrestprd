package flota.service.beans;

public class DelegatieModifAntet {

	private String id;
	private String dataPlecare;
	private String dataSosire;
	private String localitateStart;
	private String localitateStop;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataPlecare() {
		return dataPlecare;
	}

	public void setDataPlecare(String dataPlecare) {
		this.dataPlecare = dataPlecare;
	}

	public String getDataSosire() {
		return dataSosire;
	}

	public void setDataSosire(String dataSosire) {
		this.dataSosire = dataSosire;
	}

	public String getLocalitateStart() {
		return localitateStart;
	}

	public void setLocalitateStart(String localitateStart) {
		this.localitateStart = localitateStart;
	}

	public String getLocalitateStop() {
		return localitateStop;
	}

	public void setLocalitateStop(String localitateStop) {
		this.localitateStop = localitateStop;
	}

	@Override
	public String toString() {
		return "DelegatieModifAntet [id=" + id + ", dataPlecare=" + dataPlecare + ", dataSosire=" + dataSosire + ", localitateStart=" + localitateStart + ", localitateStop="
				+ localitateStop + "]";
	}

}
