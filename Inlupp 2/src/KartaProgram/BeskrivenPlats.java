package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import KartaProgram.NamngivenPlats.MarkeraLyss;

public class BeskrivenPlats extends Plats {

	private boolean markerad = false;
	private String beskrivning;
	
	
	public BeskrivenPlats(int x, int y, String vald, String beskrivning) {
		super(x, y, vald);
		this.beskrivning=beskrivning;
		
		addMouseListener(new MarkeraLyss());
		
	}
	
	public String getBeskrivning() {
		return beskrivning;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(!markerad) {
			return;
		} else {
			g.setColor(Color.MAGENTA);
			g.drawRect(0, 0, 49, 49);
		}
		
	}
	
	public class MarkeraLyss extends MouseAdapter{
		@Override
		public void mouseClicked (MouseEvent mev) {
			markerad =! markerad;
			repaint();
			
		}
	}
	


}
