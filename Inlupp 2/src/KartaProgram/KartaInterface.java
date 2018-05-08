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
	JTextField sökFält;
	JButton nyKnapp;
	MusLyss musLyss = new MusLyss();

	String[] kategorier = {"Underground", "Bus", "Train"};
	
	KartaInterface() {
		super("Inlupp 2: Hanna Severien, Viktor Fagerström Eriksson");
		
		//Meny
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
		
		
		// Filväljare
		FileFilter ff = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
		filVäljare.setFileFilter(ff);
		
		
		//Interface - NORRA
		JPanel norra = new JPanel();
		add(norra, BorderLayout.NORTH);
		nyKnapp = new JButton("New");
		norra.add(nyKnapp);
		nyKnapp.addActionListener(new NyLyss());
		
		namedRB = new JRadioButton("Named", true);
		describedRB = new JRadioButton("Described");
		norra.add(namedRB);
		norra.add(describedRB);
		
		ButtonGroup sorteringGrupp = new ButtonGroup();
		sorteringGrupp.add(namedRB);
		sorteringGrupp.add(describedRB);
		
		//Sökfält
		sökFält = new JTextField(10);
		norra.add(sökFält);
		String sökText = sökFält.getText();
		
		
		// Knappar
		JButton sökaKnapp = new JButton("Search");
		norra.add(sökaKnapp);
		sökaKnapp.addActionListener(new SökaLyss());
		
		JButton gömmaKnapp = new JButton ("Hide");
		norra.add(gömmaKnapp);
		gömmaKnapp.addActionListener(new GömLyss());
		
		JButton removeKnapp = new JButton ("Remove");
		norra.add(removeKnapp);
		removeKnapp.addActionListener(new RemoveLyss());
		
		JButton koordinaterKnapp = new JButton ("Coordinates");
		norra.add(koordinaterKnapp);
		koordinaterKnapp.addActionListener(new KoordinaterLyss());
		
		
		
		//Interface - HÖGRA
		JPanel högra = new JPanel();
		JLabel kategorierText = new JLabel ("Categories");
		högra.add(kategorierText);
		
		
		
		//Kategorilista - HÖGRA
		JList<String> Kategorilista = new JList <String>(kategorier);
		scrollPane = new JScrollPane(Kategorilista);
		högra.add(scrollPane);
		högra.setLayout(new BoxLayout(högra, BoxLayout.Y_AXIS));
		add(högra, BorderLayout.EAST);
		
		JButton gömKategorier = new JButton("Hide Categories");
		högra.add(gömKategorier);
		gömKategorier.addActionListener(new GömKategoriLyss());
		
		
		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
		setSize(1140, 923);
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
	
	class SökaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			String sökOrd = sökFält.getText();
//			String def = uppslag.get(sökOrd);
//			display.setText(def);
			
		}
	}
	
	
	class GömKategoriLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class NyLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			kartpanel.addMouseListener(musLyss);
			nyKnapp.setEnabled(false);
			kartpanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}
	
	class AddPlats extends JPanel{
		JTextField namnFält = new JTextField(10);
		
		AddPlats(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn på plats: "));
			rad1.add(namnFält);
			add(rad1);
		}
		
		public String getName() {
			return namnFält.getText();
		}
	}
	
	class MusLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			int x = mev.getX();
			int y = mev.getY();
			Plats plats = new Plats(x, y);
			kartpanel.add(plats);
			kartpanel.validate();
			kartpanel.repaint();
			kartpanel.removeMouseListener(musLyss);
			nyKnapp.setEnabled(true);
			kartpanel.setCursor(Cursor.getDefaultCursor());
			
			//AddPlats platsRuta = new AddPlats();
			//String namn = JOptionPane.showInputDialog(KartaInterface.this, platsRuta, "Ny plats", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	
	class GömLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class KoordinaterLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class RemoveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class LaddaPlatserLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class SparaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	class ExitLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
		}
	}
	
	public static void main(String[] args) {
		new KartaInterface();
	}

}
