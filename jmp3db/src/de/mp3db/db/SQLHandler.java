package de.mp3db.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import de.mp3db.config.ConfigProperty;
import de.mp3db.util.MP3Album;
import de.mp3db.util.MP3Artist;
import de.mp3db.util.MP3Genre;
import de.mp3db.util.MP3Song;
import de.mp3db.util.MP3Year;
import de.mp3db.util.Tag;

/**     Klasse repr�sentiert das SQL Datenbank Interface
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: SQLHandler.java,v 1.3 2004/08/24 12:45:33 einfachnuralex Exp $
 *  
 *      $Log: SQLHandler.java,v $
 *      Revision 1.3  2004/08/24 12:45:33  einfachnuralex
 *      CVS Kommentare eingef�gt
 *      addSong(MP3Song)
 *      SQL Querys �berarbeitet
 *
 *
 */
public class SQLHandler implements DBHandler {

	private static PreparedStatement	getSongsStatement;
	private static PreparedStatement	getAlbumsStatement;
	private static PreparedStatement	getArtistsStatement;
	private static PreparedStatement	getFilesByHashStatement;
	private static PreparedStatement	addSongStatement;
	private static PreparedStatement getArtistByNameStatement;
	private static PreparedStatement addArtistStatement;
	private static PreparedStatement addAlbumStatement;
	private static PreparedStatement getYearByNumberStatement;
	private static PreparedStatement addYearStatement;
	private static PreparedStatement getAlbumByNameStatement;
	private static PreparedStatement getMaxSongIdStatement;
	private static PreparedStatement getMaxArtistIdStatement;
	private static PreparedStatement	getMaxAlbumIdStatement;
	private static PreparedStatement	getMaxYearIdStatement;
	private static PreparedStatement	getMaxGenreIdStatement;	
	private static PreparedStatement	deleteSongsStatement;
	private static PreparedStatement	deleteArtistsStatement;
	private static PreparedStatement	deleteAlbumsStatement;
	private static PreparedStatement	deleteYearsStatement;
	private static PreparedStatement	deleteGenresStatement;	
	private static PreparedStatement getSongsByArtistStatement;
	private static PreparedStatement getSongsByAlbumStatement;
	private static PreparedStatement getSongsByYearStatement;
	private static PreparedStatement getSongsByGenreStatement;		
	private static PreparedStatement getSongByIDStatement;
	private static PreparedStatement getYearsStatement;
	private static PreparedStatement getGenresStatement;
	private static PreparedStatement getGenreByNameStatement;
	private static PreparedStatement addGenreStatement;	
	
	private Connection dbConnection;

	public SQLHandler() {
		System.out.println("Connecting to Database");
		try {

        	ConfigProperty cp = new ConfigProperty(System.getProperty("user.home") + "/.jmp3db/mp3db.conf");
        	String url = cp.getDBUrl("db");
        	String driver = cp.getDBDriver("db");
        	String user = cp.getDBUser("db");
        	String pw = cp.getDBPassword("db");
        	
            Class.forName(driver);
            this.dbConnection = DriverManager.getConnection(url,user,pw);
            DriverManager.setLogWriter(new java.io.PrintWriter(new java.io.FileWriter(System.getProperty("user.home")+"/.jmp3db/sql.log")));
			this.prepareStatements();
        }
        catch(Exception ex) {
            System.out.println("DB Connection Error : " + ex);
        }
	}

	public MP3Song getSong(int id) {
		return null;
	}

	public MP3Artist getArtist(int id) {
		return null;
	}

	public MP3Album getAlbum(int id) {
		return null;
	}

	public Vector getSongsByArtist(int artistId) {
		Vector songs = new Vector();
		try {
			getSongsByArtistStatement.setInt(1, artistId);
			ResultSet result = getSongsByArtistStatement.executeQuery();
			while(result.next()) {
				MP3Song song = new MP3Song();
				song.setID(result.getInt(1));
				song.setTitle(result.getString(2));
				song.setArtist(result.getString(3));
				song.setAlbum(result.getString(4));
				song.setYear(result.getInt(5));
				song.setGenre(result.getString(6));
				song.setTrackNo(result.getInt(7));
				song.setBitrate(result.getInt(8));
				song.setLength(result.getInt(9));
				song.setFileSize(result.getLong(10));
				song.setFileName(result.getString(11));
				song.setLastModified(result.getLong(12));
				
				songs.add(song);
			}
			result.close();
		}
		catch(SQLException ex) { 
			System.out.println("SQLError (getSongsByArtist) : " + ex); 
			return null;
		}
		return songs;
	}
	
