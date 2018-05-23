package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class BeskrivenPlats extends Plats {

	private String beskrivning;
	
	public BeskrivenPlats(int x, int y, String vald, String namn, String kategori, String typ) {
		super(x, y, vald, namn, typ);
		this.beskrivning=beskrivning;
	}

	
	public String toString() {
		return  getTyp() + ":" + " {" + getX() + ", " + getY() + "}";
	}
	
	public String getTyp() {
		return "Described";
	}
	
	public String getBeskrivningText() {
		return beskrivning;
	}
	
	@Override
	public void getBeskrivning() {
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
