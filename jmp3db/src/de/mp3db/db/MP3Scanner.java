package de.mp3db.db;

import java.io.File;
import java.util.Vector;

/**
 * @author alex
 *
 */
public class MP3Scanner {
	private Vector songs;
	private Vector ext;

	public MP3Scanner(File root, Vector ext) {
		songs = new Vector();
		
		this.ext = ext;
		ext.trimToSize();

//		ConfigProperty cp = new ConfigProperty("config/mp3db.conf");
//		File root = new File(cp.getString("Rootdir"));

		if(root.isDirectory()) {
			scan(root);
		}
	}
	
	private void scan(File rootDir) {
		try {
			File[] dir = rootDir.listFiles();

			for(int i = 0; i<dir.length; i++) {
				if(dir[i].isDirectory()) {
					scan(dir[i]);
				}
				else {
					for(int j=0; j<ext.size(); j++) {
						if(dir[i].toString().substring(dir[i].toString().length()-4, dir[i].toString().length()).equalsIgnoreCase((String)ext.get(j))) {
							songs.add(dir[i].toString());
						}
					}
				}
/*                else if(dir[i].toString().substring(dir[i].toString().length()-4, dir[i].toString().length()).equalsIgnoreCase(".mp3")) {
					songs.add(dir[i].toString());
				}
*/
				
			}
		}
		catch(NullPointerException ex) {
			System.out.println(ex.getStackTrace());
		}
		catch(Exception e) {
			System.out.println(e.getClass().getName()+" in MP3Scanner.scan: "+e.getMessage());
		}
	}
	
	public Vector getFilenames() {
		songs.trimToSize();
		return songs;
	}
}