	public Vector getSongsByAlbum(int albumId) {
		Vector songs = new Vector();
		try {
			getSongsByAlbumStatement.setInt(1, albumId);
			ResultSet result = getSongsByAlbumStatement.executeQuery();
			while(result.next()) {
				MP3Song song = new MP3Song();
				song.setID(result.getInt(1));
				song.setTitle(result.getString(2));
				song.setArtist(result.getString(3));
				song.setAlbum(result.getString(4));
				song.setYear(result.getInt(5));
				song.setGenre(result.getString(6));
				song.setTrackNo(result.getInt(7));
				song.setBitrate(result.getInt(8));
				song.setLength(result.getInt(9));
				song.setFileSize(result.getLong(10));
				song.setFileName(result.getString(11));
				song.setLastModified(result.getLong(12));
				
				songs.add(song);
			}
			result.close();
		}
		catch(SQLException ex) { 
			System.out.println("SQLError (getSongsByAlbum) : " + ex); 
			return null;
		}
		return songs;
	}

	public Vector getSongsByGenre(int genreId) {
		Vector songs = new Vector();
		try {
			getSongsByGenreStatement.setInt(1, genreId);
			ResultSet result = getSongsByGenreStatement.executeQuery();
			
			while(result.next()) {
				MP3Song song = new MP3Song();
				song.setID(result.getInt(1));
				song.setTitle(result.getString(2));
				song.setArtist(result.getString(3));
				song.setAlbum(result.getString(4));
				song.setYear(result.getInt(5));
				song.setGenre(result.getString(6));
				song.setTrackNo(result.getInt(7));
				song.setBitrate(result.getInt(8));
				song.setLength(result.getInt(9));
				song.setFileSize(result.getLong(10));
				song.setFileName(result.getString(11));
				song.setLastModified(result.getLong(12));
				
				songs.add(song);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError :" + ex);
			return null;
		}
		return songs;
	}

	public Vector getSongsByYear(int yearId) {
		Vector songs = new Vector();
		try {
			getSongsByYearStatement.setInt(1, yearId);
			ResultSet result = getSongsByYearStatement.executeQuery();
			
			while(result.next()) {
				MP3Song song = new MP3Song();
				song.setID(result.getInt(1));
				song.setTitle(result.getString(2));
				song.setArtist(result.getString(3));
				song.setAlbum(result.getString(4));
				song.setYear(result.getInt(5));
				song.setGenre(result.getString(6));
				song.setTrackNo(result.getInt(7));
				song.setBitrate(result.getInt(8));
				song.setLength(result.getInt(9));
				song.setFileSize(result.getLong(10));
				song.setFileName(result.getString(11));
				song.setLastModified(result.getLong(12));
				
				songs.add(song);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError :" + ex);
			return null;
		}
		return songs;	
	}
	
	public Vector getAllSongs() {
		Vector songs = new Vector();
		try {
			ResultSet result = getSongsStatement.executeQuery();
			
			while(result.next()) {
				MP3Song song = new MP3Song();
				song.setID(result.getInt(1));
				song.setTitle(result.getString(2));
				song.setArtist(result.getString(3));
				song.setAlbum(result.getString(4));
				song.setYear(result.getInt(5));
				song.setGenre(result.getString(6));
				song.setTrackNo(result.getInt(7));
				song.setBitrate(result.getInt(8));
				song.setLength(result.getInt(9));
				song.setFileSize(result.getLong(10));
				song.setFileName(result.getString(11));
				song.setLastModified(result.getLong(12));
				
				songs.add(song);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError :" + ex);
			return null;
		}
		songs.trimToSize();
		return songs;
	}

	public Vector getAllArtists() {
		Vector artists = new Vector();
		try {
			ResultSet result = getArtistsStatement.executeQuery();
			while(result.next()) {
				MP3Artist artist = new MP3Artist(result.getString(2));
				artist.setID(result.getInt(1));
				artists.add(artist);
			}
			result.close();
		}
		catch(SQLException ex) { 
			System.out.println("SQLError : " + ex); 
			return null;
		}
		return artists;
	}

	public Vector getAllAlbums() {
		Vector albums = new Vector();
		try {
			ResultSet result = getAlbumsStatement.executeQuery();
			
			while(result.next()) {
				MP3Album a = new MP3Album();
				a.setID(result.getInt(1));
				a.setTitle(result.getString(2));
				a.setArtist(result.getString(3));
				a.setYear(result.getInt(4));
				a.setCover("");
				
				albums.add(a);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError : " + ex);
			return null;
		}
		return albums;		
	}

	public Vector getAllGenres() {
		Vector genres= new Vector();
		try {
			ResultSet result = getGenresStatement.executeQuery();
			
			while(result.next()) {
				MP3Genre y = new MP3Genre();
				y.setID(result.getInt(1));
				y.setGenre(result.getString(3));
				
				genres.add(y);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError : " + ex);
			return null;
		}
		return genres;
	}

	public Vector getAllYears() {
		Vector years = new Vector();
		try {
			ResultSet result = getYearsStatement.executeQuery();
			
			while(result.next()) {
				MP3Year y = new MP3Year();
				y.setID(result.getInt(1));
				y.setYear(result.getInt(2));
				
				years.add(y);
			}
			result.close();
		}
		catch(SQLException ex) {
			System.out.println("SQLError : " + ex);
			return null;
		}
		return years;
	}

	private void prepareStatements() {
		try {
			// SONG Statements
			getSongsStatement = 	dbConnection.prepareStatement(
				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id)");
			addSongStatement = dbConnection.prepareStatement(
				"INSERT INTO songs (id, title, artist, album, year, genre, trackno, bitrate, length, filesize, filename, lastmodified, hashcode) "+
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			getSongByIDStatement = dbConnection.prepareStatement(
				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id) " +
				"WHERE songs.id = ? " +				"ORDER BY songs.title");
			getSongsByArtistStatement = dbConnection.prepareStatement("" +				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id) " +
				"WHERE songs.artist = ? " +				"ORDER BY songs.title ASC"); 
			getSongsByAlbumStatement = dbConnection.prepareStatement("" +				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id) " +
				"WHERE songs.album = ? " +				"ORDER BY albums.name");
			getSongsByYearStatement = dbConnection.prepareStatement(
				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id) " +
				"WHERE songs.year = ? " +				"ORDER BY years.number");
			getSongsByGenreStatement = dbConnection.prepareStatement(
				"SELECT songs.id, songs.title, artists.name, albums.name, years.number, genres.genretext, songs.trackno, songs.bitrate, songs.length, songs.filesize, songs.filename, songs.lastmodified "+ 
				"FROM songs "+
				"LEFT JOIN albums ON (songs.album = albums.id) "+
				"LEFT JOIN artists ON (songs.artist = artists.id) " +
				"LEFT JOIN years ON (songs.year = years.id) " +
				"LEFT JOIN genres ON (songs.genre = genres.id) " +
				"WHERE songs.genre = ? " +				"ORDER BY genres.genretext");
			
			// ARTIST Statements
			getArtistsStatement = dbConnection.prepareStatement(
				"SELECT id, name " +				"FROM artists " +				"ORDER BY name");
			getArtistByNameStatement = dbConnection.prepareStatement(
				"SELECT id, name " +				"FROM artists " +				"WHERE name = ?");
			addArtistStatement = dbConnection.prepareStatement(
				"INSERT INTO artists (id, name, genre) " +				"VALUES (?, ?, ?)");	

			// ALBUM Statements
			getAlbumsStatement = 	dbConnection.prepareStatement(
				"SELECT albums.id, albums.name, artists.name, years.number, albums.cover "+
				"FROM albums "+
				"LEFT JOIN artists ON (artists.id = albums.artist) "+
				"LEFT JOIN years ON (years.id = albums.year) " +				"ORDER BY albums.name");
			addAlbumStatement = dbConnection.prepareStatement(
				"INSERT INTO albums (id, name, artist, year, genre, complete, cover) " +				"VALUES (?, ?, ?, ?, ?, ?, ?)");
			getAlbumByNameStatement = dbConnection.prepareStatement(
				"SELECT id, name, artist " +				"FROM albums " +				"WHERE name = ?");

			// YEAR Statements
			getYearsStatement = dbConnection.prepareStatement(
				"SELECT id, number " +				"FROM years " +				"ORDER BY number");
			getYearByNumberStatement = dbConnection.prepareStatement(
				"SELECT id, number " +				"FROM years " +				"WHERE number = ?");
			addYearStatement = dbConnection.prepareStatement(
				"INSERT INTO years (id, number) " +				"VALUES (?, ?)");
				
			// GENRE Statements
			getGenresStatement = dbConnection.prepareStatement(
				"SELECT id, number, genretext " +				"FROM genres " +				"ORDER BY genretext");
			getGenreByNameStatement = dbConnection.prepareStatement(
				"SELECT id, genretext " +				"FROM genres " +				"WHERE genretext = ?");
			addGenreStatement = dbConnection.prepareStatement(
				"INSERT INTO genres (id, number, genretext) " +				"VALUES (?, ?, ?)");

			// SONSTIGE Statements
			getFilesByHashStatement =dbConnection.prepareStatement(
				"SELECT id,filename,lastmodified " +				"FROM songs " +				"WHERE hashcode = ?");

			// GETMAX Statements
			getMaxSongIdStatement = dbConnection.prepareStatement(
				"SELECT max(id) " +				"FROM songs");
			getMaxArtistIdStatement = dbConnection.prepareStatement(
				"SELECT max(id) " +				"FROM artists");
			getMaxAlbumIdStatement = dbConnection.prepareStatement(
				"SELECT max(id) " +				"FROM albums");
			getMaxYearIdStatement = dbConnection.prepareStatement(
				"Select max(id) " +				"FROM years");
			getMaxGenreIdStatement = dbConnection.prepareStatement(
				"Select max(id) " +				"FROM genres");

			// DELETE Statements
			deleteSongsStatement = dbConnection.prepareStatement(
				"DELETE FROM songs");
			deleteArtistsStatement = dbConnection.prepareStatement(
				"DELETE FROM artists");
			deleteAlbumsStatement = dbConnection.prepareStatement(
				"DELETE FROM albums");
			deleteYearsStatement = dbConnection.prepareStatement(
				"DELETE FROM years");
			deleteGenresStatement = dbConnection.prepareStatement(
				"DELETE FROM genres");
		}
		catch(SQLException ex) {
			System.out.println("Error preparing Statements : " + ex);
		}		
	}
	
	public void addSong(String fileName) {
		
		int filehash = new File(fileName).hashCode();
		long filemod = new File(fileName).lastModified();
		int count = 0;
		boolean exist = false;
		
		// Hashcode in der Datenbank suchen.
		try {
			getFilesByHashStatement.setInt(1, filehash);
			ResultSet files = getFilesByHashStatement.executeQuery();

			while(files.next()) {
				if(count > 1) {
					System.out.println("2 or more Files with the same Hashcode : " + files.getString(2) );
				}
				count++;
				
				if(new File(fileName).getName() == files.getString(2)) {
					exist = true;
				}
			}
			files.close();
		}
		catch(SQLException ex) {
			System.out.println(ex);
		}

		if(exist) {
			// Change Song
		}
		else {
			// Neuen MP3Song anlegen
			Tag newFile = new Tag(fileName);
			
			MP3Song newSong = new MP3Song();
			newSong.setID(this.getMaxSongId()+1);
			newSong.setTitle(newFile.getTitle());
			newSong.setArtist(newFile.getArtist());
			newSong.setAlbum(newFile.getAlbum());
			newSong.setYear(newFile.getYear());
			newSong.setGenre(newFile.getGenre());
			newSong.setTrackNo(newFile.getTrackNo());
			newSong.setBitrate(newFile.getBitrate());
			newSong.setLength(newFile.getLength());
			newSong.setFileSize(newFile.getFileSize());
			newSong.setFileName(newFile.getFileName());
			newSong.setLastModified(newFile.getLastModified());
			newSong.setHashCode(filehash);
			newSong.setCodec(newFile.getCodec());
						
			addSong(newSong);
		}
	}
	
	private void addSong(MP3Song song) {
		int artistId = 0; 
		int albumId = 0; 
		int yearId = 0;
		int genreId = 0;
		
		try {
			addSongStatement.setInt(1, song.getID());
			
			if(song.getTitle().length() < 1) {
				String tmp = (((new File(song.getFileName())).getName()));
				song.setTitle(tmp.substring(0, tmp.length()-4));
			}
			addSongStatement.setString(2, song.getTitle());
			
			// Artist in der Datenbank suchen
			// ueberpruefen ob ein Artist eingetragen ist
			if(song.getArtist().length() > 1) {
				getArtistByNameStatement.setString(1, song.getArtist());
				ResultSet artists = getArtistByNameStatement.executeQuery();
				
				// Falls Artist vorhanden, ID uebernehmen
				if(artists.next()) {
					artistId = artists.getInt(1);
				}
				// Wenn Artist nicht vorhanden, neu anlegen und ID uebernehmen
				else {
					artistId = getMaxArtistId()+1;
					addArtistStatement.setInt(1, artistId);
					addArtistStatement.setString(2, song.getArtist());
					addArtistStatement.setInt(3, 126);
					addArtistStatement.execute();
				}
				artists.close();
			}
			
			/*
			// Falls kein Artist vorhanden, 
			else {
				getArtistByNameStatement.setString(1, "Unknown");
				ResultSet artists = getArtistByNameStatement.executeQuery();
				
				// Artist 'Unknown' uebernehmen,
				if(artists.next()) {
					artistId = artists.getInt(1);
				}
				// falls nicht vorhanden, anlegen und ID uebernehmen
				else {
					artistId = getMaxArtistId()+1;
					addArtistStatement.setInt(1, artistId);
					addArtistStatement.setString(2, "Unknown");
					addArtistStatement.setInt(3, 126);
					addArtistStatement.execute();
				}
				artists.close();
			}
			*/
			addSongStatement.setInt(3, artistId);
			
			// Jahr in der Datenbank suchen
			// ueberpruefen ob Jahr vorhanden ist
			if(song.getYear() != 0) {
				getYearByNumberStatement.setInt(1, song.getYear());
				ResultSet years = getYearByNumberStatement.executeQuery();
				
				if(years.next()) {
					yearId = years.getInt(1);
				}
				else {
					yearId = getMaxYearId()+1;
					addYearStatement.setInt(1, yearId);
					addYearStatement.setInt(2, song.getYear());
					addYearStatement.execute();
				}
				years.close();
			}
			else {
				// Falls keine Jahreszahl vorhanden
			}
			addSongStatement.setInt(5, yearId);
			
			// Album in der Datenbank suchen
			// �berpr�fen ob ein Album eingetragen ist
			if(song.getAlbum().length() > 1) {
				getAlbumByNameStatement.setString(1, song.getAlbum());
				ResultSet albums = getAlbumByNameStatement.executeQuery();
				
				// Falls Album vorhanden, ID �bernehmen
				if(albums.next()) {
					albumId = albums.getInt(1);
				}
				// Wenn nicht vorhanden, anlegen und ID �bernehmen
				else {
					albumId = getMaxAlbumId()+1;
					addAlbumStatement.setInt(1, albumId);
					addAlbumStatement.setString(2, song.getAlbum());
					addAlbumStatement.setInt(3, artistId);
					addAlbumStatement.setInt(4, yearId);
					addAlbumStatement.setInt(5, 126);
					addAlbumStatement.setInt(6, 1);
					addAlbumStatement.setInt(7, 0);
					addAlbumStatement.execute();
				}
				albums.close();
			}
			// Falls kein Albumeintrag vorhanden
			else {
				// Vorerst mal leer
			}
			addSongStatement.setInt(4, albumId);

			// Genre in der Datenbank suchen
			// �berpr�fen ob ein Genre eingetragen ist
			if(song.getGenre().length() > 1) {
				getGenreByNameStatement.setString(1, song.getGenre());
				ResultSet genres = getGenreByNameStatement.executeQuery();
				
				// Falls Genre vorhanden, ID �bernehmen
				if(genres.next()) {
					genreId = genres.getInt(1);
				}
				// Wenn nicht vorhanden, anlegen und ID �bernehmen
				else {
					genreId = getMaxGenreId()+1;
					addGenreStatement.setInt(1, genreId);
					addGenreStatement.setInt(2, 0);
					addGenreStatement.setString(3, song.getGenre());
					addGenreStatement.execute();
				}
				genres.close();
			}
			// Falls kein Genreeintrag vorhanden
			else {
				// Vorerst mal leer
			}
			
			addSongStatement.setInt(6, genreId);
			addSongStatement.setInt(7, song.getTrackNo());
			addSongStatement.setInt(8, song.getBitrate());
			addSongStatement.setInt(9, song.getLength());
			addSongStatement.setLong(10, song.getFileSize());
			addSongStatement.setString(11, song.getFileName());
			addSongStatement.setInt(12, (int)song.getLastModified());
			addSongStatement.setInt(13, song.getHashCode());
			
			addSongStatement.execute();
		}
		catch(SQLException ex) {
			System.out.println(ex);
		}
	}
	
	public void changeSong(MP3Song changedSong) {
		// TODO Auto-generated method stub

	}

	public boolean clearDB() {
		boolean error = false;
		System.out.println("Clearing DB.... ");
		// L�sche alle Songs aus der DB
		try {
			deleteSongsStatement.execute();	
		}
		catch(SQLException ex) {
			System.out.println("Error clearing Songs : " + ex);
			error = true;
		}
		// L�sche alle Artists aus der DB
		try {
			deleteArtistsStatement.execute();	
		}
		catch(SQLException ex) {
			System.out.println("Error clearing Artists : " + ex);
			error = true;
		}
		// L�sche alle Albums aus der DB
		try {
			deleteAlbumsStatement.execute();	
		}
		catch(SQLException ex) {
			System.out.println("Error clearing Albums : " + ex);
			error = true;
		}
		// L�sche alle Years aus der DB
		try {
			deleteYearsStatement.execute();	
		}
		catch(SQLException ex) {
			System.out.println("Error clearing Years : " + ex);
			error = true;
		}
		// L�sche alle Genres aus der DB
		try {
			deleteGenresStatement.execute();	
		}
		catch(SQLException ex) {
			System.out.println("Error clearing Genres : " + ex);
			error = true;
		}
		// L�sche alle Warnings
		try {
			dbConnection.clearWarnings();
		}
		catch(SQLException ex) {
			System.out.println("Error clearing SQLWarnings : " + ex);
			error = true;
		}
		return error;
	}
	
	// *****************************************
	// GetMax ID Methoden
	// *****************************************
	private int getMaxSongId() {
		try {
			ResultSet result = getMaxSongIdStatement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
//				result.close();
			}
			else {
				return 0;
			}
		}
		catch(SQLException ex) {
			System.out.println("Error while getting max SongID : " + ex);
			return 0;
		}
	}

	private int getMaxArtistId() {
		try {
			ResultSet result = getMaxArtistIdStatement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
//				result.close();
			}
			else {
				return 0;
			}
		}
		catch(SQLException ex) {
			System.out.println("Error while getting maxArtistID : " + ex);
			return 0;
		}
	}
	
	private int getMaxYearId() {
		try {
			ResultSet result = getMaxYearIdStatement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
//				result.close();
			}
			else {
				return 0;
			}
		}
		catch(SQLException ex) {
			System.out.println("Error while getting maxYearID : " + ex);
			return 0;
		}
	}

	private int getMaxGenreId() {
		try {
			ResultSet result = getMaxGenreIdStatement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
//				result.close();
			}
			else {
				return 0;
			}
		}
		catch(SQLException ex) {
			System.out.println("Error while getting maxGenreID : " + ex);
			return 0;
		}
	}
	
	private int getMaxAlbumId() {
		try {
			ResultSet result = getMaxAlbumIdStatement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
//				result.close();
			}
			else {
				return 0;
			}
		}
		catch(SQLException ex) {
			System.out.println("Error while getting maxAlbumID : " + ex);
			return 0;
		}
	}
}