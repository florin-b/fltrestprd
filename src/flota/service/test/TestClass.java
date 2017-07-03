package flota.service.test;

import flota.service.model.OperatiiAngajat;
import flota.service.model.OperatiiDelegatii;

public class TestClass {

	public static void main(String[] args) {

		
		 //new OperatiiTraseu().getCoordonateTraseu("15256796182");
		 
		// new OperatiiDelegatii().getDelegatiiAprobari("SD", "GL10");
		 
		 // OperatiiTraseu.getDateSosireTraseu("15329086653");
		
		
		System.out.println(new OperatiiAngajat().getKmCota("00083215", "04-07-2017", "14-07-2017"));
		
		

	}

}
