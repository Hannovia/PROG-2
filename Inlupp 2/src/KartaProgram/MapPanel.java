package KartaProgram;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {
	private ImageIcon map;
	
	public MapPanel(String filNamn){
		setLayout(null);
		map = new ImageIcon(filNamn);
		int w = map.getIconWidth();
		int h = map.getIconHeight();
		
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w, h));
		setMinimumSize(new Dimension(w, h));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getImage(), 0, 0, this);
	}

}
