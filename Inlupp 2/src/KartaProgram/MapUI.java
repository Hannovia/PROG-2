package KartaProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import java.util.*;

public class MapUI extends JFrame {
	MapPanel mapPanel = null;
	JScrollPane scrollPane = null;
	JFileChooser fileChooser = new JFileChooser(".");
	JRadioButton namedRB;
	JRadioButton describedRB;
	JMenuBar menuBar;
	JMenu menu;
	JTextField searchBar;
	String choosenCategory;
	String errorMessage;
	String category;
	JButton newButton;
	MouseListener mouseListener = new MouseListener();
	HighlightListener highlightListener = new HighlightListener();
	public boolean unsavedChanges = false;
	File file;
	int x;
	int y;
	
	// ändra plats till place, namedplace, descriedplace klasserna

	String[] categories = { "Underground", "Bus", "Train" };
	JList<String> categoryList = new JList<String>(categories);

	Map<String, Set<Plats>> categorySet = new HashMap<>();

	ArrayList<Plats> busPlaceList = new ArrayList<>();
	ArrayList<Plats> undergroundPlaceList = new ArrayList<>();
	ArrayList<Plats> trainPlaceList = new ArrayList<>();

	Map<Position, Plats> placeMap = new HashMap<>();
	Map<String, ArrayList<Plats>> searchByNameList = new HashMap<>();
	Map<Position, Plats> searchPosList = new HashMap<>();
	HashSet<Plats> places = new HashSet<>();
	HashSet<Plats> highlightedPlaces = new HashSet<>();
	ArrayList<Plats> positions;

