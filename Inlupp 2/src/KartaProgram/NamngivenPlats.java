package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NamngivenPlats extends Plats {
	
	private boolean markerad = false;

	public NamngivenPlats(int x, int y, String vald) {
		super(x, y, vald);
		addMouseListener(new MarkeraLyss());
		
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
