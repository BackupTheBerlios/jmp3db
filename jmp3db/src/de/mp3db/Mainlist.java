package de.mp3db;

import java.util.Vector;

import de.mp3db.gui.MP3Panel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 *
 */
public class Mainlist {
	private static Mainlist instance;

	private MP3Panel panel;
	private Vector songlist;

	public Mainlist() {
		instance = this;
		panel = ManagerGUI.getInstance().getMainlist();
		songlist = new Vector();
	}

	public static Mainlist getInstance() {
		if(instance == null) {
			instance = new Mainlist();
		}
		return instance;
	}
	
	public void add(Vector newSongs) {
		this.clear();
		songlist = newSongs;
		
		if(!newSongs.isEmpty())
			for(int i=0; i<newSongs.size();i++)
				panel.add((MP3Song)newSongs.get(i));
	}
	
	public MP3Song getSelection() {
		if(panel.getSelection() != -1)
			return (MP3Song)songlist.get(panel.getSelection());
		else
			return null;
	}

	public void clear() {
		panel.clear();
		this.songlist.clear();
	}
	
	public void setSelection(int row) {
		panel.setSelection(row);
	}
}