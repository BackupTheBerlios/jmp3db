package de.mp3db.player;

/**
 * @author alex
 *
 */
public interface PlayerListener {

	public void updatePos(PlayerEvent ev);
	
	public void updateStatus(PlayerEvent ev);
}
