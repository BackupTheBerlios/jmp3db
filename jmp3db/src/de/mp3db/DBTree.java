package de.mp3db;

import java.io.File;
import java.util.Vector;

import de.mp3db.db.DBHandler;
import de.mp3db.db.MP3Scanner;
import de.mp3db.db.SQLHandler;
import de.mp3db.db.ScanEvent;
import de.mp3db.db.ScanListener;
import de.mp3db.gui.MP3TreePanel;
import de.mp3db.gui.ManagerGUI;
import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Genre;
import de.mp3db.util.MP3Song;
import de.mp3db.util.MP3Year;
import de.mp3db.util.Tag;

/**
 * @author alex
 *
 */
public class DBTree {
	private static DBTree instance;

	private MP3TreePanel panel;
	private DBHandler dbHandler;
	private ScanListener scan;

	public DBTree() {
		instance = this;
		panel = ManagerGUI.getInstance().getTree();
	}
	
	public void addScanListener(ScanListener listener) {
		this.scan = listener; 
	}
	
	public static DBTree getInstance() {
		if(instance == null) {
			instance = new DBTree();
		}
		return instance;
	}
	
	private void loadDB() throws Exception {
		dbHandler = new SQLHandler();
	}
	
	public void connect() {
		if(dbHandler == null) {
			try {
				this.loadDB();
			}
			catch(Exception e) { 
				System.out.println("Loading SQLHandler : " + e.toString());
			}
			this.loadArtists();
			this.loadAlbums();
			this.loadYears();
			this.loadGenres();
		}
	}
	
	private void clearTree() {
		panel.clear();
	}
	
	public void reloadDB() {
		this.clearTree();
		
		this.loadArtists();
		this.loadAlbums();
		this.loadYears();
		this.loadGenres();
	}	

	private void loadArtists() {
		Vector artists = dbHandler.getAllArtists();
		
		if(artists != null) {
			for(int i=0; i<artists.size();i++) {
				panel.addToArtist((MP3Artist)artists.get(i));
			}
		}
	}
	
	private void loadAlbums() {
		Vector albums = dbHandler.getAllAlbums();
		
		if(albums != null) {
			for(int i=0; i<albums.size();i++) {
				panel.addToAlbum((MP3Album)albums.get(i));
			}
		}
	}
	
	private void loadYears() {
		Vector years = dbHandler.getAllYears();
		
		if(years != null) {
			for(int i=0; i<years.size();i++) {
				panel.addToYear((MP3Year)years.get(i));
			}
		}
	}

	private void loadGenres() {
		Vector genres = dbHandler.getAllGenres();
		
		if(genres != null) {
			for(int i=0; i<genres.size();i++) {
				panel.addToGenre((MP3Genre)genres.get(i));
			}
		}
	}
	
	public Vector getActSongs() {
		return dbHandler.getAllSongs();
	}
	
	public MP3Song getSong(int id) {
		return dbHandler.getSong(id);
	}
	
	public Vector getSongsByArtist(int id) {
		return dbHandler.getSongsByArtist(id);
	}

	public Vector getSongsByAlbum(int id) {
		return dbHandler.getSongsByAlbum(id);	
	}
	
	public Vector getSongsByYear(int id) {
		return dbHandler.getSongsByYear(id);	
	}
	
	public Vector getSongsByGenre(int id) {
		return dbHandler.getSongsByGenre(id);
	}
	
	public void scan(File root) {
		Vector ext = new Vector();
		ext.add(new String(".mp3"));
		ext.add(new String(".ogg"));
		Scanner sn = new Scanner(dbHandler, scan, root, ext);
		sn.start();
	}
	
	public void scan(File root, Vector ext) {
		Scanner sn = new Scanner(dbHandler, scan, root, ext);
		sn.start();
	}
	
	public void cleanDB() {
		if(dbHandler.clearDB()) {
			System.out.println("Error while cleaning DB. Do not start Scanner");
		}
		clearTree();
	}

	public void save(MP3Song changedSong) {
		if(changedSong != null) {
			Tag file = new Tag(changedSong);
			if(file.writeTag()) {
				dbHandler.changeSong(changedSong);
			}	
		}
	}
	
}

class Scanner extends Thread {
	
	private DBHandler dbHandler;
	private ScanListener scan;
	private File root;
	private Vector ext;
	
	public Scanner(DBHandler db, ScanListener scan, File root, Vector ext) {
		this.dbHandler = db;
		this.scan = scan;
		this.root = root;
		this.ext = ext;
	}
	
	public void run() {
		MP3Scanner scanner = new MP3Scanner(this.root, ext);

		Vector files = scanner.getFilenames();

		System.out.println("Scanned "+files.size()+" files");

		long start = System.currentTimeMillis();

		if(files != null){
			for(int i=0; i<files.size(); i++) {
				String fileName = (String)files.get(i);
				if(fileName != null) {
					dbHandler.addSong(fileName);
					scan.updatePos(new ScanEvent(fileName, i, files.size()-1));
				}
			}
		}
		System.out.println("Finished adding in "+((System.currentTimeMillis()-start)/60)+" s");
		
	}
}