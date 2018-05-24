package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class NamngivenPlats extends Plats {

	public NamngivenPlats(String typ, String valdKategori, String vald, int x, int y) {
		super(typ, valdKategori, vald, x, y);
	}

	public String toString() {
		return getTyp() + ":" + " {" + getPosX() + ", " + getPosY() + "}";
	}
	
	public String getTyp() {
		return "Named";
	}

	public String getBeskrivningText() {
		return null;
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