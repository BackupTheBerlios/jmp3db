package de.mp3db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		MultiPanel multi = new MultiPanel();
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
//			File configFile = new File(System.getProperty("user.dir") + "/config/mp3db.conf");
//			File configData = new File(System.getProperty("user.home") + "/.jmp3db/mp3db.conf");
			File configLog = new File(System.getProperty("user.home") + "/.jmp3db/sql.log");
			configLog.createNewFile();
			File configDB = new File(System.getProperty("user.dir") + "/data");
			File configNewDB = new File(System.getProperty("user.home") + "/.jmp3db/data");
//			copy(configFile, configData);
			copyDirectory(configDB, configNewDB);

			tmp = true;
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return tmp;
	}

	/* File Copy
	 * @found at http://javaalmanac.com/
	 */
    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

	/* Directory Copy
	 * @found at http://javaalmanac.com/
	 */
    private void copyDirectory(File srcDir, File dstDir) throws IOException {
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdir();
            }
    
            String[] children = srcDir.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(srcDir, children[i]),
                                     new File(dstDir, children[i]));
            }
        } 
        else {
            copy(srcDir, dstDir);
        }
    }
    
	public static void main(String args[]) {
		Manager system = new Manager();
	}
}