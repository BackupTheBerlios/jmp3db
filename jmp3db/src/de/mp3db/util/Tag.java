//Created on 25.06.2004

package de.mp3db.util;

import helliker.id3.MP3File;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**     Klasse dient zum auslesen der Metadaten
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: Tag.java,v 1.7 2004/10/29 10:58:34 einfachnuralex Exp $
 *  
 *      $Log: Tag.java,v $
 *      Revision 1.7  2004/10/29 10:58:34  einfachnuralex
 *      Debugmethoden auskommentiert
 *
 *      Revision 1.6  2004/09/01 09:26:33  einfachnuralex
 *      parseGenre(String) implementiert
 *
 *      Revision 1.5  2004/08/25 15:15:08  einfachnuralex
 *      Ogg Support implementiert
 *
 *      Revision 1.4  2004/08/24 14:45:32  einfachnuralex
 *      Klasse überarbeitet / auf MP3Song Interface umgestellt
 *
 *      Revision 1.3  2004/08/24 12:48:04  einfachnuralex
 *      CVS Kommentare eingefügt
 *      parseTag(String) geändert
 *
 *
 */
public class Tag {

	private MP3Song song;
	public static final String[] GENRES = 
		{ "Blues","Classic Rock","Country","Dance","Disco","Funk","Grunge","Hip-Hop","Jazz","Metal","New Age","Oldies","Other",
			"Pop","R&B","Rap","Reggae","Rock","Techno","Industrial","Alternative","Ska","Death Metal","Pranks","Soundtrack","Euro-Techno",
			"Ambient","Trip-Hop","Vocal","Jazz+Funk","Fusion","Trance","Classical","Instrumental","Acid","House","Game","Sound Clip","Gospel",
			"Noise","AlternRock","Bass","Soul","Punk","Space","Meditative","Instrumental Pop","Instrumental Rock","Ethnic","Gothic","Darkwave",
			"Techno-Industrial","Electronic","Pop-Folk","Eurodance","Dream","Southern Rock","Comedy","Cult","Gangsta","Top 40","Christian Rap",
			"Pop/Funk","Jungle","Native American","Cabaret","New Wave","Psychedelic","Rave","Showtunes","Trailer","Lo-Fi","Tribal","Acid Punk","Acid Jazz",
			"Polka","Retro","Musical","Rock & Roll","Hard Rock","Folk","Folk-Rock","National Folk","Swing","Fast Fusion","Bebob","Latin","Revival","Celtic",
			"Bluegrass","Avantgarde","Gothic Rock","Progressive Rock","Psychedelic Rock","Symphonic Rock","Slow Rock","Big Band","Chorus","Easy Listening",
			"Acoustic","Humour","Speech","Chanson","Opera","Chamber Music","Sonata","Symphony","Booty Brass","Primus","Porn Groove","Satire","Slow Jam",
			"Club","Tango","Samba","Folklore","Ballad","Power Ballad","Rhytmic Soul","Freestyle","Duet","Punk Rock","Drum Solo","Acapella","Euro-House",
			"Dance Hall", "unknown"};
	
	public Tag(MP3Song newSong) {
		song = new MP3Song(newSong);
	}
	
	public Tag(String file) {
		this.parseTag(file);
	}

	public MP3Song getSong() {
		return song;
	}
	
