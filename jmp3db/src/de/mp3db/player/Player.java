package de.mp3db.player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

/**
 * @author alex
 *
 */
public class Player implements Runnable  {

	public static int PLAY = 1;
	public static int STOP = 2;
	public static int PAUSE = 3;
	public static int READY = 4;

	private PlayerListener listener;
	private InputStream song;

	/** The MPEG audio bitstream.*/
	private Bitstream bitstream;
	/** The MPEG audio decoder. */
	private Decoder decoder;
	/** The AudioDevice the audio samples are written to. */
	private AudioDevice audio;
	/** Has the player been closed? */
	private boolean closed = false;
	/** Has the player played back all frames from the stream? */
	private boolean complete = false;
	private int lastPosition = 0;

	private File songFile;

	public Player() {
		
	}
	
	public void addPlayerListener(PlayerListener listener) {
		this.listener = listener;
	}
	
	public void setSource(File song) {
		try {
			this.songFile = song;
			this.song = new BufferedInputStream(new FileInputStream(song));
		}
		catch(FileNotFoundException ex) {
		}
		this.initLine();
	}
	
	private void initLine() {
		try {
			bitstream = new Bitstream(song);
	
			audio = FactoryRegistry.systemRegistry().createAudioDevice();
			audio.open(decoder = new Decoder());
		}
		catch(JavaLayerException ex) {
		}
	}
	
	public void play() {
		boolean ret = true;
		int frames = Integer.MAX_VALUE;
		// report to listener
		
		while (frames-- > 0 && ret) {
			try {
				ret = decodeFrame();
			}
			catch(JavaLayerException ex) {
				ret = false;
			}
		}

//		if (!ret)
		{
			// last frame, ensure all data flushed to the audio device.
			AudioDevice out = audio;
			if (out != null)
			{
//				System.out.println(audio.getPosition());
				out.flush();
//				System.out.println(audio.getPosition());
				synchronized (this)
				{
					complete = (!closed);
					close();
				}

				// report to listener
			}
		}
		
	}
	
	protected boolean decodeFrame() throws JavaLayerException {
		try {
			AudioDevice out = audio;
			if (out == null) return false;

			Header h = bitstream.readFrame();
			if (h == null) 
				return false;
			
			// sample buffer set when decoder constructed
			SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h, bitstream);

			synchronized (this) {
				out = audio;
				if(out != null) {
					out.write(output.getBuffer(), 0, output.getBufferLength());
				}
			}

			bitstream.closeFrame();
		}
		catch (RuntimeException ex) {
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		return true;
	}

	public synchronized void close()
	{
		AudioDevice out = audio;
		if (out != null)
		{
			closed = true;
			audio = null;
			// this may fail, so ensure object state is set up before
			// calling this method.
			out.close();
			lastPosition = out.getPosition();
			try
			{
				bitstream.close();
			}
			catch (BitstreamException ex)
			{}
		}
	}
	
	/* (Kein Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Automatisch erstellter Methoden-Stub

	}

}
