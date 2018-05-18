package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import java.util.*;

// TO-DO-LIST
// - när remove-knappen går igenom listan ska den endast gå igenom en lista med markeade objekt, inte alla objekt

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
	MarkeraLyss1 markeraLyss = new MarkeraLyss1();
	NamngivenPlats namngivenPlats;
	BeskrivenPlats beskrivenPlats;
	
	
	String[] kategorier = {"Underground", "Bus", "Train"};
	JList<String> kategorilista = new JList <String>(kategorier);
	Map<Position, Plats> koordinatlista = new HashMap<>();
	HashSet<Plats> platser = new HashSet<>();
	HashSet<Plats> markeradePlatser = new HashSet<>();
	
	KartaInterface() {
		super("Inlupp 2: Hanna Severien, Viktor Fagerström Eriksson");	
		//Meny
		menyBar = new JMenuBar();
		meny = new JMenu("Archive");
		menyBar.add(meny);
	
		JMenuItem newMap = new JMenuItem("New Map");
		meny.add(newMap);
		newMap.addActionListener(new NyKartaLyss());
		
		JMenuItem loadPlaces = new JMenuItem("Load Places");
		meny.add(loadPlaces);
		
		JMenuItem save = new JMenuItem("Save");
		meny.add(save);
		
		JMenuItem exit = new JMenuItem("Exit");
		meny.add(exit);
		exit.addActionListener(new ExitLyss());
		
		
		
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
		scrollPane = new JScrollPane(kategorilista);
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
	
	class addDescribedPlace extends JPanel {
		JTextField namnFält = new JTextField(10);
		JTextField beskrivningFält = new JTextField(30);
		addDescribedPlace() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel ("Namn: "));
			rad1.add(namnFält);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Beskrivning: "));
			rad2.add(beskrivningFält);
			add(rad2);
		}
		
		public String getNamn() {
			return namnFält.getText();
		}
		
		public String getBeskrivning() {
			return beskrivningFält.getText();
		}
	}
	
	class MusLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			int x = mev.getX();
			int y = mev.getY();
	        nyKnapp.setEnabled(true);
			
			String vald = kategorilista.getSelectedValue();
			if(vald==null) {
				vald = "Ingen";
			}
			//ta bort variabarna namngiven och beskriven 
			//Plats plats i Muslyss (aka HÄR)
			kartpanel.removeMouseListener(this);
		
			kartpanel.setCursor(Cursor.getDefaultCursor());
			
			if (namedRB.isSelected()) {
				String namn = JOptionPane.showInputDialog(KartaInterface.this, "Ange namn på ny plats:");
				if (namn == null) {
					return;
				}
				else if (namn.isEmpty()) {
					return; //Skriv ett errormeddelande här
				}
				System.out.println(namn);
				Plats namngivenPlats = new NamngivenPlats(x, y, vald);
				Position position = new Position(x, y);	
				platser.add(namngivenPlats);
				kartpanel.add(namngivenPlats);
				namngivenPlats.addMouseListener(new MarkeraLyss1());
				kartpanel.validate();
				kartpanel.repaint();
				
				
				
			
			
			} else if (describedRB.isSelected()) {
				addDescribedPlace describedRuta = new addDescribedPlace();
				int svar = JOptionPane.showConfirmDialog(KartaInterface.this,  describedRuta, "Ny ruta", JOptionPane.OK_CANCEL_OPTION);
			
			if (svar != JOptionPane.OK_OPTION)
				return;
			
			String namn = describedRuta.getName();
			String beskrivning = describedRuta.getBeskrivning();
			Plats beskrivenPlats = new BeskrivenPlats(x, y, vald, beskrivning);
			Position position = new Position(x, y);
			for(Plats p:platser) {
				//System.out.println(p.getBeskrivning());
			}
			platser.add(beskrivenPlats);
			kartpanel.add(beskrivenPlats);
			beskrivenPlats.addMouseListener(new MarkeraLyss1());
			kartpanel.validate();
			kartpanel.repaint();
			for(Plats p:platser) {
				//System.out.println(p.getBeskrivning());
			}
			}
			
		}
	}
	
	public class MarkeraLyss1 extends MouseAdapter{
		@Override
		public void mouseClicked (MouseEvent mev) {
			
			Plats p = (Plats)mev.getSource();
			if (mev.getButton() == MouseEvent.BUTTON1) {
				if(markeradePlatser.contains(p)) {
					markeradePlatser.remove(p);
					System.out.println("tas bort");
				} else {
					markeradePlatser.add(p);
					System.out.println("läggs till");
				}
//				p.setVisible(false); 
				repaint();
			} else if(mev.getButton() == MouseEvent.BUTTON3) {
				p.getBeskrivning();
			}
		}
	}


	public class GömLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			
			for(Plats p: markeradePlatser) {
				p.setVisible(false);
			}
				markeradePlatser.clear();
				kartpanel.repaint();
		}
	}
	
	class AddKoordinatRuta extends JPanel{
		JTextField xFält = new JTextField(6);
		JTextField yFält = new JTextField(6);
		
		AddKoordinatRuta(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel ("X: "));
			rad1.add(xFält);
			
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Y: "));
			rad2.add(yFält);
			
			add(rad1);
			add(rad2);
		}
		
		public int getX() {
			return Integer.parseInt(xFält.getText());
		}
		
		public int getY() {
			return Integer.parseInt(yFält.getText());
		}
	}
	
	class KoordinaterLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
			AddKoordinatRuta koordinatRuta = new AddKoordinatRuta();
			int svar = JOptionPane.showConfirmDialog(KartaInterface.this, koordinatRuta, "Skriv koordinater", JOptionPane.OK_CANCEL_OPTION);
		
			int xKoordinat = koordinatRuta.getX();
			int yKoordinat = koordinatRuta.getY();
		}
	}
	
	class RemoveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave) {
				for(Plats p: markeradePlatser) {
					kartpanel.remove(p);
					platser.remove(p);
				}
				markeradePlatser.clear();
				repaint();
				
				// det ska även tas bort ur hashmappen?
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
		@Override
		public void actionPerformed(ActionEvent ave) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new KartaInterface();
	}

}
