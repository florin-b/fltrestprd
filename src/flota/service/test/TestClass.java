package flota.service.test;

import java.util.List;

import flota.service.model.OperatiiTraseu;

public class TestClass {

	
	public static void main(String[] args)
	{
		
		List<String> coords = new OperatiiTraseu().getCoordonateTraseu("90033028", "08-06-2017 06:50", "08-06-2017 07:02");
		System.out.println(coords);
		
	}
	
	
}
