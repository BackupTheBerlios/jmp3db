package de.mp3db.util;

/**
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Album.java,v 1.2 2004/08/25 09:51:53 einfachnuralex Exp $
 *  
 *      $Log: MP3Album.java,v $
 *      Revision 1.2  2004/08/25 09:51:53  einfachnuralex
 *      Complete Feld und passende Methoden hinzugefügt
 *
 *      Revision 1.1  2004/08/19 12:54:22  einfachnuralex
 *      Add to CVS
 *
 */
public class MP3Album implements java.util.Comparator {
    private int		ID;
    private String	title;
    private String	artist;
    private int		year;
    private String	genre;
    private String	cover;
    private int 		complete;
    
    public MP3Album() {
    }
    
    public MP3Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }
    
    public void setID(int id) {
        this.ID = id;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public void setTitle(String title) {
        this.title = title;
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
    
    public String getCover() {
        return this.cover;
    }
    
    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
    
    public int getComplete() {
        return this.complete;
    }
    
    /***************************************************************************
     * (alphabetischer) Vergleicht zweier MP3Songs<p>
     **************************************************************************/          
    public int compare(Object o1, Object o2) {   
        try {
            MP3Album a1 = (MP3Album)o1;
            MP3Album a2 = (MP3Album)o2;
            return a1.getTitle().compareTo(a2.getTitle());
        }
        catch(Exception e) {
            System.out.println("Exception: " +e.getClass().getName()+"; "+e.getMessage());
        }
        return 0;
    }
    
    /***************************************************************************
     * Zwei Alben sind dann identisch, wenn sie in Titel und Artist übereinstimmen
     **************************************************************************/
    public boolean equals(Object obj) {
        if(obj instanceof MP3Album) {
            MP3Album album = (MP3Album)obj;
            return getTitle().equals(album.getTitle()) && getArtist().equals(album.getArtist());
        }            
        return false;
    }

   /****************************************************************************
    * Überschreiben von java.lang.Object.toString()
    * gibt den Albumtitel sowie die einzelnen Tracks in [] zurück
    ***************************************************************************/
    
    public String toString() {
        return this.getTitle();
    }
}