package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;

public class KartaInterface extends JFrame {
	KartPanel kartpanel = null;
	JScrollPane scrollPane = null;
	JFileChooser filVäljare = new JFileChooser(".");
	JRadioButton namedRB;
	JRadioButton describedRB;
	JMenuBar menyBar;
	JMenu meny;

	
	String[] kategorier = {"Underground", "Bus", "Train"};
	
	KartaInterface() {
		
		//Menu 
		menyBar = new JMenuBar();
		meny = new JMenu("Archive");
		menyBar.add(meny);
	
		JMenuItem newMap = new JMenuItem("New Map");
		meny.add(newMap);
		JMenuItem loadPlaces = new JMenuItem("Load Places");
		meny.add(loadPlaces);
		JMenuItem save = new JMenuItem("Save");
		meny.add(save);
		JMenuItem exit = new JMenuItem("Exit");
		meny.add(exit);
		
		newMap.addActionListener(new NyKartaLyss());
		
		setJMenuBar(menyBar);
		
		FileFilter ff = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
		filVäljare.setFileFilter(ff);
		
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
		JLabel kategorierText = new JLabel ("Categories");
		högra.add(kategorierText);
		JList<String> Kategorilista = new JList <String>(kategorier);
		scrollPane = new JScrollPane(Kategorilista);
		högra.add(scrollPane);
		högra.setLayout(new BoxLayout(högra, BoxLayout.Y_AXIS));
		add(högra, BorderLayout.EAST);
		
		JButton gömKategorier = new JButton("Hide Categories");
		högra.add(gömKategorier);
		
		
		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(800, 400);
		setVisible(true);
		
	}
	
	class NyKartaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			int svar = filVäljare.showOpenDialog(KartaInterface.this);
			
			if(svar != JFileChooser.APPROVE_OPTION)
				return;
			
			File fil = filVäljare.getSelectedFile();
			String filNamn = fil.getAbsolutePath();
			if(scrollPane != null)
				remove(scrollPane);
			
			kartpanel = new KartPanel(filNamn);
			scrollPane = new JScrollPane(kartpanel);
			
			add(scrollPane, BorderLayout.CENTER);
			
			validate();
			repaint();
		}
	}
	
	public static void main(String[] args) {
		new KartaInterface();
	}

}
