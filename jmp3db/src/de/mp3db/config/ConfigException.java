/*
 * ConfigException.java
 *
 * Created on 8. März 2002, 17:02
 */

package de.mp3db.config;

/**
 *
 * @author  gaiselmann
 * @version 
 */
public class ConfigException extends GenericException {

    /**
     * Creates new <code>ConfigException</code> without detail message.
     */
    public ConfigException() {
    }
    
    public ConfigException(String msg, Throwable nestedException)
    {
        super(msg, nestedException);
    }


    /**
     * Constructs an <code>ConfigException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ConfigException(String msg) 
    {
        super(msg);
    }
}


