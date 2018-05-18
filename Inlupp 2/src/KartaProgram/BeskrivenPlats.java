package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class BeskrivenPlats extends Plats {

	private String beskrivning;
	
	public BeskrivenPlats(int x, int y, String vald, String beskrivning) {
		super(x, y, vald);
		this.beskrivning=beskrivning;
	}
	
	
	public void getBeskrivning() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		JPanel namnPanel = new JPanel();
		JLabel namnLabel = new JLabel("NAMN HÄR");
		namnPanel.add(namnLabel);
		infoPanel.add(namnPanel);
		JPanel beskrivningPanel = new JPanel();
		JLabel beskrivningLabel = new JLabel("BESKRIVNING HÄR");
		beskrivningPanel.add(beskrivningLabel);
		infoPanel.add(beskrivningPanel);
		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
	}

}
