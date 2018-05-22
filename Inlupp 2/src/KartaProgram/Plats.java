package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

abstract public class Plats extends JComponent {
	String vald; 
	String namn;
	private boolean markerad = false;

	abstract public void getBeskrivning();
	abstract public String toString();
	
	public Plats(int x, int y, String vald, String namn) {
		this.vald = vald;
		this.namn = namn;
		setBounds(x-25,y-50,50,50);
	}
	
	
	public void setMarkerad() {
		if(markerad)
			markerad = false;
		else
			markerad = true;
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
	
	public void setVisad (boolean to) {
		markerad = to;
		repaint();
	}
}

