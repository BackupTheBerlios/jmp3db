package de.mp3db.util;

/**
 * @author alex
 *
 */
public class MP3Genre {

	private int ID;
	private String genreTitle;
	private int genreID;
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int newID) {
		this.ID = newID;
	}
	
	public String getGenre() {
		return this.genreTitle;
	}
	
	public void setGenre(int genre) {
		this.genreID = genre;
	}
	
	public void setGenre(String genre) {
		this.genreTitle = genre;
	}

	public String toString() {
		return this.genreTitle;
	}
}
