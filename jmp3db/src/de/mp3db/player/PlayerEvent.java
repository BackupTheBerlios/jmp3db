package de.mp3db.player;

/**
 * @author alex
 *
 */
public class PlayerEvent {

	private int status;
	private int pos;
	private int max;
	
	public PlayerEvent(int status, int pos, int max) {
		this.pos = pos;
		this.max = max;
		this.status = status;
	}

	public int getStatus() {
		return this.status;	
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public int getMax() {
		return this.max;
	}
}