	private void parseTag(String fileName) {
		if(fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".mp3")) {
			try {
				MP3File file = new MP3File(new File(fileName));
				song = new MP3Song();
				
				//song.setID(this.getMaxSongId()+1);
				song.setTitle(file.getTitle());
				song.setArtist(file.getArtist());
				song.setAlbum(file.getAlbum());
				try {
					song.setYear(Integer.parseInt(file.getYear()));
				}
				catch(NumberFormatException ex) {
					song.setYear(0);
				}
				int genreNo = parseGenre(file.getGenre());
				//System.out.println(file.getGenre() + " " + genreNo + " " + file.getTitle());
				if(genreNo != 0) {
					song.setGenre(GENRES[genreNo]);
					song.setGenre(genreNo);
				}
				else {
					song.setGenre(file.getGenre());
				}
				song.setTrackNo(file.getTrack());
				song.setBitrate(file.getBitRate());
				song.setLength((int)file.getPlayingTime());
				song.setFileSize(file.getFileSize());
				song.setFileName(fileName);
				song.setLastModified(new File(fileName).lastModified());
				song.setHashCode(new File(fileName).hashCode());
				song.setCodec(MP3Song.MP3);				
			}
			catch(Exception ex) {
				System.out.println("Error (Tag:parseTag) : " + ex);
			}
		}
		else if(fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".ogg")) {
			try {
				VorbisInfo vorbis = new VorbisInfo(new BufferedInputStream(new FileInputStream(fileName)));
				
				song = new MP3Song();				
				//System.out.println(vorbis.toString());
				if(vorbis.getComments("title") != null) {
					song.setTitle((String)vorbis.getComments("title").get(0));
				}
				if(vorbis.getComments("artist") != null) {
					song.setArtist((String)vorbis.getComments("artist").get(0));
				}
				if(vorbis.getComments("album") != null) {
					song.setAlbum((String)vorbis.getComments("album").get(0));
				}
				if(vorbis.getComments("date") != null) {
					song.setYear(Integer.parseInt((String)vorbis.getComments("date").get(0)));
				}
				if(vorbis.getComments("genre") != null) {
					int genreNo = parseGenre((String)vorbis.getComments("genre").get(0));
					//System.out.println((String)vorbis.getComments("genre").get(0) + " " + genreNo + " " + (String)vorbis.getComments("title").get(0));
					if(genreNo != 0) {
						song.setGenre(GENRES[genreNo]);
						song.setGenre(genreNo);
					}
					else {
						song.setGenre((String)vorbis.getComments("genre").get(0));
					}
				}
				if(vorbis.getComments("tracknumber") != null) {
					song.setTrackNo(Integer.parseInt((String)vorbis.getComments("tracknumber").get(0)));
				}
				song.setBitrate((int)vorbis.getBitrate());
				song.setLength(0);
				song.setFileSize(new File(fileName).length());
				song.setFileName(fileName);
				song.setLastModified(new File(fileName).lastModified());
				song.setHashCode(new File(fileName).hashCode());
				song.setCodec(MP3Song.OGG);				
			}
			catch(IOException ex) {
				System.out.println(ex);
			}
		}
	}
	
	private int parseGenre(String genreText) {
		int genreNo = 0;
		
		if(genreText.length() != 0) {
			if(genreText.charAt(0) == '(') {
				int end = genreText.indexOf(")");
				String no = "";
				if(end-1 != 1) {
					no = genreText.substring(1, end);
				}
				else {
					no = String.valueOf(genreText.charAt(1));
				}
				try {
					genreNo = Integer.parseInt(no);
				}
				catch(NumberFormatException ex) {
					System.out.println("Error (Tag:parseGenre) : " + ex);
				}
				genreText = genreText.substring(end+1, genreText.length());
			}
		
			boolean found = false;
			int j = 0;
			while(found != true) {
				if(j > 126) {
					break;
				}
				if(genreText.equalsIgnoreCase(GENRES[j])) {
					System.out.println(genreText + " equals " + GENRES[j]);
					found = true;
					if(genreNo == 0) {
						genreNo = j;
					}
					break;
				}
				j++;
			}
			if(genreNo > 126) {
				genreNo = 0;
			}
			return genreNo;
		}
		else {
			return 0;
		}
	}
	
	public boolean writeTag() {
		boolean success = false;

		if(song != null) {
			if(song.getCodec() == MP3Song.MP3) {
				try {
					MP3File file = new MP3File(new File(song.getFileName()));
					file.setTitle(song.getTitle());
					file.setArtist(song.getArtist());
					file.setAlbum(song.getAlbum());
					file.setYear(String.valueOf(song.getYear()));
					file.setGenre(song.getGenre());
					file.setTrack(song.getTrackNo());
		
					file.writeTags();
					success = true;
				}
				catch(Exception ex) {
					System.out.println("Error (Tag:writeTag) : " + ex); 
				}
			}
		}
		
		return success;
	}
}