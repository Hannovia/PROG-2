package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;

public class KartaInterface extends JFrame {
	KartPanel kartpanel = null;
	JScrollPane scrollPane = null;
	JFileChooser fileChooser = new JFileChooser(".");
	
	JRadioButton namedRB;
	JRadioButton describedRB;
	
	KartaInterface() {
		
		//Interface - NORRA
		JPanel norra = new JPanel();
		add(norra, BorderLayout.NORTH);
		JButton nyKnapp = new JButton("New");
		norra.add(nyKnapp);
		
		namedRB = new JRadioButton("Named", true);
		describedRB = new JRadioButton("Described");
		norra.add(namedRB);
		norra.add(describedRB);
		
		ButtonGroup sorteringGrupp = new ButtonGroup();
		sorteringGrupp.add(namedRB);
		sorteringGrupp.add(describedRB);
		
		
		//SEARCH-FÄLT
		JTextField searchFält = new JTextField(20);
		norra.add(searchFält);
		String searchText = searchFält.getText(); //Sökning
		
		
		// Knappar
		JButton searchKnapp = new JButton("Search");
		norra.add(searchKnapp);
		JButton hideKnapp = new JButton ("Hide");
		norra.add(hideKnapp);
		JButton removeKnapp = new JButton ("Remove");
		norra.add(removeKnapp);
		JButton coordinatesKnapp = new JButton ("Coordinates");
		norra.add(coordinatesKnapp);
		
		
		//Interface - HÖGRA
		JPanel högra = new JPanel();
		add(högra, BorderLayout.EAST);
		
		JLabel kategorier = new JLabel ("Categories");
		högra.add(kategorier);
		
		JTextArea kategoriFält = new JTextArea();
		kategoriFält.setEditable(false);
		högra.add(kategoriFält);
		
		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(800, 400);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new KartaInterface();
	}

}
