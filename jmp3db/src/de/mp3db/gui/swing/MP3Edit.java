package de.mp3db.gui.swing;

import info.clearthought.layout.TableLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.mp3db.gui.MP3MultiPanel;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 *
 */
public class MP3Edit extends JTabbedPane implements MP3MultiPanel {
	private static MP3DBListener listener;

	// Komponenten zum editieren der MP3 Tags
	private JLabel id3tagLabel = new JLabel("Edit ID3Tag");
	private JLabel titleLabel = new JLabel("Titel :  ");
	private JTextField titleField = new JTextField(30);
	private JLabel artistLabel = new JLabel("Interpret :  ");
	private JTextField artistField = new JTextField(30);
	private JLabel albumLabel = new JLabel("Album :  ");
	private JTextField albumField = new JTextField(30);
	private JLabel yearLabel = new JLabel("Jahr :  ");
	private JTextField yearField = new JTextField(4);
	private JLabel commentLabel = new JLabel("Kommentar :  ");
	private JTextField commentField = new JTextField(30);
	private JLabel genreLabel = new JLabel("Genre :  ");
	private JComboBox genreCombo = new JComboBox();
	private JLabel trackLabel = new JLabel("Track :  ");
	private JTextField trackField = new JTextField(30);
	private JLabel filenameLabel = new JLabel("Datei :");
	private JTextField filenameField = new JTextField(30);
	private JTextField addArtistField = new JTextField(30);
	private JTextField addAlbumField = new JTextField(30);
	
	private JButton save = new JButton();
	private JButton abort = new JButton();
	private JButton reset = new JButton();
	private JButton up = new JButton();
	private JButton down = new JButton();
	private JButton addArtist = new JButton();	 
	private JButton addAlbum = new JButton();	 

	//Komponenten auf dem Search Panel
	private JLabel searchLabel = new JLabel("Search :");
	private JButton searchButton = new JButton("Search");
	private JTextField searchField = new JTextField(25);
	
	// Panels
	private JPanel mp3Edit = new JPanel();
	private JPanel searchPanel = new JPanel();
	
	// Temporäre Objekte
	private MP3Song tmp;

	public MP3Edit() {
		listener = MP3DBListener.getInstance();
		
		this.initMP3Frame();
		this.initSearchPanel();
		this.addTab("Search", new ImageIcon(getClass().getClassLoader().getResource("icons/Find16.gif")), searchPanel, "Search");
		this.addTab("Edit", new ImageIcon(getClass().getClassLoader().getResource("icons/Edit16.gif")), mp3Edit, "Edit");

		this.setVisible(true);
	}

	/* (Kein Javadoc)
	 * @see de.mp3db.gui.MP3MultiPanel#setActView(int)
	 */
	public void setActView(int view) {
		this.setSelectedIndex(view);
	}

	private void initSearchPanel() {
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
	}