	MapUI() {
		super("Inlupp 2: Hanna Severien, Viktor Fagerström Eriksson");
		// Meny
		menuBar = new JMenuBar();
		menu = new JMenu("Archive");
		menuBar.add(menu);

		JMenuItem newMap = new JMenuItem("New Map");
		menu.add(newMap);
		newMap.addActionListener(new NewMapListener());

		JMenuItem loadPlaces = new JMenuItem("Load Places");
		menu.add(loadPlaces);
		loadPlaces.addActionListener(new LoadPlacesListener());

		JMenuItem save = new JMenuItem("Save");
		menu.add(save);
		save.addActionListener(new SaveListener());

		JMenuItem exit = new JMenuItem("Exit");
		menu.add(exit);
		exit.addActionListener(new ExitListener());
		setJMenuBar(menuBar);

		// Filväljare
		FileFilter ff = new FileNameExtensionFilter("Textfil", "jpg", "txt", "png");
		fileChooser.setFileFilter(ff);

		// Interface - NORRA
		JPanel north = new JPanel();
		add(north, BorderLayout.NORTH);
		newButton = new JButton("New");
		north.add(newButton);
		newButton.addActionListener(new NewListener());

		namedRB = new JRadioButton("Named", true);
		describedRB = new JRadioButton("Described");
		north.add(namedRB);
		north.add(describedRB);

		ButtonGroup sortingGroup = new ButtonGroup();
		sortingGroup.add(namedRB);
		sortingGroup.add(describedRB);

		// Sökfält
		searchBar = new JTextField(10);
		north.add(searchBar);
		String searchText = searchBar.getText();

		// Knappar
		JButton searchButton = new JButton("Search");
		north.add(searchButton);
		searchButton.addActionListener(new SearchListener());

		JButton hideButton = new JButton("Hide");
		north.add(hideButton);
		hideButton.addActionListener(new HideListener());

		JButton removeButton = new JButton("Remove");
		north.add(removeButton);
		removeButton.addActionListener(new RemoveListener());

		JButton coordinatesButton = new JButton("Coordinates");
		north.add(coordinatesButton);
		coordinatesButton.addActionListener(new CoordinatesListener());

		// Interface - east
		JPanel east = new JPanel();
		JLabel categoriesLabel = new JLabel("Categories");
		east.add(categoriesLabel);
		scrollPane = new JScrollPane(categoryList);
		east.add(scrollPane);
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		add(east, BorderLayout.EAST);
		categoryList.setVisibleRowCount(3);

		JButton hideCategoriesButton = new JButton("Hide Categories");
		east.add(hideCategoriesButton);
		hideCategoriesButton.addActionListener(new HideCategoriesListener());

		// Fönster
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1140, 923);
		setVisible(true);

	}

	class NewMapListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			int answer = fileChooser.showOpenDialog(MapUI.this);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;

			file = fileChooser.getSelectedFile();
			String filename = file.getAbsolutePath();
			if (scrollPane != null)
				remove(scrollPane);

			mapPanel = new MapPanel(filename);
			scrollPane = new JScrollPane(mapPanel);

			add(scrollPane, BorderLayout.CENTER);

			validate();
			repaint();
		}
	}

	class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Plats p : highlightedPlaces) {
				p.UnMark();
			}
			highlightedPlaces.clear();

			String searchWord = searchBar.getText();
			ArrayList<Plats> placeName = searchByNameList.get(searchWord);
			if (placeName == null || placeName.isEmpty()) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel", "Fel", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (Plats p : placeName) {
				p.Highlight();
				p.setVisible(true);
				highlightedPlaces.add(p);
			}

			repaint();

		}
	}

	class HideCategoriesListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			String category = categoryList.getSelectedValue();
			
			if(mapPanel == null) {
				return;
			}

			Set<Plats> place = categorySet.get(category);
			
			if (categorySet.isEmpty()) {
				return;
			}

			for (Plats p : place) {
				p.setVisible(false);
			}
			
			categoryList.clearSelection();

		}
	}

	class NewListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (mapPanel == null)
				return;
			mapPanel.addMouseListener(mouseListener);
			newButton.setEnabled(false);
			mapPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	class AddDescribedPlacePanel extends JPanel {
		JTextField nameField = new JTextField(10);
		JTextField descriptionField = new JTextField(30);

		AddDescribedPlacePanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel row1 = new JPanel();
			row1.add(new JLabel("Namn: "));
			row1.add(nameField);
			add(row1);
			JPanel row2 = new JPanel();
			row2.add(new JLabel("Beskrivning: "));
			row2.add(descriptionField);
			add(row2);
		}

		public String getDescription() {
			return descriptionField.getText();
		}
	}

	class MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			x = mev.getX();
			y = mev.getY();
			addPlace();
			categoryList.clearSelection();
			mapPanel.removeMouseListener(this);
		}
	}

	public void unsavedChanges() {
		// skriv saker
	}
	
	public void inputErrorMessage() {
		errorMessage = "Fel! Mata in rätt typ av data";
		JOptionPane.showMessageDialog(MapUI.this, errorMessage, errorMessage, JOptionPane.ERROR_MESSAGE);
	}
	
	public void excistingPlace() {
		errorMessage = "Fel! Det finns redan en plats här!";
		JOptionPane.showMessageDialog(MapUI.this, errorMessage, errorMessage, JOptionPane.ERROR_MESSAGE);
	}
	
	public void addNamedPlace() {
		String name = JOptionPane.showInputDialog(MapUI.this, "Ange namn på ny plats: ");
		Position pos = new Position(x, y);
		String type = "Named";
		String category = categoryList.getSelectedValue();

		if (name == null || name.isEmpty()) {
			inputErrorMessage();
			return;
		} else if (placeMap.containsKey(pos)) {
			excistingPlace();
		}
		
		if (category == null) {
			category = "None";
		}
		
		Plats namedPlace = new NamngivenPlats(type, category, name, pos);
		
		// Sökning med namn
		ArrayList<Plats> placeName = searchByNameList.get(name);

		if (!searchByNameList.containsKey(name)) {
			placeName = new ArrayList<Plats>();
			searchByNameList.put(name, placeName);
		}

		placeName.add(namedPlace);
		
		//Sökning med position
		searchPosList.putIfAbsent(pos, namedPlace);
		Set<Plats> placesSet = categorySet.get(choosenCategory);
		if (placesSet == null) {
			placesSet = new HashSet<Plats>();
			categorySet.put(choosenCategory, placesSet);
		}
		placesSet.add(namedPlace);
		
		placeMap.put(pos, namedPlace);
		places.add(namedPlace);
		mapPanel.add(namedPlace);
		namedPlace.addMouseListener(new HighlightListener());
		mapPanel.validate();
		mapPanel.repaint();
		unsavedChanges = true;


	}

	public void addDescribedPlace() {
		AddDescribedPlacePanel describedPanel = new AddDescribedPlacePanel();
		int answer = JOptionPane.showConfirmDialog(MapUI.this, describedPanel, "Ny ruta", JOptionPane.OK_CANCEL_OPTION);
		
		if (answer != JOptionPane.OK_OPTION)
			return;
		
		String name = describedPanel.nameField.getText();
		String description = describedPanel.getDescription();
		String type = "Described";
		
		if (name == null || description == null || name.isEmpty() || description.isEmpty()) {
			inputErrorMessage();
			return;
		}
		
		Position pos = new Position(x,y);
		String category = categoryList.getSelectedValue();
		if (category == null) {
			category = "None";
		}
		Plats describedPlace = new BeskrivenPlats(type, category, name, pos, description);
		
		ArrayList<Plats> placeName = searchByNameList.get(name);
		if (!searchByNameList.containsKey(name)) {
			placeName = new ArrayList<Plats>();
			searchByNameList.put(name, placeName);
		}

		placeName.add(describedPlace);

		searchPosList.putIfAbsent(pos, describedPlace);



		Set<Plats> placesSet = categorySet.get(choosenCategory);
		if (placesSet == null) {
			placesSet = new HashSet<Plats>();
			categorySet.put(choosenCategory, placesSet);
		}
		placesSet.add(describedPlace);

		placeMap.put(pos, describedPlace);
		places.add(describedPlace);
		mapPanel.add(describedPlace);
		describedPlace.addMouseListener(new HighlightListener());
		mapPanel.validate();
		mapPanel.repaint();
		unsavedChanges = true;
	}
	
	public void addPlace() {
		newButton.setEnabled(true);

		choosenCategory = categoryList.getSelectedValue();
		if (choosenCategory == null) {
			choosenCategory = "Ingen";
		}
		mapPanel.setCursor(Cursor.getDefaultCursor());

		if (namedRB.isSelected()) {
			addNamedPlace();

		} else if (describedRB.isSelected()) {
			
			addDescribedPlace();
			
		}
	}

	public class HighlightListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {

			Plats p = (Plats) mev.getSource();
			if (mev.getButton() == MouseEvent.BUTTON1) {
				if (highlightedPlaces.contains(p)) {
					highlightedPlaces.remove(p);
					p.setHighlighted();
					repaint();
				} else {
					highlightedPlaces.add(p);
					p.setHighlighted();
					repaint();
				}
				repaint();
			} else if (mev.getButton() == MouseEvent.BUTTON3) {
				p.getDescription();
			}
		}
	}

	public class HideListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			for (Plats p : highlightedPlaces) {
				p.setVisible(false);
			}
			highlightedPlaces.clear();
			mapPanel.repaint();
		}
	}

	class AddCoordinatesPanel extends JPanel {
		JTextField xField = new JTextField(6);
		JTextField yField = new JTextField(6);

		AddCoordinatesPanel() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			JPanel row1 = new JPanel();
			row1.add(new JLabel("X: "));
			row1.add(xField);

			JPanel row2 = new JPanel();
			row2.add(new JLabel("Y: "));
			row2.add(yField);

			add(row1);
			add(row2);
		}

		public int getXField() {
			return Integer.parseInt(this.xField.getText());
		}

		public int getYField() {
			return Integer.parseInt(this.yField.getText());
		}
	}

	class CoordinatesListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			AddCoordinatesPanel coordinatesPanel = new AddCoordinatesPanel();
			try {
				int answer = JOptionPane.showConfirmDialog(MapUI.this, coordinatesPanel, "Skriv koordinater",
						JOptionPane.OK_CANCEL_OPTION);

				if (answer != JOptionPane.OK_OPTION)
					return;

				int xCoordinate = coordinatesPanel.getXField();
				int yCoordinate = coordinatesPanel.getYField();
				Position pos = new Position(xCoordinate, yCoordinate);

				for (Plats p : highlightedPlaces) {
					p.UnMark();
				}
				highlightedPlaces.clear();

				Plats p = searchPosList.get(pos);
				if (p == null) {
					JOptionPane.showMessageDialog(MapUI.this, "Fel. Det finns ingen plats här.",
							"Fel. Det finns ingen plats här.", JOptionPane.ERROR_MESSAGE);
					return;
				}
				p.Highlight();
				p.setVisible(true);
				highlightedPlaces.add(p);
				repaint();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel! Mata in rätt data", "Fel! Mata in rätt data",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Plats p : highlightedPlaces) {
				mapPanel.remove(p);
				places.remove(p);
			}
			highlightedPlaces.clear();
			placeMap.clear();
			repaint();
		}
	}
	

	class LoadPlacesListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {
				if (unsavedChanges) {
					int answer = JOptionPane.showConfirmDialog(MapUI.this, "Du har osparade ändringar, vill du fortsätta?");
					if (answer != JOptionPane.OK_OPTION)
						return;
					for (Plats p : placeMap.values()) {
						mapPanel.remove(p);
					}

					placeMap.clear();
					highlightedPlaces.clear();
					places.clear();
				}

				int answer2 = fileChooser.showOpenDialog(MapUI.this);
				if (answer2 != JFileChooser.APPROVE_OPTION)
					return;

				File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				FileReader in = new FileReader(filename);
				BufferedReader br = new BufferedReader(in);
				String line;

				while ((line = br.readLine()) != null) {
					String[] tokens = line.split(",");
					
					String type = (tokens[0]);
					choosenCategory = (tokens[1]);
					String name = tokens[2];
					int x = Integer.parseInt(tokens[3]);
					int y = Integer.parseInt(tokens[4]);
					Position pos = new Position(x, y);
					Plats p;

					if (tokens[0].equals("Named")) {
						p = new NamngivenPlats(type, choosenCategory, name, pos);

						addFromLoad(p);
						places.add(p);
						mapPanel.add(p);
						placeMap.put(pos, p);
						p.addMouseListener(new HighlightListener());
						p.setVisible(true);
					
					} else if (tokens[0].equals("Described")){
						String description = (tokens[5]);
						p = new BeskrivenPlats(type, choosenCategory, name, pos, description);

						addFromLoad(p);
						places.add(p);
						mapPanel.add(p);
						placeMap.put(pos, p);
						p.addMouseListener(new HighlightListener());
						p.setVisible(true);
						
						
					}
				}
				
				mapPanel.validate();
				mapPanel.repaint();
				in.close();
				br.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fil kan ej öppnas");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel");
			}
		}
	}
	

	private void addFromLoad(Plats plats) {
		placeMap.put(plats.getPos(), plats);
		ArrayList<Plats> searchName = searchByNameList.get(plats.getNamn());
		if (searchName == null) {
			searchName = new ArrayList<Plats>();
			searchByNameList.put(plats.getNamn(), searchName);
		}
		searchName.add(plats);

		Set<Plats> places = categorySet.get(plats.getCategory());
		if (places == null) {
			places = new HashSet<Plats>();
			categorySet.put(plats.getCategory(), places);
		}
		places.add(plats);

	}

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			save(placeMap);

		}
	}

	void save(Map<Position, Plats> what) {
		int svar = fileChooser.showSaveDialog(MapUI.this);
		if (svar == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String filename = f.getAbsolutePath();

			try {
				FileWriter output = new FileWriter(filename);
				PrintWriter out = new PrintWriter(output);
				for (Plats p : places)
					if (p instanceof NamngivenPlats) {
						out.println(p.typ + "," + p.valdKategori + "," + p.getPosX() + "," + p.getPosY() + "," + p.namn);
					} else {
						out.println(p.typ + "," + p.valdKategori +  "," + p.getPosX() + "," + p.getPosY() + "," + p.namn + "," +
								 p.getDescriptionText());
					}
				out.close();
				unsavedChanges = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel");
			}
		}

	}

	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ave) {
			if (unsavedChanges) {
				int answer = JOptionPane.showConfirmDialog(MapUI.this,
						"Du har osparade ändringar, vill du fortsätta?");
				if (answer != JOptionPane.OK_OPTION)
					return;
				for (Plats p : placeMap.values()) {
					mapPanel.remove(p);
				}

				placeMap.clear();
				highlightedPlaces.clear();
				places.clear();
			}
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new MapUI();
	}

}
