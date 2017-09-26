package flota.service.beans;

import javax.ws.rs.FormParam;

public class TestBean {

	@FormParam("name")
	private String name;

	@FormParam("address")
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "TestBean [name=" + name + ", address=" + address + "]";
	}
	
	

}
