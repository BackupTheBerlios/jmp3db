package de.mp3db;

import java.io.File;

import de.mp3db.gui.ManagerGUI;

public class Manager {

	public Manager() {
		if(!checkConfig()) {
			System.out.println("Config Path not exist. Create.");
			if(!createConfig()) {
				System.out.println("Error creating Config Path. Exit.");
				System.exit(0);
			}
		}
		
		ManagerGUI gui = new ManagerGUI();

		Mainlist mainlist = new Mainlist();
		DBTree tree = new DBTree();
		MP3Player player = new MP3Player();		
	}
	
	private boolean checkConfig() {
		File config = new File(System.getProperty("user.home") + "/.jmp3db");
		return config.exists();
	}
	
	private boolean createConfig() {
		boolean tmp = false;
		try {
			
			File config = new File(System.getProperty("user.home") + "/.jmp3db");
			config.mkdirs();
			tmp = true;
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return tmp;
	}

	public static void main(String args[]) {
		Manager system = new Manager();
	}
}