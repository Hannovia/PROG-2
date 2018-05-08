package KartaProgram;

import javax.swing.*;
import java.awt.*;

public class KartPanel extends JPanel {
	private ImageIcon karta;
	
	public KartPanel(String filNamn){
		setLayout(null);
		karta = new ImageIcon(filNamn);
		int w = karta.getIconWidth();
		int h = karta.getIconHeight();
		
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w, h));
		setMinimumSize(new Dimension(w, h));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(karta.getImage(), 0, 0, this);
	}

}
