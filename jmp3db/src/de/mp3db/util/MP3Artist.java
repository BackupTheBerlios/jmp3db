package de.mp3db.util;

/**
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Artist.java,v 1.2 2004/08/25 09:50:07 einfachnuralex Exp $
 *  
 *      $Log: MP3Artist.java,v $
 *      Revision 1.2  2004/08/25 09:50:07  einfachnuralex
 *      Genre Feld und passende Methoden hinzugefügt
 *      Konstruktor von MP3Artist(String) zu MP3Artist() geändert
 *
 *      Revision 1.1  2004/08/19 12:54:22  einfachnuralex
 *      Add to CVS
 *
 */
public class MP3Artist implements java.util.Comparator
{
    private int		ID;
    private String	name;
    private String	genre;
    
    /**	Konstruktor
     * 		@param name Name des Artists
     */
    public MP3Artist() {

    }
    
    /**	Setzt die ID des Artists
     * 		@param id ID des Artists
     */
    public void setID(int id) {
        this.ID = id;
    }
    
    /**		Gibt die ID zurück
     * 		@return Artist-ID
     */
    public int getID() {
        return this.ID;
    }
    
    /**	Setzt den Namen des Artists
     * 		@param name Name des Artists
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**	Gibt den Namen des Artists zurück
     * 		@return Name des Artists
     */
    public String getName() {
        return this.name;
    }

    /**	Setzt das Genre des Artist
     * 		@param Genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**	Gibt das Genre des Artist zurück
     * 		@return Genre
     */
    public String getGenre() {
        return this.genre;
    }
    
    /**********************************************************************************
     * (alphabetischer) Vergleicht zweier MP3Artists<p>
     *********************************************************************************/          
    public int compare(Object o1, Object o2) {   
        try {
            MP3Artist a1 = (MP3Artist)o1;
            MP3Artist a2 = (MP3Artist)o2;
            return a1.getName().compareTo(a2.getName());
        }
        catch(Exception e) { }
        return 0;
    }
    
    /************************************************************************************
     * Zwei Songs sind dann identisch, wenn sie in allen Attributen übereinstimmen
     ***********************************************************************************/
    public boolean equals(Object obj) {
        if(obj instanceof MP3Artist) {
            MP3Artist artist = (MP3Artist)obj;
            return getName().equals(artist.getName());
        }            
        return false;
    }
    
    
   /**************************************************************************************
    * Überschreiben von java.lang.Object.toString()
    * gibt den Albumtitel sowie die einzelnen Tracks in [] zurück
    *************************************************************************************/
    
    public String toString() {
        return this.name;
    }
    
    public String toVerboseString() {
    	StringBuffer buffer = new StringBuffer("Name: ");
    	buffer.append(this.name);
    	buffer.append("\nID: ");
    	buffer.append(this.ID);
    	buffer.append("\nGenre: ");
    	buffer.append(this.genre);
    	return buffer.toString();
    }
}