//package KartaProgram;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.*;
//
//
//public class NamngivenPlats extends Place {
//
//	public NamngivenPlats(String type, String chosenCategory, String selected, Position pos) {
//		super(type, chosenCategory, selected, pos);
//	}
//
//	public String toString() {
//		return getType() + ", " + " {" + getPosX() + ", " + getPosY() + "}";
//	}
//	
//	public String getType() {
//		return "Named";
//	}
//
//	public String getDescriptionText() {
//		return null;
//	}
//	
//	@Override
//	public void getDescription() {
//		JPanel infoPanel = new JPanel();
//		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
//		JPanel namePanel = new JPanel();
//		JLabel nameLabel = new JLabel("Name: " + name + " " + toString());
//		namePanel.add(nameLabel);
//		infoPanel.add(namePanel);
//		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
//	}
//
//}