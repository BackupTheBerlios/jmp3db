package de.mp3db.integrator;

import java.util.Comparator;

/**     Klasse repräsentiert ein MP3-File
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Song.java,v 1.1 2004/08/19 12:54:22 einfachnuralex Exp $
 *  
 *      $Log: MP3Song.java,v $
 *      Revision 1.1  2004/08/19 12:54:22  einfachnuralex
 *      Add to CVS
 *
 *      Revision 1.3  2003/01/02 08:25:10  stefan
 *      Änderungen in equals-Methode
 *
 *      Revision 1.2  2002/11/05 14:08:35  stefan
 *      - Bugfix in "equals()"
 *
 */
public class MP3Song implements Comparator {

    private int		ID;
    private String	title;
    private String	artist;
    private String	album;
    private String	year;
    private int		genre;
    private int		trackNo;
    private int		bitrate;
    private int		length;
    private long	fileSize;
    private String	fileName;
    private long	lastModified;
    private int		hashCode;
    private String	alias;
    
    private String 	albumDir;
    private String 	artistDir;
	private String 	artistFile;
	private String 	albumFile;
	private String 	trackFile;
	private String 	titleFile;	
	
    public MP3Song() {
         
        ID = -1;   
        title = "";
        artist = "";
        album = "";
        genre = 126;
        fileName = "";
    }

	public void setAlias(String newAlias) {
		this.alias = newAlias;
	}
	
	public String getAlias() {
		return this.alias;
	}

	public void setArtistFile(String newArtistFile) {
		this.artistFile = newArtistFile;
	}
	
	public void setAlbumFile(String newAlbumFile) {
		this.albumFile = newAlbumFile;
	}
	
	public void setTrackFile(String newTrackFile) {
		this.trackFile = newTrackFile;
	}
	
	public void setTitleFile(String newTitleFile) {
		this.titleFile = newTitleFile;
	}

	public String getArtistFile() {
		return this.artistFile;		
	}

	public String getAlbumFile() {
		return this.albumFile;		
	}

	public String getTrackFile() {
		return this.trackFile;		
	}

	public String getTitleFile() {
		return this.titleFile;		
	}

	public String getAlbumDir() {
		return this.albumDir;		
	}

	public void setAlbumDir(String newAlbum) {
		this.albumDir = newAlbum;
	}

	public String getArtistDir() {
		return this.artistDir;		
	}
	
	public void setArtistDir(String newArtist) {
		this.artistDir = newArtist;
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
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public String getYear() {
        return this.year;
    }
    
    public void setGenre(int genre) {
        this.genre = genre;
    }
    
    public int getGenre() {
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
    	code = 37 * code + year.hashCode();
    	code = 37 * code + genre;
    	code = 37 * code + trackNo;
    	code = 37 * code + bitrate;
    	code = 37 * code + length;
    	code = 37 * code + new Long(fileSize).hashCode();
    	code = 37 * code + fileName.hashCode();
    	return code;
    }
    
    // Vergleichsmethoden
    
    /**********************************************************************************
     * (alphabetischer) Vergleicht zweier MP3Songs<p>
     *********************************************************************************/          
    public int compare(Object o1, Object o2) {   
        try
        {
            MP3Song song1 = (MP3Song)o1;
            MP3Song song2 = (MP3Song)o2;
            return song1.getTitle().compareTo(song2.getTitle());
        }
        catch(Exception e)
        {
            System.out.println("Exception: " +e.getClass().getName()+"; "+e.getMessage());
        }
        return 0;
    }
    
    /************************************************************************************
     * Zwei Songs sind dann identisch, wenn sie in allen Attributen übereinstimmen
     ***********************************************************************************/
    public boolean equals(Object obj)
    {
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
            this.fileName.equals(song.getFileName()))
        {
            return true;
        }            
        return false;
    }
    
    /**********************************************************************************
     * Überschreiben von java.lang.Object.toString();
     *********************************************************************************/
    public String toString()
    { 
        return this.alias;
    }

}