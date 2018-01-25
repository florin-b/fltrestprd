package flota.service.beans;

public class IntervalData {

	private String dataStart;
	private String dataStop;

	public String getDataStart() {
		return dataStart;
	}

	public void setDataStart(String dataStart) {
		this.dataStart = dataStart;
	}

	public String getDataStop() {
		return dataStop;
	}

	public void setDataStop(String dataStop) {
		this.dataStop = dataStop;
	}

	@Override
	public String toString() {
		return "IntervalData [dataStart=" + dataStart + ", dataStop=" + dataStop + "]";
	}
	
	

}
