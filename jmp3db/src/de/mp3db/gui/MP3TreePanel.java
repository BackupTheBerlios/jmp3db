package de.mp3db.gui;

import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Genre;
import de.mp3db.util.MP3Year;

/**
 * @author alex
 *
 */
public interface MP3TreePanel {

	public void clear();
	
	public void addToArtist(MP3Artist newNode);
	
	public void addToAlbum(MP3Album newNode);
	
	public void addToGenre(MP3Genre newNode);
	
	public void addToYear(MP3Year newNode);
	
}
