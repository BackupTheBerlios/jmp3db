//Created on 25.06.2004

package de.mp3db.util;

import helliker.id3.MP3File;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**     Klasse dient zum auslesen der TAG Informationen
 *      
 *      @author $Author: einfachnuralex $
 *
 *      @version $Id: Tag.java,v 1.5 2004/08/25 15:15:08 einfachnuralex Exp $
 *  
 *      $Log: Tag.java,v $
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
	
	public Tag(MP3Song newSong) {
		song = newSong;
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
				song.setGenre(file.getGenre());
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
				System.out.println(ex);
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
				else {
					song.setTitle("");
				}
				if(vorbis.getComments("artist") != null) {
					song.setArtist((String)vorbis.getComments("artist").get(0));
				}
				else {
					song.setArtist("");
				}
				if(vorbis.getComments("album") != null) {
					song.setAlbum((String)vorbis.getComments("album").get(0));
				}
				else {
					song.setAlbum("");
				}
				if(vorbis.getComments("date") != null) {
					song.setYear(Integer.parseInt((String)vorbis.getComments("date").get(0)));
				}
				else {
					song.setYear(0);
				}
				if(vorbis.getComments("genre") != null) {
					song.setGenre((String)vorbis.getComments("genre").get(0));
				}
				else {
					song.setGenre("");
				}
				if(vorbis.getComments("tracknumber") != null) {
					song.setTrackNo(Integer.parseInt((String)vorbis.getComments("tracknumber").get(0)));
				}
				else {
					song.setTrackNo(0);
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
	
	public boolean writeTag() {
		boolean success = false;

		if(song != null) {
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
			}
		}
		
		return success;
	}
}