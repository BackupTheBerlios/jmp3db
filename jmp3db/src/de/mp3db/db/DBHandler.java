package de.mp3db.db;

import java.util.Vector;

import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Song;

/**
 * @author alex
 *
 */
public interface DBHandler {

	public MP3Song getSong(int id);
	
	public MP3Artist getArtist(int id);
	
	public MP3Album getAlbum(int id);
	
	public Vector getSongsByArtist(int artistId);
	
	public Vector getSongsByAlbum(int albumId);
	
	public Vector getSongsByYear(int yearId);
	
	public Vector getSongsByGenre(int genreId);
	
	public Vector getAllSongs();
	
	public Vector getAllArtists();
	
	public Vector getAllAlbums();
	
	public Vector getAllGenres();
	
	public Vector getAllYears();
	
	public void changeSong(MP3Song changedSong);
	
	public void addSong(String fileName);

    public boolean clearDB();	
	
}