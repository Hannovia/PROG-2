package Vardesaker;

import java.util.Scanner;

public class Värdesaker {
	private String namn;
	
	public Värdesaker(String namn) {
		this.namn = namn;
	}
	
	public String getNamn() {
		return namn;
	}
}

class Smycken extends Värdesaker {
	
	private int ädelstenar;
	private int ädelVärde = ädelstenar * 500;
	private boolean guld = false;
	private int värde;
	private int totalVärdeSmycke = 0; 
	
	public Smycken (String namn) {
		super(namn);
		
	}

	
/*	if (guld == true) {
		värde  = 2000 + ädelVärde;
	}
	*/	

}
