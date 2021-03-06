package de.mp3db;

import java.util.Vector;

import de.mp3db.gui.MP3MultiPanel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.FuzzySearch;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 *
 */
public class MultiPanel {
	private static MultiPanel instance;
	private MP3MultiPanel panel;
	
	private boolean isEdit = false;
	
	public MultiPanel() {
		instance = this;
		panel = ManagerGUI.getInstance().getMultiPanel();
		
	}
	
	public static MultiPanel getInstance() {
		if(instance == null) {
			instance = new MultiPanel();
		}
		return instance;
	}
	
	public void edit(MP3Song song) {
		if(isEdit) {
			panel.clearEditFields();
		}
		panel.editSong(song);
		panel.setActView(1);
		isEdit = true;
	}
	
	public void resetEdit() {
		panel.resetEdit();
	}
	
	public MP3Song changedSong() {
		return panel.getChangedSong();
	}
	
	public void search() {
		Vector searchResult = new Vector();
		Vector tmp = DBTree.getInstance().getActSongs();
		for(int i = 0; i < tmp.size(); i++) {
			FuzzySearch obj = new FuzzySearch(((MP3Song)tmp.get(i)).toString(), panel.getSearchString(), 12);
			double result = obj.getResult();
			if(result > 0) {
				searchResult.add((MP3Song)tmp.get(i));
			}
		}
		Mainlist.getInstance().add(searchResult);
	}
}