// Created on 03.09.2003
package de.mp3db;

import java.io.File;

import javazoom.jlGui.BasicPlayer;
import javazoom.jlGui.BasicPlayerListener;
import de.mp3db.gui.MP3PlayerPanel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 */
public class MP3Player implements BasicPlayerListener {
	private static MP3Player instance;
	private Playlist list;
	private MP3PlayerPanel panel;
	
	private BasicPlayer player = null;
	
	public MP3Player() {
		instance = this;
		list = Playlist.getInstance();
		panel = ManagerGUI.getInstance().getPlayer();
		
		player = new BasicPlayer(this);
	}
	
	public static MP3Player getInstance() {
		if(instance == null) {
			instance = new MP3Player();
		}
		return instance;
	}
	
	public void play() {
		if(!list.empty()) {
			if(player.getStatus() == BasicPlayer.STOPPED || player.getStatus() == BasicPlayer.READY ) {
				try {
					player.setDataSource(new File(list.getActSong().getFileName()));
					player.startPlayback();
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
				panel.setText(list.getActSong().getTitle());
			}
			else if(player.getStatus() == BasicPlayer.PLAYING) {
				player.stopPlayback();
				this.play();
			}
			else if(player.getStatus() == BasicPlayer.PAUSED) {
				player.resumePlayback();
			}
		}
	}
	
	public void stop() {
		if(player.getStatus() == BasicPlayer.PAUSED || player.getStatus() == BasicPlayer.PLAYING) {
			player.stopPlayback();
			panel.clrBar();
		}
		else {
			panel.clrBar();
		}
	}
	
	public void pause() {
		if(player.getStatus() == BasicPlayer.PLAYING) {
			player.pausePlayback();
		}
		else if(player.getStatus() == BasicPlayer.PAUSED) {
			player.resumePlayback();
		}
	}
	
	public void next() {
		if(player.getStatus() == BasicPlayer.PAUSED || player.getStatus() == BasicPlayer.PLAYING) {
			player.stopPlayback();
			list.nextSong();
			panel.clrBar();
			this.play();
		}
		else {
			list.nextSong();
			panel.clrBar();
			this.play();
		}
	}
	
	public void selSong() {
		list.selectSong();
		this.play();
	}
	
	public void prev() {
		if(player.getStatus() == BasicPlayer.PAUSED || player.getStatus() == BasicPlayer.PLAYING) {
			player.stopPlayback();
			list.prevSong();
			panel.clrBar();
			this.play();
		}
		else {
			list.prevSong();
			panel.clrBar();
			this.play();
		}
	}
	
	public void addPlaylist(MP3Song song) {
		list.add(song);
	}
	
	public void clearPlaylist() {
		list.clear();
	}
	
	/* (Kein Javadoc)
	 */
	public void updateCursor(int arg0, int arg1) {
		if(player.getStatus() == BasicPlayer.PLAYING) {
			panel.setBarPos(arg0, arg1);
		}
	}

	/* (Kein Javadoc)
	 */
	public void updateMediaData(byte[] arg0) {
	}

	/* (Kein Javadoc)
	 */
	public void updateMediaState(String arg0) {
		if(arg0.equals("EOM")) {
			if(player.getStatus() == BasicPlayer.STOPPED || player.getStatus() == BasicPlayer.READY) {
				panel.clrBar();
				list.nextSong();
				this.play();
			}
		}
	}
}