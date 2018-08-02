//package KartaProgram;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.*;
//
//public class BeskrivenPlats extends Place {
//
//	private String description;
//	
//	public BeskrivenPlats(String type, String chosenCategory, String namn, Position pos,  String description) {
//		super(type, chosenCategory, namn, pos);
//		this.description=description;
//	}
//
//	
//	public String toString() {
//		return  getType() + ", " + " {" + getPosX() + ", " + getPosY() + "}";
//	}
//	
//	public String getType() {
//		return "Described";
//	}
//	
//	public String getDescriptionText() {
//		return description;
//	}
//	
//	@Override
//	public void getDescription() {
//		JPanel infoPanel = new JPanel();
//		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
//		JPanel namnPanel = new JPanel();
//		JLabel nameLabel = new JLabel("Name: " + name + " " + toString());
//		namnPanel.add(nameLabel);
//		infoPanel.add(namnPanel);
//		JPanel descriptionPanel = new JPanel();
//		JLabel descriptionLabel = new JLabel("Description: " + description);
//		descriptionPanel.add(descriptionLabel);
//		infoPanel.add(descriptionPanel);
//		JOptionPane.showMessageDialog(null, infoPanel, "Platsinfo: ", JOptionPane.INFORMATION_MESSAGE);
//	}
//
//}
