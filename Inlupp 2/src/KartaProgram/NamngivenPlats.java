package KartaProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;


public class NamngivenPlats extends Plats {

	public NamngivenPlats(int x, int y, String vald) {
		super(x, y, vald);
	}

	@Override
	public void getBeskrivning() {
		JOptionPane.showMessageDialog(null, "PLATS NAMN HÄR", "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
	}

}
