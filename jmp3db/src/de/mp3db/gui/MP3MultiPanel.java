package de.mp3db.gui;

import de.mp3db.util.MP3Song;

/**
 * @author apredesc
 *
 * Folgendes auswählen, um die Schablone für den erstellten Typenkommentar zu ändern:
 * Fenster&gt;Benutzervorgaben&gt;Java&gt;Codegenerierung&gt;Code und Kommentare
 */
public interface MP3MultiPanel {
	
	public void setActView(int view);
	
	public void editSong(MP3Song song);
	
	public void clearEditFields();
	
	public void resetEdit();
	
	public MP3Song getChangedSong();
	
	public String getSearchString();
	
}
