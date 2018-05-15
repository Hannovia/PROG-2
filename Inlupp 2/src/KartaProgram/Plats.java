package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Plats extends JComponent {
	String vald;
	private boolean visad = false;
	public Plats(int x, int y, String vald) {
		this.vald = vald;
		setBounds(x-25,y-50,50,50);
		addMouseListener(new MusLyss());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			int[] xes = {25, 0, 50};
			int[] yes = {50, 0, 0};
	
		switch(vald) {
			case "Underground":
				g.setColor(Color.BLUE);
				repaint();
				break;
			case "Bus":
				g.setColor(Color.RED);
				repaint();
				break;
			case "Train":
				g.setColor(Color.GREEN);
				repaint();
				break;
			default:
				g.setColor(Color.BLACK);
				repaint();
				break;
			
		}
		g.fillPolygon(xes, yes, 3);
		
//		if(visad) {
//			visa(g);
//		} else {
//			g.setColor(Color.BLUE);
//			g.fillPolygon(xes, yes, 3);
//		}
	}	
	class MusLyss extends MouseAdapter{
		@Override
		public void mouseClicked (MouseEvent mev) {
			if(visad = false) {
				setVisible(true); // Såhär gömmer vi skiten bra va?
				visad = true;
			} else if (visad = true){ //Ska läggas in i Hide knappen
				setVisible(false);
				visad = false;
			}
			}
		}
}

