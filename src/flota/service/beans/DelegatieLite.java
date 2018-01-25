package flota.service.beans;

public class DelegatieLite {

	private String codAngajat;
	private String idDelegatie;
	private String nrMasina;
	private String dataStart;

	public String getCodAngajat() {
		return codAngajat;
	}

	public void setCodAngajat(String codAngajat) {
		this.codAngajat = codAngajat;
	}

	public String getIdDelegatie() {
		return idDelegatie;
	}

	public void setIdDelegatie(String idDelegatie) {
		this.idDelegatie = idDelegatie;
	}

	public String getNrMasina() {
		return nrMasina;
	}

	public void setNrMasina(String nrMasina) {
		this.nrMasina = nrMasina;
	}

	public String getDataStart() {
		return dataStart;
	}

	public void setDataStart(String dataStart) {
		this.dataStart = dataStart;
	}

	@Override
	public String toString() {
		return "DelegatieLite [codAngajat=" + codAngajat + ", idDelegatie=" + idDelegatie + ", nrMasina=" + nrMasina + ", dataStart=" + dataStart + "]";
	}

}
