package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

abstract public class Plats extends JComponent {
	String valdKategori; 
	String namn;
	String typ;
	private boolean markerad = false;
	private Position pos;

	abstract public void getBeskrivning();
	abstract public String toString();
	abstract public String getTyp();
	abstract public String getBeskrivningText();

	public Plats(String typ, String valdKategori, String namn, Position pos) {
	
		this.valdKategori = valdKategori;
		this.namn = namn;
		this.typ = typ;
		this.pos = pos;
		setBounds(pos.getX()-25,pos.getY()-50,50,50);
	}
	
	public String getNamn() {
		return namn;
	}
	
	public Position getPos() {
		return pos;
	}
	
	public String getKategori() {
		return valdKategori;
	}
	
	public int getPosX() {
		return pos.getX();
	}
	public int getPosY() {
		return pos.getY();
	}
	public void setMarkerad() {
		if(markerad)
			markerad = false;
		else
			markerad = true;
	}
	public void Avmarkera() {
		markerad = false;
	}
	
	public void Markera() {
		markerad = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			int[] xes = {25, 0, 50};
			int[] yes = {50, 0, 0};
	
		switch(valdKategori) {
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
			case "None":
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

