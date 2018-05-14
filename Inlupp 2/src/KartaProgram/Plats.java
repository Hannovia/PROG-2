package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Plats extends JComponent {
	String vald;
	public Plats(int x, int y, String vald) {
		this.vald = vald;
		setBounds(x-25,y-50,50,50);
		
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
		
	}	

}

