package de.mp3db.gui;

import de.mp3db.util.MP3Song;

/**
 * @author alex
 *
 */
public interface MP3Panel {

	public void add(MP3Song newSong);
	
	public void remove(int pos);

	public void clear();
	
	public int getSelection();
	
	public void setSelection(int row);
	
}
