// Created on 03.09.2003
package de.mp3db;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import de.mp3db.gui.MP3PlayerPanel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.MP3Song;

/**     Klasse repräsentiert einen MP3/Ogg-Player
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Player.java,v 1.2 2004/10/13 11:31:52 einfachnuralex Exp $
 *  
 *      $Log: MP3Player.java,v $
 *      Revision 1.2  2004/10/13 11:31:52  einfachnuralex
 *      CVS Kommentare eingefügt
 *      Klasse auf BasicPlayer2.3 API von javazoom.net umgestellt
 *
 *
 */
public class MP3Player implements BasicPlayerListener {
	private static MP3Player instance;
	private Playlist list;
	private MP3PlayerPanel panel;
	
	private BasicController player = null;
	private BasicPlayer tmp = null;
	
	public MP3Player() {
		instance = this;
		list = Playlist.getInstance();
		panel = ManagerGUI.getInstance().getPlayer();
		
		tmp = new BasicPlayer();
		tmp.addBasicPlayerListener(this);
		player = (BasicController)tmp;
	}
	
	public static MP3Player getInstance() {
		if(instance == null) {
			instance = new MP3Player();
		}
		return instance;
	}
	
	public void play() {
		if(!list.empty()) {
			try {
				if(tmp.getStatus() == BasicPlayer.STOPPED || tmp.getStatus() == BasicPlayer.UNKNOWN ) {
						player.open(new File(list.getActSong().getFileName()));
						player.play();
					panel.setText(list.getActSong().getTitle());
				}
				else if(tmp.getStatus() == BasicPlayer.PLAYING) {
					player.stop();
					this.play();
				}
				else if(tmp.getStatus() == BasicPlayer.PAUSED) {
					player.resume();
				}
			}
			catch(BasicPlayerException ex) {
				System.out.println(ex);
			}
		}
	}
	
	public void stop() {
		if(tmp.getStatus() == BasicPlayer.PAUSED || tmp.getStatus() == BasicPlayer.PLAYING) {
			try {
				player.stop();
				panel.clrBar();
				//player = new BasicPlayer(this);
			}
			catch(BasicPlayerException ex) {
				System.out.println(ex);
			}
		}
		else {
			panel.clrBar();
		}
	}
	
	public void pause() {
		try {
			if(tmp.getStatus() == BasicPlayer.PLAYING) {
				player.pause();
			}
			else if(tmp.getStatus() == BasicPlayer.PAUSED) {
				player.resume();
			}
		}
		catch(BasicPlayerException ex) {
			System.out.println(ex);
		}
	}
	
	public void next() {
		if(tmp.getStatus() == BasicPlayer.PAUSED || tmp.getStatus() == BasicPlayer.PLAYING) {
			try {
				player.stop();
				list.nextSong();
				panel.clrBar();
				this.play();
			}
			catch(BasicPlayerException ex) {
				System.out.println(ex);
			}
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
		if(tmp.getStatus() == BasicPlayer.PAUSED || tmp.getStatus() == BasicPlayer.PLAYING) {
			try {
			player.stop();
			list.prevSong();
			panel.clrBar();
			this.play();
			}
			catch(BasicPlayerException ex) {
				System.out.println(ex);
			}
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
		if(tmp.getStatus() == BasicPlayer.PLAYING) {
			panel.setBarPos(arg0, arg1);
		}
	}

	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#opened(java.lang.Object, java.util.Map)
	 */
	public void opened(Object arg0, Map arg1) {
		// TODO Auto-generated method stub
		System.out.println("Opened : " + arg0.toString() + " : " + arg1.toString());
		//System.out.println("Duration : " + Math.round(((Integer)arg1.get("duration")).intValue()));
		//panel.setBarPos(0, Math.round(((Integer)arg1.get("duration")).intValue()));
		if(arg1.get("audio.type").toString().equalsIgnoreCase("mp3")) {
			panel.setBarPos(0, ((Integer)arg1.get("mp3.length.bytes")).intValue());
		}
		if(arg1.get("audio.type").toString().equalsIgnoreCase("ogg")) {
			panel.setBarPos(0, ((Integer)arg1.get("ogg.length.bytes")).intValue());
		}

	}
	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#progress(int, long, byte[], java.util.Map)
	 */
	public void progress(int arg0, long arg1, byte[] arg2, Map arg3) {
		// TODO Auto-generated method stub
		//System.out.println("Progress : " + arg0 + " : " + arg1 + " : " + arg2 + " : " + arg3.toString());
		panel.setBarPos(arg0, -1);
	}
	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#setController(javazoom.jlgui.basicplayer.BasicController)
	 */
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see javazoom.jlgui.basicplayer.BasicPlayerListener#stateUpdated(javazoom.jlgui.basicplayer.BasicPlayerEvent)
	 */
	public void stateUpdated(BasicPlayerEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getCode() == BasicPlayerEvent.EOM) {
			if(tmp.getStatus() == BasicPlayer.STOPPED || tmp.getStatus() == BasicPlayer.UNKNOWN) {
				panel.clrBar();
				list.nextSong();
				this.play();
			}
		}
		if(arg0.getCode() == BasicPlayerEvent.STOPPED) {
			panel.setText("Stopped");
		}
		if(arg0.getCode() == BasicPlayerEvent.PAUSED) {
			panel.setText("Paused");
		}
		if(arg0.getCode() == BasicPlayerEvent.RESUMED) {
			panel.setText(list.getActSong().getTitle());
		}
	}
}