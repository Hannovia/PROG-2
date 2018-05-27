package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import java.util.*;

public class KartaInterface extends JFrame {
	KartPanel kartpanel = null;
	JScrollPane scrollPane = null;
	JFileChooser filVäljare = new JFileChooser(".");
	JRadioButton namedRB;
	JRadioButton describedRB;
	JMenuBar menyBar;
	JMenu meny;
	JTextField sökFält;
	String valdKategori;
	JButton nyKnapp;
	MusLyss musLyss = new MusLyss();
	MarkeraLyss markeraLyss = new MarkeraLyss();
	File fil;
	int x;
	int y;
	public boolean osparadeÄndringar = false;

	String[] kategorier = { "Underground", "Bus", "Train" };
	JList<String> kategorilista = new JList<String>(kategorier);

	Map<String, Set<Plats>> kategoriSet = new HashMap<>();

	ArrayList<Plats> busPlatsLista = new ArrayList<>();
	ArrayList<Plats> undergroundPlatsLista = new ArrayList<>();
	ArrayList<Plats> trainPlatsLista = new ArrayList<>();

	Map<Position, Plats> platsMap = new HashMap<>();
	Map<String, ArrayList<Plats>> sökNamnLista = new HashMap<>();
	Map<Position, Plats> sökPosLista = new HashMap<>();
	HashSet<Plats> platser = new HashSet<>();
	HashSet<Plats> markeradePlatser = new HashSet<>();
	ArrayList<Plats> positioner;

