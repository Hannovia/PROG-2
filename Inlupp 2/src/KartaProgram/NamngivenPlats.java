package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class NamngivenPlats extends Plats {

	public NamngivenPlats(String typ, String valdKategori, String vald, Position pos) {
		super(typ, valdKategori, vald, pos);
	}

	public String toString() {
		return getType() + ":" + " {" + getPosX() + ", " + getPosY() + "}";
	}
	
	public String getType() {
		return "Named";
	}

	public String getDescriptionText() {
		return null;
	}
	
	@Override
	public void getDescription() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JPanel namnPanel = new JPanel();
		JLabel namnLabel = new JLabel("Name: " + namn + toString());
		namnPanel.add(namnLabel);
		infoPanel.add(namnPanel);
		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
	}

}