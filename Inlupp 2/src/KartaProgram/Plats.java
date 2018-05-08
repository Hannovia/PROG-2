package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Plats extends JComponent{
	public Plats(int x, int y) {
		
		setBounds(x,y,50,50);
	//	setPreferredSize(new Dimension(50,450));
	// Skriva kod som bestämmer färg på triangel beroende på kategori

		
	}
	
	public void paintComponent(Graphics g) {
		System.out.println("test");
		super.paintComponent(g);
			int[] xes = {0, 25, 50};
			int[] yes = {0, 50, 0};
			g.setColor(Color.RED);
			g.fillPolygon(xes, yes, 3);
//		if (Underground) {
//			stuff
//		} else if(Bus){
//			stuff
//		} else if (Train){
//			stuff
//		}
		
	//	int[] xes = { 150, 300, 150};
	//	int[] yes = {100, 0, 150, 300};
	//	g.drawPolygon(xes, yes, 3);
	
	}
}

