package flota.service.test;

import flota.service.model.OperatiiDelegatii;
import flota.service.model.OperatiiTraseu;

public class TestClass {

	public static void main(String[] args) {

		
		 //new OperatiiTraseu().getCoordonateTraseu("15256796182");
		 
		// new OperatiiDelegatii().getDelegatiiAprobari("SD", "GL10");
		 
		 // OperatiiTraseu.getDateSosireTraseu("15329086653");
		
		
		
		
		System.out.println(new OperatiiTraseu().getDateSosireTraseu("16194720104"));
		
		new OperatiiDelegatii().aprobaAutomatDelegatie("16194720104");
		
		

	}

}
