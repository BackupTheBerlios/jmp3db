package de.mp3db.util;

import java.util.Comparator;

/**     Klasse repräsentiert ein MP3-File
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Song.java,v 1.3 2004/09/01 09:28:02 einfachnuralex Exp $
 *  
 *      $Log: MP3Song.java,v $
 *      Revision 1.3  2004/09/01 09:28:02  einfachnuralex
 *      Feld int genreNo hinzugefügt
 *      kompett überarbeitet
 *
 *      Revision 1.2  2004/08/19 14:09:56  einfachnuralex
 *      CVS Kommentare hinzugefügt
 *
 *      Revision 1.1  2004/08/19 12:54:22  einfachnuralex
 *      Add to CVS
 *
 */
public class MP3Song implements Comparator {
	public static int OGG = 1;
	public static int MP3 = 2;
	
    private int     ID;
    private String  title;
    private String  artist;
    private String  album;
    private int     year;
    private String  genre;
    private int	genreNo;
    private int     trackNo;
    private int     bitrate;
    private int     length;
    private long    fileSize;
    private String  fileName;
    private long    lastModified;
    private int     hashCode;
    
    private int codec;
    
    public MP3Song() {
        ID = -1;   
        title = "";
        artist = "";
        album = "";
        year = 0;
        genre = "";
        trackNo = 0;
        bitrate = 0;
        length = 0;
        fileSize = 0;
        fileName = "";
        lastModified = 0;
        hashCode = 0;
        codec = 0;
    }

    public MP3Song(MP3Song newSong) {
    	this.ID = newSong.getID();
		this.title = newSong.getTitle();
		this.artist = newSong.getArtist();
		this.album = newSong.getAlbum();
		this.year = newSong.getYear();
		this.genre = newSong.getGenre();
		this.trackNo = newSong.getTrackNo();
		this.bitrate = newSong.getBitrate();
		this.length = newSong.getLength();
		this.fileSize =newSong.getFileSize();
		this.fileName = newSong.getFileName();
		this.lastModified = newSong.getLastModified();
		this.hashCode = newSong.getHashCode();
		this.codec = newSong.getCodec();
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
    
    public void setGenre(int genre) {
        this.genreNo = genre;
    }
    
    public int getGenreNo() {
        return this.genreNo;
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
    
    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }
    
    public int getHashCode() {
        return this.hashCode;
    }
    
    public int hashCode() {
    	int code = 0;
    	code = 37 * code + title.hashCode();
    	code = 37 * code + artist.hashCode();
    	code = 37 * code + album.hashCode();
    	code = 37 * code + year;
    	code = 37 * code + genre.hashCode();
    	code = 37 * code + trackNo;
    	code = 37 * code + bitrate;
    	code = 37 * code + length;
    	code = 37 * code + new Long(fileSize).hashCode();
    	code = 37 * code + fileName.hashCode();
    	return code;
    }
    
    public void setCodec(int codec) {
    	this.codec = codec;
    }
    
    public int getCodec() {
    	return codec;
    }
    
    // Vergleichsmethoden
    
    /**********************************************************************************
     * (alphabetischer) Vergleicht zweier MP3Songs<p>
     *********************************************************************************/          
    public int compare(Object o1, Object o2) {   
        try {
            MP3Song song1 = (MP3Song)o1;
            MP3Song song2 = (MP3Song)o2;
            return song1.getTitle().compareTo(song2.getTitle());
        }
        catch(Exception e) { 
            return 0;        	
        }
    }
    
    /************************************************************************************
     * Zwei Songs sind dann identisch, wenn sie in allen Attributen übereinstimmen
     ***********************************************************************************/
    public boolean equals(Object obj) {
        if(!(obj instanceof MP3Song))
            return false;
            
        MP3Song song = (MP3Song)obj;
        if(
            this.title.equals(song.getTitle()) &&
            this.artist.equals(song.getArtist()) &&
            this.album.equals(song.getAlbum()) &&
            this.year == song.getYear() &&
            this.genre == song.getGenre() &&
            this.trackNo == song.getTrackNo() &&
            this.bitrate == song.getBitrate() &&
            this.length == song.getLength() &&
            this.fileSize == song.getFileSize() &&
            this.fileName.equals(song.getFileName())) {
            return true;
        }            
        return false;
    }
    
    /**********************************************************************************
     * Überschreiben von java.lang.Object.toString();
     *********************************************************************************/
    public String toString() { 
        return this.title;
    }
}