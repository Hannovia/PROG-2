package Vardesaker;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


public class VärdesakInterface extends JFrame{

	ArrayList<Värdesaker> värdesakLista = new ArrayList<>();
	
	String[] värdesaker = {"Smycke", "Apparat", "Aktie"};
	JComboBox<String> värdeBox = new JComboBox<String> (värdesaker);
	JLabel valdLabel = new JLabel("Nytt: ");
	
	JTextField fält;
	JTextArea display;
	JRadioButton sorteringNamn;
	JRadioButton sorteringVärde;
	JPanel south;
	
	public VärdesakInterface () {
		super ("Sakregister");
		setLayout(new BorderLayout());
		
		JLabel fönsterTitel = new JLabel ("Värdesaker");
		
		
		// Display + textfält i mitten
		display = new JTextArea();
		JScrollPane scroll = new JScrollPane(display);
		display.setEditable(false);
		add(scroll, BorderLayout.CENTER);
		display.setBackground(Color.DARK_GRAY);
		
		Font f = new Font("Times Roman", Font.BOLD, 20);
		display.setFont(f);
		display.setForeground(Color.WHITE);
		
		
		// SOUTH
		south = new JPanel();	
		south.setLayout(new FlowLayout());
		add(south, BorderLayout.SOUTH);
		
		
		// Drop-down meny
		south.add(valdLabel);
		south.add(värdeBox);
		värdeBox.addActionListener(new dropDownLyss());
		
		
		// Knappar - SOUTH
		JButton visaKnapp = new JButton ("Visa");
		south.add(visaKnapp);
		visaKnapp.addActionListener(new VisaLyss());
		
		JButton börskraschKnapp = new JButton ("Börskrasch");
		south.add(börskraschKnapp);
		börskraschKnapp.addActionListener(new BörskraschLyss());
		
		
		// Sortering - RIGHT
		JPanel right = new JPanel();
		Font g = new Font("Times Roman", Font.BOLD, 16);
		right.setFont(g);
		right.add(new JLabel("Sortera efter "));
		sorteringNamn = new JRadioButton("Namn", true);
		right.add(sorteringNamn);
		sorteringVärde = new JRadioButton("Värde");
		right.add(sorteringVärde);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		add(right, BorderLayout.EAST);
		
	
		
		ButtonGroup sorteringGroup = new ButtonGroup(); //Knapp nr 2 avamrkeras
		sorteringGroup.add(sorteringNamn);
		sorteringGroup.add(sorteringVärde);
		
		
		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 700);
		setVisible(true);
		
	}
	
	
	class addSmycke extends JPanel{
		JTextField nameField = new JTextField(20);
		JTextField stenarField = new JTextField(3);
		JCheckBox goldBox = new JCheckBox("Av guld");
		
		addSmycke(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel ("Namn: "));
			rad1.add(nameField);
			add(rad1);
			
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel ("Stenar: "));
			rad2.add(stenarField);
			add(rad2);
			
			JPanel rad3 = new JPanel();
			rad3.add(goldBox);
			add(rad3);
		}
		
		public String getName() {
			return nameField.getText();
		}
		
		public int getStenar() {
			return Integer.parseInt(stenarField.getText());
		}
		
		public boolean getGold() {
			return goldBox.isSelected();
		}
		
		
	}
	
	class addApparat extends JPanel{
		JTextField nameField = new JTextField(20);
		JTextField prisField = new JTextField(10);
		JTextField slitageField = new JTextField(2);
		
		addApparat(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel ("Namn: "));
			rad1.add(nameField);
			add(rad1);
			
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Pris: "));
			rad2.add(prisField);
			add(rad2);
			
			JPanel rad3 = new JPanel();
			rad3.add(new JLabel ("Slitage: (1-10) "));
			rad3.add(slitageField);
			add(rad3);
		}
		
		public String getName() {
			return nameField.getText(); 
		}
		
		public int getPrice() {
			return Integer.parseInt(prisField.getText());
		}
		
		public int getSlitage() {
			return Integer.parseInt(slitageField.getText());
		}
	}
	
	class addAktie extends JPanel{
		JTextField nameField = new JTextField(20);
		JTextField antalField = new JTextField(2);
		JTextField kursField = new JTextField(4);
		
		addAktie(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel ("Namn: "));
			rad1.add(nameField);
			add(rad1);
			
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Antal: "));
			rad2.add(antalField);
			add(rad2);
			
			JPanel rad3 = new JPanel();
			rad3.add(new JLabel("Kurs: "));
			rad3.add(kursField);
			add(rad3);
		}
		
		public String getName() {
			return nameField.getText();
		}
		
		public int getAntal() {
			return Integer.parseInt(antalField.getText());
		}
		
		public int getKurs() {
			return Integer.parseInt(kursField.getText());
		}
	}
	
	class dropDownLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			String valt = (String)värdeBox.getSelectedItem();
			valdLabel.setText(valt);
			
			switch(valt){
				
			case "Aktie":
				try {
				addAktie aktieRuta = new addAktie();
				int svar = JOptionPane.showConfirmDialog(VärdesakInterface.this, aktieRuta, "Ny Aktie", JOptionPane.OK_CANCEL_OPTION);
				
				if(svar != JOptionPane.OK_OPTION)
					return;
				String namn = aktieRuta.getName();
				int antal = aktieRuta.getAntal();
				int kurs = aktieRuta.getKurs();
				
				Aktie värdesak = new Aktie(namn, antal, kurs);
				värdesakLista.add(värdesak);
				
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(VärdesakInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data", JOptionPane.ERROR_MESSAGE);
				}
				
				break;
				
			case "Smycke":
				try {
					addSmycke smyckeRuta = new addSmycke();
					int svar = JOptionPane.showConfirmDialog(VärdesakInterface.this, smyckeRuta, "Nytt smycke", JOptionPane.OK_CANCEL_OPTION);
					
					if(svar != JOptionPane.OK_OPTION)
						return;
					String namn = smyckeRuta.getName();
					int stenar = smyckeRuta.getStenar();
					boolean guld = smyckeRuta.getGold();
					int ädelstenar = smyckeRuta.getStenar();
					
					Värdesaker värdesak = new Smycke(namn, stenar, guld, ädelstenar);
					värdesakLista.add(värdesak);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(VärdesakInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data", JOptionPane.ERROR_MESSAGE);
				}
				
				break;
				
			case "Apparat":
				try {
					addApparat apparatRuta = new addApparat();
					int svar = JOptionPane.showConfirmDialog(VärdesakInterface.this, apparatRuta, "Ny apparat", JOptionPane.OK_CANCEL_OPTION);
					
					if(svar != JOptionPane.OK_OPTION)
						return;
					String namn = apparatRuta.getName();
					int pris = apparatRuta.getPrice();
					int slitage = apparatRuta.getSlitage();
					
					Värdesaker värdesak = new Apparat(namn, pris, slitage);
					värdesakLista.add(värdesak);
					
				}catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(VärdesakInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data", JOptionPane.ERROR_MESSAGE);
				}
				
				break;
			}
		}
	}
	
	
	class VisaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			display.setText("");
			
			if(sorteringNamn.isSelected())
				värdesakLista.sort(new NamnComparator());
			else if(sorteringVärde.isSelected())
				värdesakLista.sort(new VärdeComparator());
		
			for(Värdesaker värdesak: värdesakLista) {
				display.append(värdesak.toString() + "\n");
			}
		}
	}
	
	class BörskraschLyss implements ActionListener{
		public void actionPerformed (ActionEvent ave) {
			for(Värdesaker värdesak: värdesakLista) {
				if (värdesak instanceof Aktie) {
					Aktie a = (Aktie)värdesak;
					a.börskrasch();
				}
			}	
		}
	}
	
	class NamnComparator implements Comparator<Värdesaker>{
		public int compare(Värdesaker v1, Värdesaker v2) {
			return v1.getNamn().compareTo(v2.getNamn());
		}
	}
	
	class VärdeComparator implements Comparator<Värdesaker>{
		public int compare (Värdesaker v1, Värdesaker v2) {
			return (int) (v2.getVärde()-v1.getVärde());
		}
	}
	
		
	public static void main (String [] args) {
		new VärdesakInterface ();
	}
}
