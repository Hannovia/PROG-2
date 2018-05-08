package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

public class Plats extends JPanel{
	public Plats(int x, int y) {

	
	// Skriva kod som bestämmer färg på triangel beroende på kategori

		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.ORANGE);
		int[] xes = { 150, 300, 150};
		int[] yes = {100, 0, 150, 300};
		g.drawPolygon(xes, yes, 4);
	
	}
}