	private void initMP3Frame() {
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(listener);
		genreCombo.setEditable(true);
		filenameField.setEnabled(false);
		save.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Save16.gif")));
		save.setActionCommand("saveSong");
		save.setToolTipText("Save Song");
		save.addActionListener(listener);
		abort.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Exit16.gif")));
		abort.setActionCommand("abortEdit");
		abort.setToolTipText("Abort");
		save.addActionListener(listener);
		reset.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Undo16.gif")));
		reset.setActionCommand("resetEdit");
		reset.setToolTipText("Reset Song");
		reset.addActionListener(listener);
		up.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Up16.gif")));
		up.setActionCommand("PrevSong");
		up.setToolTipText("Prev Song");
		up.addActionListener(listener);
		down.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Down16.gif")));
		down.setActionCommand("NextSong");
		down.setToolTipText("Next Song");
		down.addActionListener(listener);
		addArtist.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Back16.gif")));
		addAlbum.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Back16.gif")));
		
		double sizes[][] = {{80, 50, 30, TableLayout.FILL, 50, 30, 50, 20, 30, 50, 30, 30, TableLayout.FILL }, // Columns
							{20, 20, 20, 20, 20, 20, 20, 4, 20 }}; // Rows
		
		TableLayout layout = new TableLayout(sizes);
		mp3Edit.setLayout(layout);

		mp3Edit.add(id3tagLabel, "0, 0");
		mp3Edit.add(trackLabel, "6, 0");
		mp3Edit.add(trackField, "7, 0");
		mp3Edit.add(titleLabel, "0, 1");
		mp3Edit.add(titleField, "1, 1, 7, 1");
		mp3Edit.add(artistLabel, "0, 2");
		mp3Edit.add(artistField, "1, 2, 7, 2");
		mp3Edit.add(albumLabel, "0, 3");
		mp3Edit.add(albumField, "1, 3, 7, 3");
		mp3Edit.add(yearLabel, "0, 4");
		mp3Edit.add(yearField, "1, 4");
		mp3Edit.add(genreLabel, "4, 4");
		mp3Edit.add(genreCombo, "5, 4, 7, 4");
		mp3Edit.add(commentLabel, "0, 5");
		mp3Edit.add(commentField, "1, 5, 7, 5");
		mp3Edit.add(filenameLabel, "0, 6");
		mp3Edit.add(filenameField, "1, 6, 12, 6");
		mp3Edit.add(new JSeparator(), "0, 7, 12, 7");
		mp3Edit.add(save, "0, 8");
		mp3Edit.add(abort, "1, 8, 2, 8");
		mp3Edit.add(reset, "8, 8, 9, 8");
		mp3Edit.add(down, "10, 8");
		mp3Edit.add(up, "11, 8");
		mp3Edit.add(addArtist, "8, 2");
		mp3Edit.add(addArtistField, "9, 2, 12, 2");
		mp3Edit.add(addAlbum, "8, 3");
		mp3Edit.add(addAlbumField, "9, 3, 12, 3");
	}

	public void clearEditFields() {
		titleField.setText("");
		artistField.setText("");
		albumField.setText("");
		yearField.setText("");
		commentField.setText("");
		genreCombo.removeAllItems();
		trackField.setText("");
		filenameField.setText("");
		addArtistField.setText("");
		addAlbumField.setText("");
		//tmp = null;
	}
	
	public void editSong(MP3Song song) {
		tmp = new MP3Song(song);
		if(song != null) {
			if(song.getTrackNo() > 0) {
				trackField.setText(String.valueOf(song.getTrackNo()));
			}
			titleField.setText(song.getTitle());
			artistField.setText(song.getArtist());
			albumField.setText(song.getAlbum());
			if(song.getYear() > 1) {
				yearField.setText(String.valueOf(song.getYear()));
			}
			genreCombo.addItem(song.getGenre());
			commentField.setText("");
			filenameField.setText(song.getFileName());
		}
	}
	
	public MP3Song getChangedSong() {
		if(tmp != null) {
			MP3Song changedSong = new MP3Song();
			changedSong.setID(tmp.getID());
			changedSong.setTitle(titleField.getText());
			changedSong.setArtist(artistField.getText());
			changedSong.setAlbum(albumField.getText());
			int year;
			try {
				year = Integer.parseInt(yearField.getText());
			}
			catch(Exception ex) {
				year = 0;
			}
			changedSong.setYear(year);
			changedSong.setGenre(genreCombo.getSelectedItem().toString());
			changedSong.setFileName(tmp.getFileName());
			changedSong.setBitrate(tmp.getBitrate());
			changedSong.setFileSize(tmp.getFileSize());
			changedSong.setLength(tmp.getLength());
			changedSong.setLastModified(tmp.getLastModified());
			changedSong.setCodec(tmp.getCodec());
			
			return changedSong;
		}
		else {
			return null;
		}
	}
	
	public void resetEdit() {
		this.clearEditFields();
		this.editSong(tmp);
	}
	
	public String getSearchString() {
		return searchField.getText();
	}
}