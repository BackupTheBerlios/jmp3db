package de.mp3db.config;

import java.io.PrintStream;
import java.io.PrintWriter;

/* use Debug.log.... for logging and co. */
// hier nicht 
// import de.materna.gp.wfms.util.debug.Debug;

/** Eine BasisKlasse für Exceptions, die es ermöglicht zugleich
 *  auch eine vorher aufgetretene Exception mit weiterzugeben.
 *
 * @author  jens rudolph
 * @version 0.1
 */
public class GenericException extends RuntimeException 
{
	
	/* do not change */
	// hier nicht
	// public static final String model = GenericException.class.toString ();
	
	/** die eingebettete Exception,
	 *  wenn nicht belegt, dann null
	 */
	private Throwable nested = null;

	/** Neue Instanz der Klasse GenericException */
    public GenericException () {
		super ("GenericException");
    }
	
	/** Neue Instanz der Klasse GenericException */
    public GenericException ( String msg ) {
		super (msg);
    }
	
	/** Übergibt eine vorher aufgetretene Exception
	 *
	 *  @parama msg Fehlermeldung
	 *  @param nestedException eigebetteter Fehler
	 */
	public GenericException ( String msg, Throwable nestedException )
	{
		super(msg);
		nested = nestedException;
	}
	
	public void printStackTrace ()
	{
		super.printStackTrace ();
		if ( nested != null )
		{
			System.err.println ("--------------------------------");
			System.err.println ("Fehler durch eingebetteten Fehler:");
			nested.printStackTrace ();
		}
	}
	public void printStackTrace (PrintStream ps)
	{
		super.printStackTrace (ps);
		if ( nested != null )
		{
			ps.println ("--------------------------------");
			ps.println ("Fehler durch eingebetteten Fehler:");
			nested.printStackTrace (ps);
		}
	}
	public void printStackTrace (PrintWriter pw)
	{
		super.printStackTrace (pw);
		if ( nested != null )
		{
			pw.println ("--------------------------------");
			pw.println ("Fehler durch eingebetteten Fehler:");
			nested.printStackTrace (pw);
		}
	}
	
	public String getMessage ()
	{
		if ( nested != null )
		{
			StringBuffer sb = new StringBuffer ();
			sb.append (super.getMessage ());
			sb.append ("\nInnerException: ");
			sb.append (nested.getMessage ());
			return sb.toString ();
		}
		else
			return super.getMessage ();
	}
	
	public String getLocalizedMessage ()
	{
		if ( nested != null )
		{
			StringBuffer sb = new StringBuffer ();
			sb.append (super.getLocalizedMessage ());
			sb.append ("\nInnerException: ");
			sb.append (nested.getLocalizedMessage ());
			return sb.toString ();
		}
		else
			return super.getLocalizedMessage ();
	}
	
	public String toString ()
	{
		if ( nested != null )
		{
			StringBuffer sb = new StringBuffer ();
			sb.append (super.toString ());
			sb.append ("\nInnerException: ");
			sb.append (nested.toString ());
			return sb.toString ();
		}
		else
			return super.toString ();
	}		
	
}
