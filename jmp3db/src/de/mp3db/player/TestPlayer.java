package de.mp3db.player;

import java.io.File;

/**
 * @author alex
 *
 */
public class TestPlayer {

	public static void main(String[] args) {
		Player test = new Player();
		test.setSource(new File("D:\\test.mp3"));
		test.play();
	}
}
