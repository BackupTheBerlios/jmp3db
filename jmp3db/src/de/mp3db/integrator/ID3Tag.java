package de.mp3db.integrator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class ID3Tag {
	private static final String ISO = new String("ISO-8859-1");

	private static final String[] GENRES = 
			{ "Blues","Classic Rock","Country","Dance","Disco","Funk","Grunge","Hip-Hop","Jazz","Metal","New Age","Oldies","Other","Pop","R&B","Rap","Reggae","Rock","Techno","Industrial","Alternative","Ska","Death Metal","Pranks","Soundtrack","Euro-Techno","Ambient","Trip-Hop","Vocal","Jazz+Funk","Fusion","Trance","Classical","Instrumental","Acid","House","Game","Sound Clip","Gospel","Noise","AlternRock","Bass","Soul","Punk","Space","Meditative","Instrumental Pop","Instrumental Rock","Ethnic","Gothic","Darkwave","Techno-Industrial","Electronic","Pop-Folk","Eurodance","Dream","Southern Rock","Comedy","Cult","Gangsta","Top 40","Christian Rap","Pop/Funk","Jungle","Native American","Cabaret","New Wave","Psychedelic","Rave","Showtunes","Trailer","Lo-Fi","Tribal","Acid Punk","Acid Jazz","Polka","Retro","Musical","Rock & Roll","Hard Rock","Folk","Folk-Rock","National Folk","Swing","Fast Fusion","Bebob","Latin","Revival","Celtic","Bluegrass","Avantgarde","Gothic Rock","Progressive Rock","Psychedelic Rock","Symphonic Rock","Slow Rock","Big Band","Chorus","Easy Listening","Acoustic","Humour","Speech","Chanson","Opera","Chamber Music","Sonata","Symphony","Booty Brass","Primus","Porn Groove","Satire","Slow Jam","Club","Tango","Samba","Folklore","Ballad","Power Ballad","Rhytmic Soul","Freestyle","Duet","Punk Rock","Drum Solo","Acapella","Euro-House","Dance Hall", "unknown"};

	String title = new String();
	String artist = new String();
	String album = new String();
	String year = new String();
	String comment = new String();
	boolean validTag = false;
	String message = new String();
	byte[] newTags = new byte[128];
	byte track;
	byte genre;

	public ID3Tag(String filename) {
		readTag(filename);
	}

	public ID3Tag() {
		setTitle("");
		setArtist("");
		setAlbum("");
		setYear("");
		setComment("");
		setTrack((byte)0);
		setGenre((byte)126);
	}

	public void readTag(String filename) {
        byte[] tagBytes = new byte[128];
		try {
			RandomAccessFile in = new RandomAccessFile(filename,"r");			
			in.seek(in.length ()-128);
	        in.read(tagBytes);
			in.close();
		}
		catch(IOException io) { addMessage("File not found : " + filename.toString()); }
		
		try {
			if( ((char)tagBytes[0] == 'T') && ((char)tagBytes[1] == 'A') && ((char)tagBytes[2] == 'G') ) {
				setTitle(new String(tagBytes,3,30, ISO));
				setArtist(new String(tagBytes,33,30, ISO));
				setAlbum(new String(tagBytes,63,30, ISO));
				setYear(new String(tagBytes,93,4, ISO));
				setComment(new String(tagBytes,97,28, ISO));
				setTrack(tagBytes[126]);
				setGenre(tagBytes[127]);

				addMessage("ID3Tag vorhanden");
				validTag = true;
			}
			else {
				addMessage("ID3Tag nicht vorhanden");
				validTag = false;
			}
		}
		catch(UnsupportedEncodingException ex) {
			addMessage("Error in the encoding!");
			validTag = false;
		}

	}
	
	public String getTitle() { 
		return title; 
	}

	public void setTitle(String tit) { 
		title = tit.trim(); 
	}

	public String getArtist() { 
		return artist; 
	}

	public void setArtist(String art) { 
		artist = art.trim(); 
	}

	public String getAlbum() { 
		return album; 
	}

	public void setAlbum(String alb) { 
		album = alb.trim(); 
	}

	public String getYear() {  
		return year; 
	}

	public void setYear(String y) { 
		year = y.trim(); 
	}

	public String getComment() { 
		return comment;
	}

	public void setComment(String c) { 
		comment = c.trim(); 
	}

	public void setTrack(byte t) {
		if(t >= 32) track = 0;
		else track = t;
	}

	public String getTrackS () { 
		return new StringBuffer().append(track).toString();
	}

	public byte getTrack() {
		return track;
	}

	public byte getGenre() {  
		return genre; 
	}

	public String getGenreS() { 
		return GENRES[genre];
	}

	public void setGenre (byte g) {
		if (g>=0 && g<126) genre = g;

		else  genre = 126;
	}

	public void addMessage(String newMessage) {
		message = newMessage;
	}

	public String getMessage() {
		return message;
	}

	public boolean isTagValid() {
		return validTag;
	}

//************** ID3Tag-Writer Methoden ************************

	private byte[] getByteArray(String txt, int num) {
		byte[] newTag, newBytes;
		try {
			newBytes = txt.trim().getBytes("ISO-8859-1"); // Padding should always be done 
		}
		catch (UnsupportedEncodingException ex) { newBytes = txt.trim().getBytes(); }

		// with 0x00, not whitespace.
		newTag = new byte[num];
		// ***** SEHR häßliche Bruteforce-Methode!
		if(txt.length() != 0) {
			if(newBytes.length < num) {
				for(int i=0;i<newBytes.length;i++) newTag[i]=newBytes[i];
				for(int j=newBytes.length;j<num;j++) newTag[j]=0;
			}
			else {
				for(int i =0; i<num;i++) newTag[i]=newBytes[i];
			}
		}
		else {
			for (int i=0;i<num;i++) newTag[i]=0;
		}
		return newTag;
	}

	private void makeNewTag() {
		byte [] t;
		int i =0;
		newTags[0]=(byte)'T';
		newTags[1]=(byte)'A';
		newTags[2]=(byte)'G';
		t = getByteArray(getTitle(),30);
		for(i=0; i<30;i++) 
			newTags[i+3]=t[i];
		t = getByteArray(getArtist(),30);
		for(i=0; i<30;i++) 
			newTags[i+33]=t[i];
		t = getByteArray(getAlbum(),30);
		for(i=0; i<30;i++) 
			newTags[i+63]=t[i];
		t = getByteArray(getYear(),4);
		for(i=0; i<4;i++) 
			newTags[i+93]=t[i];
		t = getByteArray(getComment(),29);
		for(i=0; i<29;i++) 
			newTags[i+97]=t[i];
		newTags[126]=getTrack();
		newTags[127]=getGenre();
	}

	public void writeID3Tag(String filename) {
		makeNewTag();
		try {
			RandomAccessFile in = new RandomAccessFile(filename,"rw");			

			byte[] tagBytes = new byte[128];
			in.seek(in.length()-128);
			in.read(tagBytes);

			if( ((char)tagBytes[0] == 'T') && ((char)tagBytes[1] == 'A') && ((char)tagBytes[2] == 'G') ) {
				in.seek(in.length()-128);
				in.write(newTags);
				in.close();
			}
			else {
				in.seek(in.length());
				in.write(newTags);
				in.close();
			}
		}
		catch(IOException io) { addMessage("Error while writing ID3Tag. File : " + filename.toString()); }
	}
}