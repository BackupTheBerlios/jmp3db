//Created on 25.06.2004

package de.mp3db.util;

import helliker.id3.MP3File;

import java.io.File;

/**     Klasse dient zum auslesen der TAG Informationen
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: Tag.java,v 1.3 2004/08/24 12:48:04 einfachnuralex Exp $
 *  
 *      $Log: Tag.java,v $
 *      Revision 1.3  2004/08/24 12:48:04  einfachnuralex
 *      CVS Kommentare eingefügt
 *      parseTag(String) geändert
 *
 *
 */
public class Tag {

	public static int OGG = 1;
	public static int MP3 = 2;
	
	private int     ID;
	private String  title;
	private String  artist;
	private String  album;
	private int     year;
	private String  genre;
	private int     trackNo;
	private int     bitrate;
	private int     length;
	private long    fileSize;
	private String  fileName;
	private long    lastModified;
    
	private int codec;

	public Tag(MP3Song song) {
		ID = song.getID();
		title = song.getTitle();
		artist = song.getArtist();
		album = song.getAlbum();
		year = song.getYear();
		genre = song.getGenre();
		trackNo = song.getTrackNo();
		bitrate = song.getBitrate();
		length = song.getLength();
		fileSize = song.getFileSize();
		fileName = song.getFileName();
		lastModified = song.getLastModified();
	}
	
	public Tag(String file) {
		this.parseTag(file);
		
	}

	private void parseTag(String fileName) {
		if(fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".mp3")) {
		codec = MP3;
			try {
				MP3File file = new MP3File(new File(fileName));
				title = new String(file.getTitle());
				artist = new String(file.getArtist());
				album = new String(file.getAlbum());
				try {
					year = Integer.parseInt(file.getYear());
				}
				catch(NumberFormatException ex) {
					year = 0;
				}
				genre = file.getGenre();
				trackNo = file.getTrack();
				bitrate = file.getBitRate();
				length = (int)file.getPlayingTime();
				fileSize = file.getFileSize();
				this.fileName = new String(fileName);
				lastModified = new File(fileName).lastModified();
				
			}
			catch(Exception ex) {
				System.out.println(ex);
			}
		}
		else if(fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".ogg")) {
			
		}
		 		
	}
	
	public boolean writeTag() {
	boolean success = false;
		try {
			MP3File file = new MP3File(new File(fileName));
			file.setTitle(title);
			file.setArtist(artist);
			file.setAlbum(album);
			file.setYear(String.valueOf(year));
			file.setGenre(genre);
			file.setTrack(trackNo);

			file.writeTags();
			success = true;
		}
		catch(Exception ex) {
		}
		
		return success;
	}
    
	public void setTitle(String title) {
		this.title = title;
	}
    
	public void setID(int id) {
		this.ID = id;
	}
    
	public int getID() {
		return this.ID;
	}
    
	public String getTitle() {
		return this.title;
	}
    
	public void setArtist(String artist) {
		this.artist = artist;
	}
    
	public String getArtist() {
		return this.artist;
	}
    
	public void setAlbum(String album) {
		this.album = album;
	}
    
	public String getAlbum() {
		return this.album;
	}
    
	public void setYear(int year) {
		this.year = year;
	}
    
	public int getYear() {
		return this.year;
	}
    
	public void setGenre(String genre) {
		this.genre = genre;
	}
    
	public String getGenre() {
		return this.genre;
	}
    
	public void setTrackNo(int trackNo) {
		this.trackNo = trackNo;
	}
    
	public int getTrackNo() {
		return this.trackNo;
	}
    
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
    
	public int getBitrate() {
		return this.bitrate;
	}
    
	public void setLength(int length) {
		this.length = length;
	}
    
	public int getLength() {
		return this.length;
	}
    
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
    
	public long getFileSize() {
		return this.fileSize;
	}
    
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
	public String getFileName() {
		return this.fileName;
	}
    
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
    
	public long getLastModified() {
		return this.lastModified;
	}
    
	public void setCodec(int codec) {
		this.codec = codec;
	}
    
	public int getCodec() {
		return codec;
	}
}