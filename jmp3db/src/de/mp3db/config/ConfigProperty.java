package de.mp3db.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ConfigProperty
{
    private static      ResourceBundle resources;
    private static      String         sLanguage;
    private static		ConfigProperty instance;
    
    // Error-Meldungen
    private static       String sfzCannotInit;
    private static       String szfNotFound;
    private static       String szfCannotParse;
    private static       String szfCannotWrite;
    private static       String szfWrongType;
    private static       String szfErrorMsgTitle;
    private static       String szfJdbcNotFound;
    private static       String szfPortError;
    private static       String szfJdbcConfigError;
    private static       String szfNoDefault;
    
    // Buttons
    private static       String szfSaveButton;
    private static       String szfCancelButton;
    
    // Labels
    private static       String szfPropertyLabel;
    private static       String szfDbLabel;
    private static       String szfDBType;
    private static       String szfDBServer;
    private static       String szfDBPort;
    private static       String szfDBUser;
    private static       String szfDBPw;
    private static       String szfDBInstance;
    
    // JDBC Pattern
    private static       String szfPattern;
    private static       String szfDriver;
    private static       String szfJdbcDrivers;
    
    private static Config       config;
    private static List         dbConfigurations;
    private static List         properties;
    private static File         configFile;
    private static Hashtable    propertyTable;
    private static Hashtable    jdbcTable;
    
    // Temporäre Datei, die nach Programmende gelöscht wird
    private static File         backup;
    
    // unterstützte JDBC Treiber
    private static String[]     jdbcDrivers;
    
    public ConfigProperty(String configFileName) throws ConfigException
    {
        try
        {
            resources = ResourceBundle.getBundle("de.mp3db.config.Resources");
            sLanguage = Locale.getDefault().getLanguage();
            init();
            configFile = new File(configFileName);
            readConfigFile(configFile);
            
            /*
            // BackupDatei anlegen
            if(backup == null)
            {
                backup = File.createTempFile("_conf",".bak",new File(System.getProperty("user.dir")));
                backup.deleteOnExit();
            }
            createBackup();
             */
        }
        catch(Exception e)
        {
            throw new ConfigException(sfzCannotInit,e);
        }
    }
    
    /***************************************************************************
     * Konstruktor, der zusätzlich eine "Locale" mitübergibt, um die 
     * Sprache unabhängig vom Betriebssystem zu wählen
     **************************************************************************/
    public ConfigProperty(String configFileName, Locale locale) throws ConfigException
    {
        try
        {
            resources = ResourceBundle.getBundle("de.mp3db.config.Resources", locale);
            sLanguage = locale.getLanguage();
            init();
            readConfigFile(configFile);
            /*
            configFile = new File(configFileName);
            FileInputStream fiStream = new FileInputStream(configFile);
            config = config.unmarshal(fiStream);
            dbConfigurations = config.getJdbcConfig();
            properties = config.getProperty();
            
            // unterstützte Treiber einlesen
            jdbcDrivers = getDrivers(szfJdbcDrivers);
            
            // JDBC-Verbindungen in die Hashtable eintragen
            // key = name
            // value = JdbcConfig
            jdbcTable = new Hashtable();
            for(int i=0; i<dbConfigurations.size(); i++)
            {
                JdbcConfig config = (JdbcConfig)dbConfigurations.get(i);
                jdbcTable.put(config.getName(), config);
            }
            
            // Werte werden in Hashtable übertragen
            // Key = name
            // Value = value
            propertyTable = new Hashtable();
            for(int i=0; i<properties.size(); i++)
            {
                Property prop = (Property)properties.get(i);
                propertyTable.put(prop.getName(), prop.getValue());
            }
            */
            
            
        }
        catch(Exception e)
        {
            throw new ConfigException(sfzCannotInit,e);
        }
    }
    
    /***************************************************************************
     * Gibt eine DatenbankURL zurück
     * @param Name der DB-Connection
     * @throws ConfigException wenn keine DB-Connection mit diesem Namen definiert ist
     **************************************************************************/
    public String getDBUrl(String name) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        return config.getUrl();
    }
    
    /***************************************************************************
     * Setzt eine DB-URL
     * @param name Name der DB-Connection
     * @param url URL der DB-Connection
     * @throws ConfigException, wenn keine DBConnectionmit diesem Namen definiert ist
     **************************************************************************/
    public void setDBUrl(String name, String url) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        config.setUrl(url);
    }

        /***************************************************************************
     * Gibt eine Datenbank-Treiber zurück
     * @param Name der DB-Connection
     * @throws ConfigException wenn keine DB-Connection mit diesem Namen definiert ist
     **************************************************************************/
    public String getDBDriver(String name) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        return config.getDriver();
    }
    
    /***************************************************************************
     * Setzt einen DB-Treiber
     * @param name Name der DB-Connection
     * @param driver Treiber der DB-Connection
     * @throws ConfigException, wenn keine DBConnectionmit diesem Namen definiert ist
     **************************************************************************/
    public void setDBDriver(String name, String driver) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        config.setDriver(driver);
    }

    /***************************************************************************
     * Gibt einen Datenbank - User zurück
     * @param Name der DB-Connection
     * @throws ConfigException wenn keine DB-Connection mit diesem Namen definiert ist
     **************************************************************************/
    public String getDBUser(String name) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        return config.getUser();
    }
    
    /***************************************************************************
     * Setzt einen DB-User
     * @param name Name der DB-Connection
     * @param user  User der DB-Connection
     * @throws ConfigException, wenn keine DBConnectionmit diesem Namen definiert ist
     **************************************************************************/
    public void setDBUser(String name, String user) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        config.setUser(user);
    }
    
    /***************************************************************************
     * Gibt eine Datenbankuser-Password zurück
     * @param Name der DB-Connection
     * @throws ConfigException wenn keine DB-Connection mit diesem Namen definiert ist
     **************************************************************************/
    public String getDBPassword(String name) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        return config.getPassword();
    }
    
    /***************************************************************************
     * Setzt eine DB-User Password
     * @param name Name der DB-Connection
     * @param pw Password der DB-Connection
     * @throws ConfigException, wenn keine DBConnectionmit diesem Namen definiert ist
     **************************************************************************/
    public void setDBPassword(String name, String pw) throws ConfigException
    {
        JdbcConfig config = (JdbcConfig)jdbcTable.get(name);
        if(config == null)
        {
            String values[] = new String[2];
            values[0] = name;
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfJdbcNotFound, values));
        }
        config.setPassword(pw);
    }

    /***************************************************************************
     * Ermittelt einen Default-Wert (als String) für eine Property, sofern dieser im
     * Property-File als "Name_Default_Value" definiert ist
     * @param key Name der Property
     * @returns Default-Wert als String
     * @throws ConfigException wenn keine Property dieses Namens existiert oder kein Default-Wert gefunden werden kann
     **************************************************************************/
    public String getDefaultValue(String key) throws ConfigException
    {
        try
        {
            return resources.getString(key+"_Default_Value");
        }
        catch(Exception e)
        {
            String[] values = new String[1];
            values[0] = key;
            throw new ConfigException(prepareMsg(szfNoDefault, values), e);
        }
    }
    
    /***************************************************************************
     * Setzt für eine Property den Default Wert, sofern dieser im Propertyfile
     * definiert ist (als "Name_Default_Value" sowie "Name_Default_Type")
     * @param key Name der Property
     * @throws ConfigException falls Property oder DefaultValue/Type nicht existiert
     **************************************************************************/
    public void setToDefault(String key)
    {
        try
        {
            String value = resources.getString(key+"_Default_Value");
            String type = resources.getString(key+"_Default_Type");
            Property property = getProperty(key);
            property.setValue(value);
            property.setType(type);
            
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(Exception e)
        {
            String values[] = new String[1];
            values[0] = key;
            throw new ConfigException(prepareMsg(szfNoDefault,values),e);
        }
    }
    
    /***************************************************************************
     * Gibt einen Property-Wert als String zurück
     * @param key key Name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert
     * @return Wert der Property als String
     **************************************************************************/
    public String getString(String key) throws ConfigException
    {
        String result = (String)propertyTable.get(key);
        if(result == null)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        return result;
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ String in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert
     **************************************************************************/
    public void setString(String key, String value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            property.setValue(value);
        
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }
        
    /***************************************************************************
     * Gibt einen Property-Wert als byte zurück
     * @return Wert der Property als byte
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in byte konvertiert werden kann
     **************************************************************************/    
    public byte getByte(String key)  throws ConfigException
    {
        try
        {
            return Byte.parseByte(propertyTable.get(key).toString());
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "byte";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ byte in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder der Typ nicht kompatibel ist
     **************************************************************************/
    public void setByte(String key, byte value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if(!property.getType().equals("byte") &&
                !property.getType().equals("int") &&
                !property.getType().equals("short") &&
                !property.getType().equals("long") &&
                !property.getType().equals("float") &&
                !property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "byte";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }

    
    /*************************************************************************** 
     * Gibt einen Property-Wert als int zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in int konvertiert werden kann
     * @return Wert der Property als int
     **************************************************************************/    
    public int getInt(String key)  throws ConfigException
    {
        try
        {
            return Integer.parseInt(propertyTable.get(key).toString());
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "int";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ int in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder es der Typ nicht kompatibel ist
     **************************************************************************/
    public void setInt(String key, int value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if( !property.getType().equals("int") &&
                !property.getType().equals("long") &&
                !property.getType().equals("float") &&
                !property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "int";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }
    
    /***************************************************************************
     * Gibt einen Property-Wert als short zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in short konvertiert werden kann
     * @return Wert der Property als short
     **************************************************************************/    
    public short getShort(String key)  throws ConfigException
    {
        try
        {
            return Short.parseShort(propertyTable.get(key).toString());
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "short";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ short in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder der Typ inkompatibel ist
     **************************************************************************/
    public void setShort(String key, short value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if( !property.getType().equals("int") &&
                !property.getType().equals("short") &&
                !property.getType().equals("long") &&
                !property.getType().equals("float") &&
                !property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "short";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }
    
    /***************************************************************************
     * Gibt einen Property-Wert als long zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in long konvertiert werden kann
     * @return Wert der Property als long
     **************************************************************************/    
    public long getLong(String key)  throws ConfigException
    {
        try
        {
            return Long.parseLong(propertyTable.get(key).toString());
        }
               catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "long";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
 
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ long in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder der Typ inkompatibel ist
     **************************************************************************/
    public void setLong(String key, long value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if(!property.getType().equals("long") &&
                !property.getType().equals("float") &&
                !property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "long";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }
    
    /***************************************************************************
     * Gibt einen Property-Wert als float zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in float konvertiert werden kann
     * @return Wert der Property als float
     **************************************************************************/    
    public float getFloat(String key)  throws ConfigException
    {
        try
        {
            return Float.parseFloat(propertyTable.get(key).toString());
        }
               catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "float";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
 
    }

        /***************************************************************************
     * Schreibt einen Property-Wert vom Typ float in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder es sich nicht um eine Property vom Typ byte handelt
     **************************************************************************/
    public void setFloat(String key, float value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if(!property.getType().equals("float") &&
                !property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "float";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }

    /***************************************************************************
     * Gibt einen Property-Wert als double zuück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder der Wert nicht in double konvertiert werden kann
     * @return Wert der Property als double
     **************************************************************************/    
    public double getDouble(String key)  throws ConfigException
    {
        try
        {
            return Double.parseDouble(propertyTable.get(key).toString());
        }
               catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        catch(Exception e)
        {
            String values[] = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "double";
            throw new ConfigException(prepareMsg(szfCannotParse,values), e);
        }
 
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ double in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder wenn der Typ inkompatibel ist
     **************************************************************************/
    public void setDouble(String key, double value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if(!property.getType().equals("double") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "double";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }
    
    
    /***************************************************************************
     * Gibt einen Property-Wert als boolean zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder wenn der Wert nicht in boolean konvertiert werden kann
     * @return Wert der Property als boolean
     **************************************************************************/    
    public boolean getBoolean(String key)  throws ConfigException
    {
        String value = null;
        try
        {
            value = propertyTable.get(key).toString().toLowerCase();
        }
        catch(NullPointerException e)
        {
            // wird im 1. if-Block abgefangen
        }
        if(value == null)
        {
            String[] values = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound,values));
        }
        else if(value.equals("true"))
            return true;
        else if(value.equals("false"))
            return false;
        else
        {
            String[] values = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "boolean";
            throw new ConfigException(prepareMsg(szfCannotParse, values)+": "+value);
        }
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ boolean in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder der Typ inkompatibel ist
     **************************************************************************/
    public void setBoolean(String key, boolean value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if( !property.getType().equals("boolean") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "boolean";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }

    
    /***************************************************************************
     * Gibt einen Property-Wert als char zurück
     * @param key name der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert oder wenn der Wert nicht in char konvertiert werden kann
     * @return Wert der Property als char 
     **************************************************************************/    
    public char getChar(String key)  throws ConfigException
    {
        String value = null;
        try
        {
            value = propertyTable.get(key).toString();
        }
        catch(NullPointerException e)
        {
            // wird im 1. if-Block abgefangen
        }
        if(value == null)
        {
            String[] values = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        else if(value.length() != 1)
        {
            String[] values = new String[3];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            values[2] = "char";
            throw new ConfigException(prepareMsg(szfCannotParse,values)+": "+value);
        }
        else
            return value.toCharArray()[0];
    }
    
    /***************************************************************************
     * Schreibt einen Property-Wert vom Typ char in das Config-File
     * @key Name der Property
     * @param value Wert der Property
     * @throws ConfigException falls keine Property mit dem angegebenen Namen existiert 
     *  oder der Typ inkompatibel ist
     **************************************************************************/
    public void setChar(String key, char value) throws ConfigException
    {
        try
        {
            Property property = getProperty(key);
            
            // Typ der Property abfragen und Exception aufwerfen wenn inkompatibel
            if(!property.getType().equals("char") &&
                !property.getType().equals("String"))
            {
                String values[] = new String[2];
                values[0] = property.getType();
                values[1] = "char";
                throw new ConfigException(prepareMsg(szfWrongType, values));
            }
            property.setValue(""+value);
            config.validate();
            config.marshal(new FileOutputStream(configFile));
        }
        catch(NullPointerException e)
        {
            String values[] = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }

        catch(Exception e)
        {
            String values[] = new String[2];
            values[0] = "'"+value+"'";
            values[1] = "'"+key+"'";
            throw new ConfigException(prepareMsg(szfCannotWrite, values),e);
        }
    }

    /***************************************************************************
     * Ermittelt die Description einer Property
     * Wenn vorhanden wird die Description mit der aktuell eingestellten 'Locale'
     * zurückgegeben, andernfalls die Default-Description (en)
     * @param key Property-Name
     * @returns Description der aktuellen Locale, Description der Default-Locale
     *          oder null wenn keine Description vorhanden
     * @throws ConfigException wenn keine Property mit dem übergebenen Key existiert
     **************************************************************************/
    public String getDescription(String key) throws ConfigException
    {
        String localeDescription = null;
        String defaultDescription = null;
        Property property = null;
        
        // Property ermitteln
        for(int i=0; i<properties.size(); i++)
        {
            property = (Property)properties.get(i);
            if(property.getName().equals(key))
            {
                break;
            }
        }
        if(property == null)
        {
            String[] values = new String[2];
            values[0] = "'"+key+"'";
            values[1] = configFile.toString();
            throw new ConfigException(prepareMsg(szfNotFound, values));
        }
        
        // Description ermitteln
        List descriptions = property.getDescription();
        for(int j=0; j<descriptions.size(); j++)
        {
            Description description = (Description)descriptions.get(j);
            if(description.getLocale().equals(sLanguage))
            {
                localeDescription = description.getContent();
                break;
            }
            else if(description.defaultedLocale())
            {
                defaultDescription = description.getContent();
            }
        }
        System.out.println("Locale: "+localeDescription+" Default: "+defaultDescription);
        if(localeDescription != null)
            return localeDescription;
        else
            return defaultDescription;
    }
    /***************************************************************************
     * Erstellt ein JPanel, das die im Konfig-File vorhandenen Parameter darstellt
     * und deren Manipulation erlaubt
     * @returns JPanel mit den vorhandenen Properties
     **************************************************************************/
    public JPanel getConfigPanel(java.awt.Window parentContainer)
    { 
        final java.awt.Window parent = (java.awt.Window)parentContainer;
        final JPanel panel = new JPanel();
        final JPanel propertyPanel = new JPanel();
        
        // hier werden die JDBC-Konfigurationen gehalten
        final Hashtable configs = new Hashtable();        
        
        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(gbLayout);
        
        // Panel für Properties
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(20);
        layout.setVgap(10);
        propertyPanel.setLayout(layout);
        propertyPanel.setBorder(new TitledBorder(szfPropertyLabel));
        
        
        // +++ Kontrollelemente für JDBC-Verbindungen +++
        for(int i=0; i<dbConfigurations.size(); i++)
        {
            JdbcConfig config = (JdbcConfig)dbConfigurations.get(i);
            String[] jdbcValues = getJdbcValues(config);
            
            JPanel jdbcPanel = new JPanel();
            GridLayout jdbcLayout = new GridLayout(0,2);
            jdbcLayout.setHgap(20);
            jdbcLayout.setVgap(10);
            jdbcPanel.setLayout(jdbcLayout);
            jdbcPanel.setBorder(new TitledBorder(szfDbLabel+": "+config.getName()));
            
            JLabel typeLabel = new JLabel(szfDBType);
            JComboBox typeBox = new JComboBox(jdbcDrivers);
            typeBox.setSelectedItem(jdbcValues[0]);
            
            final JLabel serverLabel = new JLabel(szfDBServer);
            final JTextField serverField = new JTextField(jdbcValues[1],20);
            serverField.setName("dbServer");
            final JLabel portLabel = new JLabel(szfDBPort);
            final JTextField portField = new JTextField(jdbcValues[2],20);
            portField.setName("dbPort");
            final JLabel instanceLabel = new JLabel(szfDBInstance);
            final JTextField instanceField = new JTextField(jdbcValues[3],20);
            instanceField.setName("dbInstance");
            final JLabel userLabel = new JLabel(szfDBUser);
            final JTextField userField =  new JTextField(jdbcValues[4], 20);
            userField.setName("dbUser");
            final JLabel pwLabel = new JLabel(szfDBPw);
            final JTextField pwField = new JTextField(jdbcValues[5], 20);
            pwField.setName("dbPassword");
            
            jdbcPanel.add(typeLabel);
            jdbcPanel.add(typeBox);
            jdbcPanel.add(serverLabel);
            jdbcPanel.add(serverField);
            jdbcPanel.add(portLabel);
            jdbcPanel.add(portField);
            jdbcPanel.add(instanceLabel);
            jdbcPanel.add(instanceField);
            jdbcPanel.add(userLabel);
            jdbcPanel.add(userField);
            jdbcPanel.add(pwLabel);
            jdbcPanel.add(pwField);
            
            // *** Bei ODBC nur DBName aktivieren ***
            if(config.getType().equals("ODBC"))
            {
                serverLabel.setEnabled(false);
                serverField.setEnabled(false);
                portLabel.setEnabled(false);
                portField.setEnabled(false);
            }
            // *** ChangeListener um "Server" und "Port" Felder
            //      zu aktiverien bzw. zu deaktivieren ***
            typeBox.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e)
                {
                    JComboBox box = (JComboBox)e.getSource();
                    if(box.getSelectedItem().equals("ODBC"))
                    {
                        serverLabel.setEnabled(false);
                        serverField.setEnabled(false);
                        portLabel.setEnabled(false);
                        portField.setEnabled(false);
                    }
                    else
                    {
                        serverLabel.setEnabled(true);
                        serverField.setEnabled(true);
                        portLabel.setEnabled(true);
                        portField.setEnabled(true);
                    }
                }
            });
            
            c.gridwidth = GridBagConstraints.REMAINDER;
            gbLayout.setConstraints(jdbcPanel, c);
            panel.add(jdbcPanel);
            configs.put(config.getName(), jdbcPanel);
        }
        
            
        
        // *** für jede Property ein Textfeld hinzufügen ***
        for(int i=0; i<properties.size(); i++)
        {
            Property prop = (Property)properties.get(i);
            String labelText = getDescription(prop.getName());
            if(labelText == null)
                labelText = prop.getName();
            JLabel label = new JLabel(labelText);
            label.setName(prop.getName());
            
            JComponent field = null;
            
            // Bei boolean JComboBox, ansonsten JTextField
            if(!prop.getType().equals("boolean"))
                field = new JTextField(prop.getValue(), 20);
            else
            {
                Vector v = new Vector();
                v.add("true");
                v.add("false");
                field = new JComboBox(v);
                if(prop.getValue().equals("false"))
                    ((JComboBox)(field)).setSelectedItem("false");
            }
            
            propertyPanel.add(label);
            propertyPanel.add(field);
        }
        JButton save = new JButton(szfSaveButton);
        JButton cancel = new JButton(szfCancelButton);
        
        gbLayout.setConstraints(propertyPanel, c);
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        gbLayout.setConstraints(save, c);
        gbLayout.setConstraints(cancel, c);
        
        panel.add(propertyPanel);
        panel.add(save);
        panel.add(cancel);
        
        
        // +++ Funktionen Save und Cancel +++
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(save(propertyPanel) && saveJdbc(configs))
                    parent.dispose();
            }
        });
        
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                parent.dispose();
            }
        });
        
        return panel;
    }
    
    /***************************************************************************
     * Erzeugt ein Tempfile mit den momentan aktuellen Einstellugnen
     * @throws IOExceptio wenn das File nicht angelegt werden kann
     **************************************************************************/
    public void createBackup() throws Exception
    {
        if(backup == null)
            backup = File.createTempFile("_conf",".bak",new File(System.getProperty("user.dir")));
        
        config.validate();
        config.marshal(new FileOutputStream(backup));
    }
    
    /***************************************************************************
     * Löscht die temporäre Backupdatei (falls vorhanden)
     **************************************************************************/
    public void deleteBackup()
    {
        if(backup != null)
            backup.delete();
    }
    
    /***************************************************************************
     * Macht die zuletzt im ConfigPanel gespeicherten Änderungen rückgängig 
     * @throws ConfigException, wenn noch keine Änderungen vorgenommen wurden
     **************************************************************************/
    public void undoChanges() throws Exception
    {
        if(backup != null)
            readConfigFile(backup);
        config.validate();
        config.marshal(new FileOutputStream(configFile));
    }
    
    /***************************************************************************
     * Fügt einer Fehlermeldung zusätzliche Infos hinzu, indem '?' durch die Werte
     * des übergebenen String-Arrays ersetzt werden (Ähnlich wie bei PreparedStatements)
     * @param msg in der Form: "Unable to ... ? ... ?"
     * @param values String-Array, das die einzufügenden Strings enthält
     * @returns parsedMsg Abgeänderte Message in der Form "Unable to ... values[0] ... values[1]"
     **************************************************************************/
    private String prepareMsg(String msg, String[] values)
    {
        for(int i=0; i<values.length; i++)
        {
            int index = msg.indexOf('?');
            if(index >= 0)
                msg = msg.substring(0,index) + values[i] + msg.substring(index +1);
        }
        return msg;
    }
    
    /***************************************************************************
     * Fügt ServerName, Portmunner und Datenbank-Instanz in den als Pattern für
     * den jeweiligen Datenbanktyp definierten String ein
     * @param url als Pattern definierte URL
     * @param values StringArray  ([serverNaem, port, instance])
     **************************************************************************/
    private String prepareUrl(String url, String[] values)
    {
        for(int i=0; i<values.length; i++)
        {
            int index = url.indexOf('$');
            if(index >= 0)
                url = url.substring(0,index) + values[i] + url.substring(index +1);
        }
        return url;
    }
    
    /***************************************************************************
     * Speichert die aktuellen Einstellungen
     * @returns true wenn alle Werte kompatibel mit dem Propertytyp sind
     *  ansonsten false
     **************************************************************************/
    private boolean save(JPanel panel)
    {
        String key = null;
        String value = null;
        for(int i=0; i<panel.getComponentCount(); i++)
        try
        {
            if(panel.getComponent(i) instanceof JLabel)
            {
                JLabel label = (JLabel)panel.getComponent(i);
                
                if(panel.getComponent(i+1) instanceof JTextField)
                {
                    JTextField field = (JTextField)panel.getComponent(i+1);
                    key = label.getName();
                    value = field.getText();
                }
                else if(panel.getComponent(i+1) instanceof JComboBox)
                {
                    JComboBox field = (JComboBox)panel.getComponent(i+1);
                    key = label.getText();
                    value = (String)field.getSelectedItem();
                }
            
            // Abhängig vom Property Type versuchen die Property abzuspeichern
            if(getProperty(key).getType().equals("byte"))
                setByte(key,new Byte(value).byteValue());
            else if(getProperty(key).getType().equals("short"))
                setShort(key, new Short(value).shortValue());
            else if(getProperty(key).getType().equals("int"))
                setInt(key, new Integer(value).intValue());
            else if(getProperty(key).getType().equals("long"))
                setLong(key, new Long(value).longValue());
            else if(getProperty(key).getType().equals("float"))
                setFloat(key, new Float(value).floatValue());
            else if(getProperty(key).getType().equals("double"))
                setDouble(key, new Double(value).doubleValue());
            else if(getProperty(key).getType().equals("char"))
                setChar(key, new Character(value.charAt(0)).charValue());
            else
                setString(key, value);
            }
        }
        catch(NumberFormatException e)
        {
            String values[] = new String[2];
            values[0] = value;
            values[1] = key;
            String msg = prepareMsg(szfCannotWrite, values);
            values[0] = getProperty(key).getType();
            values[1] = value;
            msg += "\n"+prepareMsg(szfWrongType, values);
            JOptionPane.showMessageDialog(panel, msg, szfErrorMsgTitle, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch(Exception e)
        {
            System.out.println(e.getClass().getName()+": "+e.getMessage());
            e.printStackTrace();
        }
        return true;
    }
    
    /***************************************************************************
     * Speichert die vorhandenen JDBC-Konfigurationen im Config-File ab
     * @returns true wenn alle Werte kompatibel mit dem Propertytyp sind
     *  ansonsten false
     **************************************************************************/
    public boolean saveJdbc(Hashtable configs)
    {
        Enumeration keys = configs.keys();
        while(keys.hasMoreElements())
        {
            String configName = (String)keys.nextElement();
            JPanel panel = (JPanel)configs.get(configName);
            JdbcConfig config = (JdbcConfig)jdbcTable.get(configName);
            
            
            // Array für die JDBC-Parameter
            String[] jdbcValues = new String[6];
                
                
            // JDBC Typ
            String type = null;
            String user = null;
            String pw = null;
                
            
            for(int i=0; i<panel.getComponentCount(); i++)
            {
                if(panel.getComponent(i) instanceof JTextField)
                {
                    JTextField field = (JTextField)panel.getComponent(i);
                    
                    if(field.getName().equals("dbServer"))
                        jdbcValues[0] = field.getText();
                    else if(field.getName().equals("dbPort"))
                    {
                        // Prüfen ob als Port ein Integerwert eingetragen ist
                        // Bei ODBC nicht nötig
                        try
                        {
                            if(field.isEnabled())
                                Integer.parseInt(field.getText());
                        }
                        catch(NumberFormatException e)
                        {
                            JOptionPane.showMessageDialog(panel,szfPortError,szfErrorMsgTitle,JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        jdbcValues[1] = field.getText();
                    }
                    else if(field.getName().equals("dbInstance"))
                        jdbcValues[2] = field.getText();
                    else if(field.getName().equals("dbUser"))
                        user = field.getText();
                    else if(field.getName().equals("dbPassword"))
                        pw = field.getText();
                }
                else if(panel.getComponent(i) instanceof JComboBox)
                {
                    JComboBox box = (JComboBox)panel.getComponent(i);
                    type = (String)box.getSelectedItem();
                }
            }
            String url = null;
            if(type.equals("ODBC"))
                url = "jdbc:odbc:"+jdbcValues[2];
            else
                url = prepareUrl(resources.getString(type+szfPattern),jdbcValues);
            String driver = resources.getString(type+szfDriver);
            config.setType(type);
            config.setDriver(driver);
            config.setUrl(url);
            config.setUser(user);
            config.setPassword(pw);
            try
            {
				ConfigProperty.config.validate();
				ConfigProperty.config.marshal(new FileOutputStream(configFile));
            }
            catch(Exception e)
            {
                return false;
            }
        }
        return true;
    }
    
    /***************************************************************************
     * Ermittelt aus den in der java.util.List properties vorkommenden Objekte
     * Die Property mit dem angegebenen Namen
     * @param name Name der Property
     * @returns Property mit dem angegebenen Namen
     **************************************************************************/
    private Property getProperty(String name)
    {
        Iterator propIterator = properties.iterator();
        while(propIterator.hasNext())
        {
            Property p = (Property)propIterator.next();
            if(p.getName().equals(name))
                return p;
        }
        return null;
    }
    
    /***************************************************************************
     * Ermittelt aus einem Objekt der Klasse JdbcConfig die für die GUI benötigten 
     * Werte als String-Array, in folgender Reihenfolge:
     * 1. Typ
     * 2. Server
     * 3. Port
     * 4. Instance
     * 5. User
     * 6. Password
     **************************************************************************/
    private String[] getJdbcValues(JdbcConfig config)
    {
        String result[] = new String[6];
        String buffer = null;
        int index = -1;
        
        
        result[0] = config.getType();
        String[] delimeters = getDelimeters(resources.getString(result[0]+"_Pattern"));
        
        // *** URL wird anhand der im Property-File definierten Pattern
        //      ausgewertet  ***
        if(result[0].equals("ODBC"))
        {
            buffer = config.getUrl();
            if(buffer.startsWith("jdbc:odbc:"))
                result[3] = buffer.substring(10);
        }
        else
        {
            if(config.getUrl().startsWith(delimeters[0]))
            {
                buffer  = config.getUrl();
                buffer  = buffer.substring(delimeters[0].length());
                index   = buffer.indexOf(delimeters[1]);
                
                if(index < 0)
                {
                    index = buffer.indexOf(delimeters[2]);
                    delimeters[1] = delimeters[2];
                }   
                if(index > 0)
                {
                    // Server einlesen
                    result[1]   = buffer.substring(0,index);
                    if(buffer.length() > index)
                        buffer      = buffer.substring(index+ + delimeters[1].length());
                }
                index = buffer.indexOf(delimeters[2]);
                if(index > 0)
                {
                    // Port einlesen
                    result[2]   = buffer.substring(0,index);
                    if(buffer.length() > index)
                        buffer = buffer.substring(index+ + delimeters[2].length());
                }
                // Wenn kein Port angegeben ist Standardport verwenden
                else
                {
                    result[2] = resources.getString(result[0]+"_defaultPort");
                }
                result[3] = buffer;
            }
            else
            {
                throw new ConfigException(szfJdbcConfigError+config.getName());
            }
        }
        result[4] = config.getUser();
        result[5] = config.getPassword();
        return result;
    }
    
    /***************************************************************************
     * Gibt die im Property-File definierten Treiber als String-Array zurück
     **************************************************************************/
    private String[] getDrivers(String driverString)
    {
        Vector driver = new Vector();
        
        int index = driverString.indexOf(';');
        while(index > 0)
        {
            driver.add(driverString.substring(0,index));
            if(driverString.length() > index)
            {
                driverString = driverString.substring(index + 1);
                index = driverString.indexOf(';');
            }
            else
                index = -1;
        }
        if(driverString.length() > 0)
            driver.add(driverString);
        
        String[] results = new String[driver.size()];
        
        for(int i=0; i<driver.size(); i++)
            results[i] = (String)driver.get(i);
        return results;
    }
    
    /***************************************************************************
     * Zerlegt die implements Property-File definierten URL-Pattern in die einzelnen
     * Bestandteile, um die notwendigen Infos (Server, Port und Instanz) aus der
     * DB-Url zu extrahieren
     * Pattern sind im Format: string[SERVER]delimeter[PORT]delimeter[INSTANZ]
     **************************************************************************/
    private String[] getDelimeters(String urlPattern)
    {
        String[] delimeters = new String[3];
        int index = urlPattern.indexOf('$');
        int i = 0;
        
        while(index > 0)
        {
            delimeters[i++] = urlPattern.substring(0,index);
            if(urlPattern.length() > index)
                urlPattern = urlPattern.substring(index+1);
            else
                urlPattern = "";
            index = urlPattern.indexOf('$');
        }
        return delimeters;
    }
    
    /***************************************************************************
     * liest die benötigten Parameter aus dem Property-File aus
     **************************************************************************/
    private void init()
    {
            // Error-Meldungen
            sfzCannotInit       = resources.getString("propertyCannotInit");    
            szfNotFound         = resources.getString("propertyNotFound");
            szfCannotParse      = resources.getString("propertyCannotParse");
            szfCannotWrite      = resources.getString("propertyCannotWrite");
            szfWrongType        = resources.getString("propertyWrongType");
            szfErrorMsgTitle    = resources.getString("errorMsgTitle");
            szfJdbcNotFound     = resources.getString("jdbcNotFound");
            szfPortError        = resources.getString("portError");
            szfJdbcConfigError  = resources.getString("jdbcConfigError");
            szfNoDefault        = resources.getString("propertyNoDefault");
    
            // Buttons
            szfSaveButton   = resources.getString("saveButton");
            szfCancelButton = resources.getString("cancelButton");
    
            // Labels
            szfPropertyLabel= resources.getString("propertyLabel");
            szfDbLabel      = resources.getString("dbLabel");
            szfDBType       = resources.getString("dbType");
            szfDBServer     = resources.getString("dbServer");
            szfDBPort       = resources.getString("dbPort");
            szfDBUser       = resources.getString("dbUser");
            szfDBPw         = resources.getString("dbPassword");
            szfDBInstance   = resources.getString("dbInstance");
    
            // JDBC Pattern
            szfPattern      = resources.getString("urlPattern");
            szfDriver       = resources.getString("driverPattern");
            szfJdbcDrivers  = resources.getString("JDBC_Driver");
            
    }
    
    /***************************************************************************
     * Aktualiesiert die Einstellungen aus dem übergebenen File
     **************************************************************************/
    private void readConfigFile(File configFile) throws Exception
    {
        System.out.println("ConfFile: "+configFile.toString());
        FileInputStream fiStream = new FileInputStream(configFile);
        config = Config.unmarshal(fiStream);
        dbConfigurations = config.getJdbcConfig();
        properties = config.getProperty();

        // unterstützte Treiber einlesen
        jdbcDrivers = getDrivers(szfJdbcDrivers);

        // JDBC-Verbindungen in die Hashtable eintragen
        // key = name
        // value = JdbcConfig
        jdbcTable = new Hashtable();
        for(int i=0; i<dbConfigurations.size(); i++)
        {
            JdbcConfig config = (JdbcConfig)dbConfigurations.get(i);
            jdbcTable.put(config.getName(), config);
        }

        // Werte werden in Hashtable übertragen
        // Key = name
        // Value = value
        propertyTable = new Hashtable();
        for(int i=0; i<properties.size(); i++)
        {
            Property prop = (Property)properties.get(i);
            propertyTable.put(prop.getName(), prop.getValue());
        }
    }
}