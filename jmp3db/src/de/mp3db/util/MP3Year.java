package de.mp3db.util;

/**
 * @author alex
 *
 */
public class MP3Year {

	private int ID;
	private int year;
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int newID) {
		this.ID = newID;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setYear(int newYear) {
		this.year = newYear;
	}
	
	public String toString() {
		return String.valueOf(this.year);
	}
}
