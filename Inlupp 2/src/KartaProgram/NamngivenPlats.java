package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class NamngivenPlats extends Plats {

	public NamngivenPlats(int x, int y, String vald, String namn, String kategori) {
		super(x, y, vald, namn, kategori);
	}

	public String toString() {
		return " {" + getX() + ", " + getY() + "}";
	}
	
	@Override
	public void getBeskrivning() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JPanel namnPanel = new JPanel();
		JLabel namnLabel = new JLabel("Name: " + namn + toString());
		namnPanel.add(namnLabel);
		infoPanel.add(namnPanel);
		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
	}
}
