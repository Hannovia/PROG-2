package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class BeskrivenPlats extends Plats {

	private String beskrivning;
	
	public BeskrivenPlats(String typ, String valdKategori, String namn, Position pos,  String beskrivning) {
		super(typ, valdKategori, namn, pos);
		this.beskrivning=beskrivning;
	}

	
	public String toString() {
		return  getType() + ":" + " {" + getPosX() + ", " + getPosY() + "}";
	}
	
	public String getType() {
		return "Described";
	}
	
	public String getDescriptionText() {
		return beskrivning;
	}
	
	@Override
	public void getDescription() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JPanel namnPanel = new JPanel();
		JLabel namnLabel = new JLabel("Name: " + namn + toString());
		namnPanel.add(namnLabel);
		infoPanel.add(namnPanel);
		JPanel beskrivningPanel = new JPanel();
		JLabel beskrivningLabel = new JLabel("Description: " + beskrivning);
		beskrivningPanel.add(beskrivningLabel);
		infoPanel.add(beskrivningPanel);
		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
	}

}
