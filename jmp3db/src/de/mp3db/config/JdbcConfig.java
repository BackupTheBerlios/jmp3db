package de.mp3db.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingAttributeException;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class JdbcConfig
    extends MarshallableObject
    implements Element
{

    private String _Name;
    private boolean isDefaulted_Name = true;
    private final static String DEFAULT_NAME = String.valueOf("dbConnection");
    private String _Type;
    private String _Url;
    private String _Driver;
    private String _User;
    private String _Password;

    public boolean defaultedName() {
        return (_Name!= null);
    }

    public String getName() {
        if (_Name == null) {
            return DEFAULT_NAME;
        }
        return _Name;
    }

    public void setName(String _Name) {
        this._Name = _Name;
        if (_Name == null) {
            invalidate();
        }
    }

    public String getType() {
        return _Type;
    }

    public void setType(String _Type) {
        this._Type = _Type;
        if (_Type == null) {
            invalidate();
        }
    }

    public String getUrl() {
        return _Url;
    }

    public void setUrl(String _Url) {
        this._Url = _Url;
        if (_Url == null) {
            invalidate();
        }
    }

    public String getDriver() {
        return _Driver;
    }

    public void setDriver(String _Driver) {
        this._Driver = _Driver;
        if (_Driver == null) {
            invalidate();
        }
    }

    public String getUser() {
        return _User;
    }

    public void setUser(String _User) {
        this._User = _User;
        if (_User == null) {
            invalidate();
        }
    }

    public String getPassword() {
        return _Password;
    }

    public void setPassword(String _Password) {
        this._Password = _Password;
        if (_Password == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Type == null) {
            throw new MissingAttributeException("type");
        }
        if (_Url == null) {
            throw new MissingContentException("url");
        }
        if (_Driver == null) {
            throw new MissingContentException("driver");
        }
        if (_User == null) {
            throw new MissingContentException("user");
        }
        if (_Password == null) {
            throw new MissingContentException("password");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("jdbcConfig");
        if (_Name!= null) {
            w.attribute("name", _Name.toString());
        }
        w.attribute("type", _Type.toString());
        w.leaf("url", _Url.toString());
        w.leaf("driver", _Driver.toString());
        w.leaf("user", _User.toString());
        w.leaf("password", _Password.toString());
        w.end("jdbcConfig");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("jdbcConfig");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("name")) {
                if (_Name!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Name = xs.takeAttributeValue();
                continue;
            }
            if (an.equals("type")) {
                if (_Type!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Type = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("url")) {
            xs.takeStart("url");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Url = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("url", x);
            }
            xs.takeEnd("url");
        }
        if (xs.atStart("driver")) {
            xs.takeStart("driver");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Driver = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("driver", x);
            }
            xs.takeEnd("driver");
        }
        if (xs.atStart("user")) {
            xs.takeStart("user");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _User = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("user", x);
            }
            xs.takeEnd("user");
        }
        if (xs.atStart("password")) {
            xs.takeStart("password");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Password = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("password", x);
            }
            xs.takeEnd("password");
        }
        xs.takeEnd("jdbcConfig");
    }

    public static JdbcConfig unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static JdbcConfig unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static JdbcConfig unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((JdbcConfig) d.unmarshal(xs, (JdbcConfig.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof JdbcConfig)) {
            return false;
        }
        JdbcConfig tob = ((JdbcConfig) ob);
        if (_Name!= null) {
            if (tob._Name == null) {
                return false;
            }
            if (!_Name.equals(tob._Name)) {
                return false;
            }
        } else {
            if (tob._Name!= null) {
                return false;
            }
        }
        if (_Type!= null) {
            if (tob._Type == null) {
                return false;
            }
            if (!_Type.equals(tob._Type)) {
                return false;
            }
        } else {
            if (tob._Type!= null) {
                return false;
            }
        }
        if (_Url!= null) {
            if (tob._Url == null) {
                return false;
            }
            if (!_Url.equals(tob._Url)) {
                return false;
            }
        } else {
            if (tob._Url!= null) {
                return false;
            }
        }
        if (_Driver!= null) {
            if (tob._Driver == null) {
                return false;
            }
            if (!_Driver.equals(tob._Driver)) {
                return false;
            }
        } else {
            if (tob._Driver!= null) {
                return false;
            }
        }
        if (_User!= null) {
            if (tob._User == null) {
                return false;
            }
            if (!_User.equals(tob._User)) {
                return false;
            }
        } else {
            if (tob._User!= null) {
                return false;
            }
        }
        if (_Password!= null) {
            if (tob._Password == null) {
                return false;
            }
            if (!_Password.equals(tob._Password)) {
                return false;
            }
        } else {
            if (tob._Password!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Name!= null)?_Name.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        h = ((127 *h)+((_Url!= null)?_Url.hashCode(): 0));
        h = ((127 *h)+((_Driver!= null)?_Driver.hashCode(): 0));
        h = ((127 *h)+((_User!= null)?_User.hashCode(): 0));
        h = ((127 *h)+((_Password!= null)?_Password.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<jdbcConfig");
        sb.append(" name=");
        sb.append(getName().toString());
        if (_Type!= null) {
            sb.append(" type=");
            sb.append(_Type.toString());
        }
        if (_Url!= null) {
            sb.append(" url=");
            sb.append(_Url.toString());
        }
        if (_Driver!= null) {
            sb.append(" driver=");
            sb.append(_Driver.toString());
        }
        if (_User!= null) {
            sb.append(" user=");
            sb.append(_User.toString());
        }
        if (_Password!= null) {
            sb.append(" password=");
            sb.append(_Password.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Config.newDispatcher();
    }

}
