package de.mp3db.util;

/**
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: MP3Artist.java,v 1.1 2004/08/19 12:54:22 einfachnuralex Exp $
 *  
 *      $Log: MP3Artist.java,v $
 *      Revision 1.1  2004/08/19 12:54:22  einfachnuralex
 *      Add to CVS
 *
 *      Revision 1.4  2003/03/10 08:14:36  stefan
 *      SQL: addSongs funktionsfähig
 *<br>
 *      Revision 1.3  2003/01/02 08:25:10  stefan<br>
 *      Änderungen in equals-Methode<br>
 *		<br>
 *      Revision 1.2  2002/11/05 14:08:35  stefan<br>
 *      - Bugfix in "equals()"<br>
 *
 */
public class MP3Artist implements java.util.Comparator
{
    private int		ID;
    private String	name;
    private String	genre;
    
    /**		Konstruktor
     * 		@param name Name des Artists
     */
    public MP3Artist(String name) {
        this.name = name;
    }
    
    /**		Setzt die ID des Artists
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
    
    /**		Setzt den Namen des Artists
     * 		@param name Name des Artists
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**		Gibt den Namen des Artists zurück
     * 		@return Name des Artists
     */
    public String getName() {
        return this.name;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
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