	KartaInterface() {
		super("Inlupp 2: Hanna Severien, Viktor Fagerström Eriksson");
		// Meny
		menyBar = new JMenuBar();
		meny = new JMenu("Archive");
		menyBar.add(meny);

		JMenuItem newMap = new JMenuItem("New Map");
		meny.add(newMap);
		newMap.addActionListener(new NyKartaLyss());

		JMenuItem loadPlaces = new JMenuItem("Load Places");
		meny.add(loadPlaces);
		loadPlaces.addActionListener(new LaddaPlatserLyss());

		JMenuItem save = new JMenuItem("Save");
		meny.add(save);
		save.addActionListener(new SparaLyss());

		JMenuItem exit = new JMenuItem("Exit");
		meny.add(exit);
		exit.addActionListener(new ExitLyss());
		setJMenuBar(menyBar);

		// Filväljare
		FileFilter ff = new FileNameExtensionFilter("Textfil", "jpg", "txt", "png");
		filVäljare.setFileFilter(ff);

		// Interface - NORRA
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

		// Sökfält
		sökFält = new JTextField(10);
		norra.add(sökFält);
		String sökText = sökFält.getText();

		// Knappar
		JButton sökaKnapp = new JButton("Search");
		norra.add(sökaKnapp);
		sökaKnapp.addActionListener(new SökaLyss());

		JButton gömmaKnapp = new JButton("Hide");
		norra.add(gömmaKnapp);
		gömmaKnapp.addActionListener(new GömLyss());

		JButton removeKnapp = new JButton("Remove");
		norra.add(removeKnapp);
		removeKnapp.addActionListener(new RemoveLyss());

		JButton koordinaterKnapp = new JButton("Coordinates");
		norra.add(koordinaterKnapp);
		koordinaterKnapp.addActionListener(new KoordinaterLyss());

		// Interface - HÖGRA
		JPanel högra = new JPanel();
		JLabel kategorierText = new JLabel("Categories");
		högra.add(kategorierText);

		// Kategorilista - HÖGRA
		scrollPane = new JScrollPane(kategorilista);
		högra.add(scrollPane);
		högra.setLayout(new BoxLayout(högra, BoxLayout.Y_AXIS));
		add(högra, BorderLayout.EAST);
		kategorilista.setVisibleRowCount(3);

		JButton gömKategorier = new JButton("Hide Categories");
		högra.add(gömKategorier);
		gömKategorier.addActionListener(new GömKategoriLyss());

		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1140, 923);
		setVisible(true);

	}

	class NyKartaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int svar = filVäljare.showOpenDialog(KartaInterface.this);

			if (svar != JFileChooser.APPROVE_OPTION)
				return;

			fil = filVäljare.getSelectedFile();
			String filnamn = fil.getAbsolutePath();
			if (scrollPane != null)
				remove(scrollPane);

			kartpanel = new KartPanel(filnamn);
			scrollPane = new JScrollPane(kartpanel);

			add(scrollPane, BorderLayout.CENTER);

			validate();
			repaint();
		}
	}

	class SökaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Plats p : markeradePlatser) {
				p.Avmarkera();
			}
			markeradePlatser.clear();

			String sökOrd = sökFält.getText();
			ArrayList<Plats> platsNamn = sökNamnLista.get(sökOrd);
			if (platsNamn == null || platsNamn.isEmpty()) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel", "Fel", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (Plats p : platsNamn) {
				p.Markera();
				p.setVisible(true);
				markeradePlatser.add(p);
			}

			repaint();

		}
	}

	class GömKategoriLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			String kategori = kategorilista.getSelectedValue();
			
			if(kartpanel == null) {
				return;
			}

			Set<Plats> plats = kategoriSet.get(kategori);
			
			if (kategoriSet.isEmpty()) {
				return;
			}

			for (Plats p : plats) {
				p.setVisible(false);
			}
			kategorilista.clearSelection();

		}
	}

	class NyLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (kartpanel == null)
				return;
			kartpanel.addMouseListener(musLyss);
			nyKnapp.setEnabled(false);
			kartpanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	class AddDescribedPlace extends JPanel {
		JTextField namnFält = new JTextField(10);
		JTextField beskrivningFält = new JTextField(30);

		AddDescribedPlace() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn: "));
			rad1.add(namnFält);
			add(rad1);
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Beskrivning: "));
			rad2.add(beskrivningFält);
			add(rad2);
		}

		public String getBeskrivning() {
			return beskrivningFält.getText();
		}
	}

	class MusLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			x = mev.getX();
			y = mev.getY();
			AddPlats();
			kategorilista.clearSelection();
			kartpanel.removeMouseListener(this);
		}
	}

	public void AddPlats() {
		nyKnapp.setEnabled(true);

		valdKategori = kategorilista.getSelectedValue();
		if (valdKategori == null) {
			valdKategori = "Ingen";
		}
		kartpanel.setCursor(Cursor.getDefaultCursor());

		if (namedRB.isSelected()) {
			String namn = JOptionPane.showInputDialog(KartaInterface.this, "Ange namn på ny plats:");
			System.out.println(namn);

			if (namn == null) {
				return;

			} else if (namn.isEmpty()) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if(platsMap.contains(pos, p )) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel. Det finns redan en plats här!",
						"Fel. Det finns redan en plats här!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String typ = "Named";
			Position pos = new Position(x, y);
			String kategori = kategorilista.getSelectedValue();
			if (kategori == null) {
				kategori = "None";
			}
			Plats namngivenPlats = new NamngivenPlats(typ, kategori, namn, pos);

			ArrayList<Plats> platsNamn = sökNamnLista.get(namn);

			if (!sökNamnLista.containsKey(namn)) {
				platsNamn = new ArrayList<Plats>();
				sökNamnLista.put(namn, platsNamn);
			}

			platsNamn.add(namngivenPlats);

			sökPosLista.putIfAbsent(pos, namngivenPlats);
			Set<Plats> Platser = kategoriSet.get(valdKategori);
			if (Platser == null) {
				Platser = new HashSet<Plats>();
				kategoriSet.put(valdKategori, Platser);
			}
			Platser.add(namngivenPlats);

			platsMap.put(pos, namngivenPlats);
			platser.add(namngivenPlats);
			kartpanel.add(namngivenPlats);
			namngivenPlats.addMouseListener(new MarkeraLyss());
			kartpanel.validate();
			kartpanel.repaint();
			osparadeÄndringar = true;

		} else if (describedRB.isSelected()) {

			AddDescribedPlace describedRuta = new AddDescribedPlace();
			int svar = JOptionPane.showConfirmDialog(KartaInterface.this, describedRuta, "Ny ruta",
					JOptionPane.OK_CANCEL_OPTION);

			if (svar != JOptionPane.OK_OPTION)
				return;

			String namn = describedRuta.namnFält.getText();
			String beskrivning = describedRuta.getBeskrivning();
			String typ = "Described";

			if (namn == null || beskrivning == null) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data",
						JOptionPane.ERROR_MESSAGE);

				return;
			} else if (namn.isEmpty() || beskrivning.isEmpty()) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Position pos = new Position(x, y);
			String kategori = kategorilista.getSelectedValue();
			if (kategori == null) {
				kategori = "None";
			}
			Plats beskrivenPlats = new BeskrivenPlats(typ, kategori, namn, pos, beskrivning);

			ArrayList<Plats> platsNamn = sökNamnLista.get(namn);
			if (!sökNamnLista.containsKey(namn)) {
				platsNamn = new ArrayList<Plats>();
				sökNamnLista.put(namn, platsNamn);
			}

			platsNamn.add(beskrivenPlats);

			sökPosLista.putIfAbsent(pos, beskrivenPlats);



			Set<Plats> Platser = kategoriSet.get(valdKategori);
			if (Platser == null) {
				Platser = new HashSet<Plats>();
				kategoriSet.put(valdKategori, Platser);
			}
			Platser.add(beskrivenPlats);

			platsMap.put(pos, beskrivenPlats);
			platser.add(beskrivenPlats);
			kartpanel.add(beskrivenPlats);
			beskrivenPlats.addMouseListener(new MarkeraLyss());
			kartpanel.validate();
			kartpanel.repaint();
			osparadeÄndringar = true;
		}
	}

	public class MarkeraLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {

			Plats p = (Plats) mev.getSource();
			if (mev.getButton() == MouseEvent.BUTTON1) {
				if (markeradePlatser.contains(p)) {
					markeradePlatser.remove(p);
					p.setMarkerad();
					repaint();
				} else {
					markeradePlatser.add(p);
					p.setMarkerad();
					repaint();
				}
				repaint();
			} else if (mev.getButton() == MouseEvent.BUTTON3) {
				p.getBeskrivning();
			}
		}
	}

	public class GömLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			for (Plats p : markeradePlatser) {
				p.setVisible(false);
			}
			markeradePlatser.clear();
			kartpanel.repaint();
		}
	}

	class AddKoordinatRuta extends JPanel {
		JTextField xFält = new JTextField(6);
		JTextField yFält = new JTextField(6);

		AddKoordinatRuta() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("X: "));
			rad1.add(xFält);

			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Y: "));
			rad2.add(yFält);

			add(rad1);
			add(rad2);
		}

		public int getXFält() {
			return Integer.parseInt(this.xFält.getText());
		}

		public int getYFält() {
			return Integer.parseInt(this.yFält.getText());
		}
	}

	class KoordinaterLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			AddKoordinatRuta koordinatRuta = new AddKoordinatRuta();
			int svar = JOptionPane.showConfirmDialog(KartaInterface.this, koordinatRuta, "Skriv koordinater",
					JOptionPane.OK_CANCEL_OPTION);

			if (svar != JOptionPane.OK_OPTION)
				return;

			int xKoordinat = koordinatRuta.getXFält();
			int yKoordinat = koordinatRuta.getYFält();
			System.out.println(xKoordinat + " " + yKoordinat);
			Position pos = new Position(xKoordinat, yKoordinat);

			for (Plats p : markeradePlatser) {
				p.Avmarkera();
			}
			markeradePlatser.clear();

			Plats p = sökPosLista.get(pos);
			if (p == null) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel. Det finns ingen plats här.",
						"Fel. Det finns ingen plats här.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			p.Markera();
			p.setVisible(true);
			markeradePlatser.add(p);
			repaint();
		}
	}

	class RemoveLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Plats p : markeradePlatser) {
				kartpanel.remove(p);
				platser.remove(p);
			}
			markeradePlatser.clear();
			platsMap.clear();
			repaint();
		}
	}

	class LaddaPlatserLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {
				if (osparadeÄndringar) {
					int svar = JOptionPane.showConfirmDialog(KartaInterface.this,
							"Du har osparade ändringar, vill du fortsätta?");
					if (svar != JOptionPane.OK_OPTION)
						return;
					for (Plats p : platsMap.values()) {
						kartpanel.remove(p);
					}

					platsMap.clear();
					markeradePlatser.clear();
					platser.clear();
				}

				int svar2 = filVäljare.showOpenDialog(KartaInterface.this);
				if (svar2 != JFileChooser.APPROVE_OPTION)
					return;
				
				File file = filVäljare.getSelectedFile();
				String filnamn = file.getAbsolutePath();
				FileReader in = new FileReader(filnamn); 
				BufferedReader br = new BufferedReader(in);
				String line;
				while ((line = br.readLine()) != null) {
					String[] tokens = line.split(",");

					if (tokens[0].equals("Named")) {
						String typ = (tokens[0]);
						valdKategori = (tokens[1]);
						int x = Integer.parseInt(tokens[2]);
						int y = Integer.parseInt(tokens[3]);
						String namn = tokens[4];
						Position pos = new Position(x, y);

						Plats p = new NamngivenPlats(typ, valdKategori, namn, pos);
						addFrånLadd(p);
						platser.add(p);
						kartpanel.add(p);
						platsMap.put(pos, p);
						p.addMouseListener(new MarkeraLyss());
						p.setVisible(true);
						kartpanel.validate();
						kartpanel.repaint();

					} else if (tokens[0].equals("Described")) {
						String typ = (tokens[0]);
						valdKategori = (tokens[1]);
						int x = Integer.parseInt(tokens[2]);
						int y = Integer.parseInt(tokens[3]);
						Position pos = new Position(x, y);
						String namn = tokens[4];
						String beskrivning = (tokens[5]);

						Plats p = new BeskrivenPlats(typ, valdKategori, namn, pos, beskrivning);
						addFrånLadd(p);
						platser.add(p);
						kartpanel.add(p);
						platsMap.put(pos, p);
						p.addMouseListener(new MarkeraLyss());
						p.setVisible(true);
						kartpanel.validate();
						kartpanel.repaint();
					}
				}

				in.close();
				br.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fil kan ej öppnas");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel");
			}

		}
	}

	private void addFrånLadd(Plats plats) {
		System.out.println("test");
		platsMap.put(plats.getPos(), plats);
		ArrayList<Plats> sökNamn = sökNamnLista.get(plats.getNamn());
		if (sökNamn == null) {
			sökNamn = new ArrayList<Plats>();
			sökNamnLista.put(plats.getNamn(), sökNamn);
		}
		sökNamn.add(plats);

		Set<Plats> Platser = kategoriSet.get(plats.getKategori());
		if (Platser == null) {
			Platser = new HashSet<Plats>();
			kategoriSet.put(plats.getKategori(), Platser);
		}
		Platser.add(plats);

	}

	class SparaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			spara(platsMap);

		}
	}

	void spara(Map<Position, Plats> vad) {
		int svar = filVäljare.showSaveDialog(KartaInterface.this);
		if (svar == JFileChooser.APPROVE_OPTION) {
			File f = filVäljare.getSelectedFile();
			String filnamn = f.getAbsolutePath();

			try {
				FileWriter utfil = new FileWriter(filnamn);
				PrintWriter out = new PrintWriter(utfil);
				for (Plats p : platser)
					if (p instanceof NamngivenPlats) {
						out.println(p.typ + "," + p.valdKategori + "," + p.getPosX() + "," + p.getPosY() + "," + p.namn);
					} else {
						out.println(p.typ + "," + p.valdKategori +  "," + p.getPosX() + "," + p.getPosY() + "," + p.namn + "," +
								 p.getBeskrivningText());
					}
				out.close();
				osparadeÄndringar = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(KartaInterface.this, "Fel");
			}
		}

	}

	class ExitLyss implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ave) {
			if (osparadeÄndringar) {
				int svar = JOptionPane.showConfirmDialog(KartaInterface.this,
						"Du har osparade ändringar, vill du fortsätta?");
				if (svar != JOptionPane.OK_OPTION)
					return;
				for (Plats p : platsMap.values()) {
					kartpanel.remove(p);
				}

				platsMap.clear();
				markeradePlatser.clear();
				platser.clear();
			}
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new KartaInterface();
	}

}
