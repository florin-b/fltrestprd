package flota.service.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prop1;
	private String prop2;

	public TestObject() {

	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	@Override
	public String toString() {
		return "TestObject [prop1=" + prop1 + ", prop2=" + prop2 + "]";
	}

}
