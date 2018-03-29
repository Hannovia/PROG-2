package Vardesaker;

public class Värdesaker {
	private String namn;
	
	public Värdesaker(String namn) {
		this.namn = namn;
	}
	
	public String getNamn() {
		return namn;
	}
}

class Smycke extends Värdesaker {

	private int ädelstenar;
	private int ädelVärde = ädelstenar * 500;
	private boolean guld = false;
	private int värde;
	private int totalVärdeSmycke = 0; 
	
	public Smycke (String namn) {
		super(namn);
		
	}

	public void räknaVärdet() {
	
	if (guld == true) {
		värde  = (2000) + (ädelVärde);
	}
	else {
		värde = (700) + ädelVärde;
	}	
	}
}

class Aktie extends Värdesaker {
	private double antalAktier;
	private double kurs;
	private double aktieVärde = (antalAktier) * (kurs);
	
	public Aktie (String namn) {
		super(namn);
	}
	
	public void setKurs(double nyKurs) {
		kurs = nyKurs;
	}
}

class Apparat extends Värdesaker {
	private double inköpspris;
	private double slitage;
	private double apparatVärde = (inköpspris) * (slitage);
	
	public Apparat (String namn ) {
		super(namn);
	}
}

