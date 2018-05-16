package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BeskrivenPlats extends Plats {

	private String beskrivning;
	
	
	public BeskrivenPlats(int x, int y, String vald, String beskrivning) {
		super(x, y, vald);
		this.beskrivning=beskrivning;
	}
	
	public String getBeskrivning() {
		return beskrivning;
	}
}
