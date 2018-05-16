package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

abstract public class Plats extends JComponent {
	String vald;
	private boolean markerad = false;
	
	abstract public String getBeskrivning();

	public Plats(int x, int y, String vald) {
		this.vald = vald;
		addMouseListener(new MarkeraLyss());
		setBounds(x-25,y-50,50,50);
	}
	
	public String toString() {
		return getBeskrivning(); 
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
			
			if (mev.getButton() == MouseEvent.BUTTON3) {
				JOptionPane.showMessageDialog(Plats.this, toString());
				
			} else if (mev.getButton() == MouseEvent.BUTTON1) {
				markerad =! markerad;
				repaint();
			} 
		}
	}
	
	public void setVisad (boolean to) {
		markerad = to;
		repaint();
	}
}

