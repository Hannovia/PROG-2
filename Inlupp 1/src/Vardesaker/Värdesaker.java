package Vardesaker;

public abstract class Värdesaker {
	private String namn;
	
	abstract public String getTyp();
	abstract public double getVärde();
	abstract public String getAttribut();
	
	public Värdesaker(String namn) {
		this.namn = namn;
	}
	
	public String getNamn() {
		return namn;
	}
	
	public String toString() {		
		return getTyp() + ": " + namn +  " värde: " + getVärde()  + getAttribut();
	}

}


class Smycke extends Värdesaker {

	private int ädelstenar;
	private boolean guld = false;

	
	public Smycke (String namn, int värde, boolean guld) {
		super(namn);
		
	}
	
	public String getTyp() {
		return "Smycke";
	}
	
	public String getGuldOrSilver() {
		if (guld)
			return "Guld";
		else
			return "Silver";
	}

	public double getVärde() {
	
	if (guld) {
		return (2000 + 500 * ädelstenar) * 1.25;
	}
	else {
		return (700 + 500*ädelstenar) * 1.25;
		}	
	}
	
	public String getAttribut() {
		return "Antal ädelstenar: " + ädelstenar + ". " + getGuldOrSilver();
	}
}

class Aktie extends Värdesaker {
	private double antalAktier;
	private double kurs;
	
	public Aktie (String namn, int antal, int kurs) {
		super(namn);
	}
	
	public String getTyp() {
		return "Aktie";
	}
	
	public void setKurs(double nyKurs) {
		kurs = nyKurs;
	}
	
	public double getVärde() {
		return (antalAktier * kurs) * 1.25;
	}
	
	public String getAttribut() {
		return "Antal aktier: " + antalAktier + ". Kurs: " + kurs;
	}
}

class Apparat extends Värdesaker {
	private double inköpspris;
	private double slitage;
	
	public Apparat (String namn, int värde, int slitage) {
		super(namn);
	}
	
	public String getTyp() {
		return "Apparat";
	}
	
//	public double beräknaSlitage() {
//		return INPUT FRÅN ANVÄNDAREN / 10 * inköpspris;
//	}
	
	public double getVärde() {
		return (inköpspris * slitage) * 1.25;
	}
	
	public String getAttribut() {
		return "Inköpspris" + inköpspris + ". " + "Slitage: " + slitage;
	}
}



