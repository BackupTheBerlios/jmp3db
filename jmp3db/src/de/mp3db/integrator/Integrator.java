package de.mp3db.integrator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * @author alex
 *
 */
class Integrator extends JFrame implements ActionListener{

	// Lade Configurationsdatei
	private File dir = new File("D:\\Music");

	// Variablen
	private String actPath;
	private MP3Song[] songs;
	
	// Komponenten
	private JComboBox abcBox = new JComboBox();
	private JPanel mainPanel = new JPanel();
	private DefaultListModel listModel = new DefaultListModel();
	private JList list = new JList(listModel);
	private DefaultListModel eastListModel = new DefaultListModel();
	private JList eastList = new JList(eastListModel);

	// Komponenten Textfelder für FileInfo	
	private JTextField artist1 = new JTextField(30);
	private JTextField artist2 = new JTextField(30);
	private JTextField artist3 = new JTextField(30);
	private JTextField album1 = new JTextField(30);
	private JTextField album2 = new JTextField(30);
	private JTextField album3 = new JTextField(30);
	private JTextField title1 = new JTextField(30);	
	private JTextField title2 = new JTextField(30);	
	private JTextField track1 = new JTextField(2);
	private JTextField track2 = new JTextField(2);		
	private JTextField year1 = new JTextField(4);		
	private JTextField newFileName = new JTextField(30);		

	// Konstante ABC
	private static final String[] ABC = 
		{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	// Konstruktor
	public Integrator() {
		super();
		this.setTitle("MP3 Integrator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);		
		initTopPanel();
		initWestPanel();
		initEastPanel();
		initCenterPanel();
		this.setContentPane(mainPanel);
	}

	private void initWestPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listPane = new JScrollPane(list);

		JButton button = new JButton();
		button = new JButton("Edit");
		button.setActionCommand("Edit");
		button.addActionListener(this);
		
		panel.add(button, BorderLayout.SOUTH);		
		panel.add(listPane, BorderLayout.CENTER);
		
		mainPanel.add(panel, BorderLayout.WEST);
	}

	private void initTopPanel() {
		JPanel topPanel = new JPanel();
		JButton button;

		
		for(int g=0; g<ABC.length; g++)
			abcBox.addItem(ABC[g]);
		abcBox.setEditable(false);
		topPanel.add(abcBox);
		
		button = new JButton("Select");
		button.setActionCommand("Select");
		button.addActionListener(this);
		topPanel.add(button);		
		mainPanel.add(topPanel, BorderLayout.NORTH);		
	}

	private void initEastPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		eastList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listPane = new JScrollPane(eastList);

		JButton button = new JButton();
		button = new JButton("Edit");
		button.setActionCommand("Edit2");
		button.addActionListener(this);
		
		panel.add(button, BorderLayout.SOUTH);		
		panel.add(listPane, BorderLayout.CENTER);
	
		mainPanel.add(panel, BorderLayout.EAST);					
	}

	private void initCenterPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(12, 2));
		JLabel label;
		
		label = new JLabel("Artist ID3Tag");
		centerPanel.add(label);
		centerPanel.add(artist1);
		label = new JLabel("Artist Dir");
		centerPanel.add(label);
		centerPanel.add(artist2);
		label = new JLabel("Artist File");
		centerPanel.add(label);
		centerPanel.add(artist3);		
		label = new JLabel("Album ID3Tag");
		centerPanel.add(label);
		centerPanel.add(album1);		
		label = new JLabel("Album Dir");
		centerPanel.add(label);
		centerPanel.add(album2);		
		label = new JLabel("Album File");
		centerPanel.add(label);
		centerPanel.add(album3);		
		label = new JLabel("Title ID3Tag");
		centerPanel.add(label);
		centerPanel.add(title1);
		label = new JLabel("Title File");
		centerPanel.add(label);
		centerPanel.add(title2);
		label = new JLabel("Track ID3Tag");
		centerPanel.add(label);
		centerPanel.add(track1);
		label = new JLabel("Track File");
		centerPanel.add(label);
		centerPanel.add(track2);
		label = new JLabel("Year ID3Tag");
		centerPanel.add(label);
		centerPanel.add(year1);
		label = new JLabel("New FileName");
		centerPanel.add(label);
		centerPanel.add(newFileName);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	}

	private void select() {
		int selLetter = abcBox.getSelectedIndex();
		actPath = dir.toString() + "/-" + ABC[selLetter] + "-";
		File newPath = new File(actPath);
		clearList();
		listDir(newPath);
	}

	private void listDir(File newPath) {
		if(newPath.isDirectory()) {
			File[] dir = newPath.listFiles();
			Arrays.sort(dir);
			
			for(int i = 0; i<dir.length; i++) {
				if(dir[i].isDirectory()) {
					listModel.addElement(dir[i].toString());
				}
			}
		}
	}

	private void edit() {
		int sel = list.getSelectedIndex();
		String value = (String)listModel.getElementAt(sel);
		scanner(new File(value));
	}

	private void getFileInfo() {
		int sel = eastList.getSelectedIndex();
		
		artist1.setText(songs[sel].getArtist());
		artist2.setText(songs[sel].getArtistDir());		
		artist3.setText(songs[sel].getArtistFile());
		
		album1.setText(songs[sel].getAlbum());
		album2.setText(songs[sel].getAlbumDir());
		album3.setText(songs[sel].getAlbumFile());

		title1.setText(songs[sel].getTitle());
		title2.setText(songs[sel].getTitleFile());
		
		track1.setText(String.valueOf(songs[sel].getTrackNo()));
		track2.setText(songs[sel].getTrackFile());
		
		year1.setText(songs[sel].getYear());
		
	}

	private void scanner(File rootDir) {
		Scanner scan = new Scanner(rootDir);
		songs = scan.getResult();
		
		clearEastList();
		for(int i = 0; i<songs.length; i++) {
			eastListModel.addElement(songs[i].toString());
		}
	}

	public void clearList() {
		listModel.clear();	
	}

	public void clearEastList() {
		eastListModel.clear();	
	}

	public void actionPerformed(ActionEvent ev) {
		JButton button = (JButton) ev.getSource();
		String command = button.getActionCommand();
		if (command.equals("Select"))
			select();
		else if(command.equals("Edit"))
			edit();
		else if(command.equals("Edit2"))
			getFileInfo();
	}

	public static void main(String[] args) {
		Integrator frame = new Integrator();
		frame.setSize(800, 400);
		frame.setLocation(100, 100);
		frame.setVisible(true);
	}
}
