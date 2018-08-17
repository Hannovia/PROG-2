package KartaProgram;

import javax.swing.*;
import javax.swing.event.AncestorListener;

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
	CategoryListListener categoryListener = new CategoryListListener();
	HighlightListener highlightListener = new HighlightListener();
	public boolean unsavedChanges = false;
	public boolean openMap = false;
	File file;
	int x;
	int y;

	String[] categories = {"Underground", "Bus", "Train"};
	JList<String> categoryList = new JList<String>(categories);

	Map<String, Set<Place>> categorySet = new HashMap<>();

	ArrayList<Place> busPlaceList = new ArrayList<>();
	ArrayList<Place> undergroundPlaceList = new ArrayList<>();
	ArrayList<Place> trainPlaceList = new ArrayList<>();

	Map<Position, Place> placeMap = new HashMap<>();
	Map<String, ArrayList<Place>> searchByNameList = new HashMap<>();
	Map<Position, Place> searchPosList = new HashMap<>();
	HashSet<Place> places = new HashSet<>();
	HashSet<Place> highlightedPlaces = new HashSet<>();
	ArrayList<Place> positions;

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
		categoryList.addMouseListener(categoryListener);

		JButton hideCategoriesButton = new JButton("Hide Categories");
		east.add(hideCategoriesButton);
		hideCategoriesButton.addActionListener(new HideCategoriesListener());

		// Fönster
		addWindowListener(new WindowExit());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1140, 923);
		setVisible(true);

	}
	
	// Fungerar inte
	class CategoryListListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			try {

				String category = categoryList.getSelectedValue();

				if (mapPanel == null) {
					return;
				}

				Set<Place> place = categorySet.get(category);

				if (categorySet.isEmpty() || category == null) {
					return;
				}

				for (Place p : place) {
					p.setVisible(true);
				}


			} catch (NumberFormatException e) {
				inputErrorMessage();
			}
		}
	}	

	class NewMapListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if(unsavedChanges) {
				int answer = JOptionPane.showConfirmDialog(MapUI.this, "Du har osparade ändringar, vill du fortsätta?");

				if (answer == JOptionPane.YES_OPTION) {
					for (Place p : placeMap.values()) {
						mapPanel.remove(p);
						mapPanel.repaint();
					}
				placeMap.clear();
				highlightedPlaces.clear();
				places.clear();
				} else {
					return;
				}
			}
			int answer = fileChooser.showOpenDialog(MapUI.this);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			
			for (Place p: places) {
				if(places.contains(p)) {
					mapPanel.remove(p);
					places.remove(p);
					placeMap.remove(p);
					repaint();
				}
				return;
			}

			file = fileChooser.getSelectedFile();
			String filename = file.getAbsolutePath();
			if (scrollPane != null)
				remove(scrollPane);

			mapPanel = new MapPanel(filename);
			scrollPane = new JScrollPane(mapPanel);

			add(scrollPane, BorderLayout.CENTER);

			validate();
			repaint();
			openMap = true; 
			unsavedChanges = false;
		}
	}

	class SearchListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Place p : highlightedPlaces) {
				p.UnMark();
			}
			highlightedPlaces.clear();

			String searchWord = searchBar.getText();
			ArrayList<Place> placeName = searchByNameList.get(searchWord);
			if (placeName == null || placeName.isEmpty()) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel", "Fel", JOptionPane.ERROR_MESSAGE);
				return;
			}
			for (Place p : placeName) {
				p.Highlight();
				p.setVisible(true);
				highlightedPlaces.add(p);
			}

			repaint();

		}
	}

	class HideCategoriesListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {

				String category = categoryList.getSelectedValue();

				if (mapPanel == null) {
					return;
				}

				Set<Place> place = categorySet.get(category);

				if (categorySet.isEmpty() || category == null) {
					return;
				}

				for (Place p : place) {
					p.setVisible(false);
				}

				categoryList.clearSelection();

			} catch (NumberFormatException e) {
				inputErrorMessage();
			}
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
		mapPanel.validate();
		mapPanel.repaint();
		unsavedChanges = true;
	}

	class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			x = mev.getX();
			y = mev.getY();
			addPlace();
			categoryList.clearSelection();
			mapPanel.removeMouseListener(this);
		}
	}
	public void inputErrorMessage() {
		errorMessage = "Fel! Mata in rätt typ av data";
		JOptionPane.showMessageDialog(MapUI.this, errorMessage, errorMessage, JOptionPane.ERROR_MESSAGE);
	}
	
	public void excistingPlace() {
		errorMessage = "Fel! Det finns redan en plats här!";
		JOptionPane.showMessageDialog(MapUI.this, errorMessage, errorMessage, JOptionPane.ERROR_MESSAGE);
		return;
	}
	
	public void noMapOpen() {
		if (openMap == false) {
			JOptionPane.showMessageDialog(MapUI.this, "Du måste ladda in en karta först!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	public void addNamedPlace() {
		String name = JOptionPane.showInputDialog(MapUI.this, "Ange namn på ny plats: ", "Namn");
		Position pos = new Position(x, y);
		String type = "Named";
		String category = categoryList.getSelectedValue();
		if (placeMap.containsKey(pos)) {
			excistingPlace();
		}
		if (name == null || name.isEmpty()) {
			inputErrorMessage();
			return;
		}
		if (category == null) {
			category = "None";
		}
		Place place = new NamedPlace(type, category, name, pos);
		searchByName(name, place);
		searchPosList.putIfAbsent(pos, place);
		hideByCategory(place);
		placeMap.put(pos, place);
		places.add(place);
		mapPanel.add(place);
		place.addMouseListener(new HighlightListener());
	}

	public void addDescribedPlace() {
		AddDescribedPlacePanel describedPanel = new AddDescribedPlacePanel();
		int answer = JOptionPane.showConfirmDialog(MapUI.this, describedPanel, "Ny plats", JOptionPane.OK_CANCEL_OPTION);
		
		if (answer != JOptionPane.OK_OPTION)
			return;
		
		String name = describedPanel.nameField.getText();
		String description = describedPanel.getDescription();
		String type = "Described";
		Position pos = new Position(x,y);
		String category = categoryList.getSelectedValue();
		
		if (name == null || description == null || name.isEmpty() || description.isEmpty()) {
			inputErrorMessage();
			return;
			
		} else if (placeMap.containsKey(pos)) {
			excistingPlace();
		}
		
		if (category == null) {
			category = "None";
		}
		
		Place place = new DescribedPlace(type, category, name, pos, description);
		searchByName(name, place);
		searchPosList.putIfAbsent(pos, place);
		hideByCategory(place);
		placeMap.put(pos, place);
		places.add(place);
		mapPanel.add(place);
		place.addMouseListener(new HighlightListener());
	}
	
	public void searchByName(String name, Place place) {
		ArrayList<Place> placeName = searchByNameList.get(name);
		if (!searchByNameList.containsKey(name)) {
			placeName = new ArrayList<Place>();
			searchByNameList.put(name, placeName);
		}
		placeName.add(place);
		
	}
	
	public void hideByCategory(Place place) {
		Set<Place> placesSet = categorySet.get(choosenCategory);
		if (placesSet == null) {
			placesSet = new HashSet<Place>();
			categorySet.put(choosenCategory, placesSet);
		}
		placesSet.add(place);
	}
	
	public class HighlightListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {

			Place p = (Place) mev.getSource();
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

			for (Place p : highlightedPlaces) {
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
				int answer = JOptionPane.showConfirmDialog(MapUI.this, coordinatesPanel, "Skriv koordinater", JOptionPane.OK_CANCEL_OPTION);

				if (answer != JOptionPane.OK_OPTION)
					return;

				int xCoordinate = coordinatesPanel.getXField();
				int yCoordinate = coordinatesPanel.getYField();
				Position pos = new Position(xCoordinate, yCoordinate);

				for (Place p : highlightedPlaces) {
					p.UnMark();
				}
				highlightedPlaces.clear();

				Place p = searchPosList.get(pos);
				if (p == null) {
					inputErrorMessage();
					return;
				}
				p.Highlight();
				p.setVisible(true);
				highlightedPlaces.add(p);
			} catch (NumberFormatException e) {
				inputErrorMessage();
			}
			
			repaint();
		}
	}

	class RemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Place p : highlightedPlaces) {
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
			noMapOpen();
			try {
				if(unsavedChanges) {
					int answer = JOptionPane.showConfirmDialog(MapUI.this, "Du har osparade ändringar, vill du fortsätta?");

					if (answer == JOptionPane.YES_OPTION) {
						for (Place p : placeMap.values()) {
							mapPanel.remove(p);
							mapPanel.repaint();
						}
					placeMap.clear();
					highlightedPlaces.clear();
					places.clear();
					} else {
						return;
					}
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
					int x = Integer.parseInt(tokens[2]);
					int y = Integer.parseInt(tokens[3]);
					String name = tokens[4];
					Position pos = new Position(x, y);
					Place p = null;

					if (tokens[0].equals("Named")) {
						p = new NamedPlace(type, choosenCategory, name, pos);
					
					} else if (tokens[0].equals("Described")){
						String description = (tokens[5]);
						p = new DescribedPlace(type, choosenCategory, name, pos, description);
					}
					
					if(p != null) {
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
				repaint();
				in.close();
				br.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fil kan ej öppnas");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel");
			}
		}
	}
	
	
	private void addFromLoad(Place place) {
		placeMap.put(place.getPos(), place);
		ArrayList<Place> searchName = searchByNameList.get(place.getNamn());
		if (searchName == null) {
			searchName = new ArrayList<Place>();
			searchByNameList.put(place.getNamn(), searchName);
		}
		searchName.add(place);

		Set<Place> places = categorySet.get(place.getCategory());
		if (places == null) {
			places = new HashSet<Place>();
			categorySet.put(place.getCategory(), places);
		}
		places.add(place);

	}

	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			save();
		}
	}

	void save() {
		noMapOpen();
		int answer = fileChooser.showSaveDialog(MapUI.this);
		if (answer == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String filename = f.getAbsolutePath();

			try {
				FileWriter output = new FileWriter(filename);
				PrintWriter out = new PrintWriter(output);
				for (Place p : places)
					if (p instanceof NamedPlace) {
						out.println(p.type + "," + p.chosenCategory + "," + p.getPosX() + "," + p.getPosY() + "," + p.name);
					} else {
						out.println(p.type + "," + p.chosenCategory +  "," + p.getPosX() + "," + p.getPosY() + "," + p.name + "," + p.getDescriptionText());
					}
				out.close();
				unsavedChanges = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(MapUI.this, "Fel");
			}
		}

	}
	
	public void exit() {
		if(unsavedChanges) {
			int answer = JOptionPane.showConfirmDialog(MapUI.this, "Du har osparade ändringar, vill du fortsätta?");

			if (answer == JOptionPane.YES_OPTION) {
				System.exit(0);
			} else {
				return;
			}
		} else {
			System.exit(0);
		}
	}

	class WindowExit extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent wev) {
			exit();
		}
	}
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			exit();
		}
	}

	public static void main(String[] args) {
		new MapUI();
		
	}

}
