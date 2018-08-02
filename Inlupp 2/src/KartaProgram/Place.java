package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;

abstract public class Place extends JComponent {
	String chosenCategory; 
	String name;
	String type;
	private boolean selected = false;
	private Position pos;

	abstract public void getDescription();
	abstract public String toString();
	abstract public String getType();
	abstract public String getDescriptionText();

	public Place(String type, String chosenCategory, String name, Position pos) {
	
		this.chosenCategory = chosenCategory;
		this.name = name;
		this.type = type;
		this.pos = pos;
		setBounds(pos.getX()-25,pos.getY()-50,50,50);
	}
	
	public String getNamn() {
		return name;
	}
	
	public Position getPos() {
		return pos;
	}
	
	public String getCategory() {
		return chosenCategory;
	}
	
	public int getPosX() {
		return pos.getX();
	}
	public int getPosY() {
		return pos.getY();
	}
	public void setHighlighted() {
		if(selected)
			selected = false;
		else
			selected = true;
	}
	public void UnMark() {
		selected = false;
	}
	
	public void Highlight() {
		selected = true;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			int[] xes = {25, 0, 50};
			int[] yes = {50, 0, 0};
	
		switch(chosenCategory) {
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
		
		if(!selected) {
			return;
		} else {
			g.setColor(Color.MAGENTA);
			g.drawRect(0, 0, 49, 49);
		}
	}	
	
	public void setVisad (boolean to) {
		selected = to;
		repaint();
	}

}

