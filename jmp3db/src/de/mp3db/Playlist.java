package de.mp3db;

import java.util.Vector;

import de.mp3db.gui.MP3Panel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.MP3Song;

/** Klasse repräsentiert eine Playlist
 * @author alex
 */

public class Playlist {
	private static Playlist instance;

	private MP3Panel panel;
	private Vector songlist;
	
	private int actSong = 0;
	
	public Playlist() {
		instance = this;
		panel = ManagerGUI.getInstance().getPlaylist();
		songlist = new Vector();
	}

	public static Playlist getInstance() {
		if(instance == null) {
			instance = new Playlist();
		}
		return instance;
	}
	
	public void add(MP3Song newSong) {
		songlist.add(newSong);
		panel.add(newSong);
	}

	public boolean empty() {
		return songlist.isEmpty();
	}

	public MP3Song getActSong() {
		if(songlist.isEmpty()) {
			return null;
		}
		else {
			return (MP3Song)songlist.get(actSong);
		}
	}
	
	public void nextSong() {
		if((actSong+1) >= songlist.size()) {
			actSong = 0;
		}
		else {
			actSong++;
		}
	}
	
	public void selectSong() {
		if(panel.getSelection() != -1) {
			actSong = panel.getSelection(); 
		}
	}

	public void prevSong() {
		if(actSong == 0) {
			actSong = songlist.size()-1;
		}
		else {
			actSong--;
		}
	}
	
	public void clear() {
		panel.clear();
		this.songlist.clear();
		actSong = 0;
	}
	
	public void removeSelection() {
		if(panel.getSelection() != -1) {
			int actSelection = panel.getSelection();
			songlist.removeElementAt(actSelection);
			if(actSong >= actSelection) {
				actSong--;
			}
			panel.remove(actSelection);
		}
	}
}