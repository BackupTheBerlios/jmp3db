package de.mp3db.db;

/**
 * @author alex
 *
 */
public class ScanEvent {

	private int pos;
	private int max;
	private String fileName;
	
	public ScanEvent(String fileName, int pos, int max) {
		this.pos = pos;
		this.max = max;
		this.fileName = fileName;
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public int getMax() {
		return this.max;
	}
	
	public String getFileName() {
		return this.fileName;
	}
}
