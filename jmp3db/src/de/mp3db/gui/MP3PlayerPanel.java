package de.mp3db.gui;

/**
 * @author alex
 *
 */
public interface MP3PlayerPanel {
	public void setText(String message);
	
	public void setBarPos(int pos, int max);
	
	public void clrBar();
}
