package de.mp3db.integrator;

import java.io.File;

/**    Klasse scannt ein Verzeichnis nach MP3 Files ab und zerlegt diese.
 *      
 *      @author alex
 *
 */
public class Scanner {

	private MP3Song[] songs;
	private File root;
	private int totalFiles = 0;

	public Scanner(File newRoot) {

		this.root = newRoot;
		getTotalFiles(root);

		songs = new MP3Song[totalFiles];
		scan(this.root);
	}
	
	private void scan(File rootDir) {
		int actFile = 0;
		if(rootDir.isDirectory()) {
			File[] dir = rootDir.listFiles();
			
			for(int i = 0; i<dir.length; i++) {
				if(dir[i].isDirectory()) {
					File[] newDir = dir[i].listFiles();

					for(int h = 0; h<newDir.length; h++) {
						if(newDir[h].toString().substring(newDir[h].toString().length()-4, newDir[h].toString().length()).equalsIgnoreCase(".mp3")) {
							read(newDir[h], actFile, dir[i].getName().toString());
							actFile++;
						}
					}

				}
                else if(dir[i].toString().substring(dir[i].toString().length()-4, dir[i].toString().length()).equalsIgnoreCase(".mp3")) {
						read(dir[i], actFile, "");
						actFile++;
				}
			}
		}
	}

	private void read(File newSong, int index, String albumName) {
		ID3Tag idTag = new ID3Tag(newSong.toString());

		songs[index] = new MP3Song();
		songs[index].setFileName(newSong.toString());
		songs[index].setAlbumDir(albumName);
		songs[index].setArtistDir(this.root.getName());

		if(idTag.isTagValid()) {
			songs[index].setTitle(idTag.getTitle());
			songs[index].setArtist(idTag.getArtist());
			songs[index].setAlbum(idTag.getAlbum());
			songs[index].setYear(idTag.getYear());
			songs[index].setGenre((int)idTag.getGenre());
			songs[index].setTrackNo((int)idTag.getTrack());
		}
		
		String filename = newSong.getName();
		filename = filename.replaceAll("_", " ");
		filename = filename.substring(0, filename.length()-4).trim();
		songs[index].setAlias(filename);
		
		String trackFile = "";
		String albumFile = "";
		String artistFile = "";
		String titleFile = "";
				
		char one = filename.charAt(0);
		char sec = filename.charAt(1);
		char tir = filename.charAt(2);
		char four = filename.charAt(3);	
		
//		StringBuilder tmp = new StringBuilder();
		StringBuffer tmp = new StringBuffer();
		if(Character.isDigit(one)) {
			tmp.append(filename.substring(1, filename.length()));
			trackFile = Character.toString(one);
		}	
		
		if(Character.isDigit(sec)) {
			tmp.append(filename.substring(2, filename.length()));				
			trackFile = Character.toString(one)+Character.toString(sec);

			if(sec == '.' || sec == '-' || sec == ' ') {
				tmp.append(filename.substring(2, filename.length()));	
			}
			
			if(tir == '.' ||  tir == '-') {
				tmp.append(filename.substring(3, filename.length()).trim());
			}
			
			if(four == '-' || four == '.') {
				tmp.append(filename.substring(4, filename.length()));			
			}
		}
		else {
			tmp.append(filename);
		}
		
		filename = tmp.toString().trim();
				
		int i = getStrings(filename);
		int[] first = new int[i];
		
		for(int h = 0; h<i;h++) {
			if(h == 0)
				first[h] = filename.indexOf("-");
			else 
				first[h] = filename.indexOf("-", first[h-1]+1);
		}
		
		if(i != 0) {
			if(i == 1) {
				artistFile = new String((filename.substring(0, first[0])).trim());
				titleFile = new String((filename.substring(first[0]+1, filename.length())).trim());
			}
			else if(i == 2) {
				albumFile = new String((filename.substring(0, first[0])).trim());
				artistFile = new String((filename.substring(first[0]+1, first[1])).trim());
				titleFile = new String((filename.substring(first[1]+1, filename.length())).trim());
			}
		}
		else {
			titleFile = new String(filename.trim());
		}

		songs[index].setTrackFile(trackFile);
		songs[index].setAlbumFile(albumFile);
		songs[index].setArtistFile(artistFile);
		songs[index].setTitleFile(titleFile);
	}	

	private void getTotalFiles(File selRoot) {
		if(selRoot.isDirectory()) {
			File[] dir = selRoot.listFiles();
			
			for(int i = 0; i<dir.length; i++) {
				if(dir[i].isDirectory()) {
					getTotalFiles(dir[i]);
				}
				else if(dir[i].toString().substring(dir[i].toString().length()-4, dir[i].toString().length()).equalsIgnoreCase(".mp3")) {
					totalFiles++;
				}
			}
		}
	}

	private int getStrings(String name)  {
		int i = 0;
		int h = 0;

		while(h != -1) {
			h = name.indexOf('-', h+1);
			if(h != -1)
				i++;
		}
		return i;			
	}

	public MP3Song[] getResult() {
		return this.songs;
	}
}