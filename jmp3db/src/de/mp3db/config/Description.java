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
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class Description
    extends MarshallableObject
    implements Element
{

    private String _Locale;
    private boolean isDefaulted_Locale = true;
    private final static String DEFAULT_LOCALE = String.valueOf("en");
    private String _Content;

    public boolean defaultedLocale() {
        return (_Locale!= null);
    }

    public String getLocale() {
        if (_Locale == null) {
            return DEFAULT_LOCALE;
        }
        return _Locale;
    }

    public void setLocale(String _Locale) {
        this._Locale = _Locale;
        if (_Locale == null) {
            invalidate();
        }
    }

    public String getContent() {
        return _Content;
    }

    public void setContent(String _Content) {
        this._Content = _Content;
        if (_Content == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("description");
        if (_Locale!= null) {
            w.attribute("locale", _Locale.toString());
        }
        if (_Content!= null) {
            w.chars(_Content.toString());
        }
        w.end("description");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("description");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("locale")) {
                if (_Locale!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Locale = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        {
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Content = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("content", x);
            }
        }
        xs.takeEnd("description");
    }

    public static Description unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static Description unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static Description unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((Description) d.unmarshal(xs, (Description.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Description)) {
            return false;
        }
        Description tob = ((Description) ob);
        if (_Locale!= null) {
            if (tob._Locale == null) {
                return false;
            }
            if (!_Locale.equals(tob._Locale)) {
                return false;
            }
        } else {
            if (tob._Locale!= null) {
                return false;
            }
        }
        if (_Content!= null) {
            if (tob._Content == null) {
                return false;
            }
            if (!_Content.equals(tob._Content)) {
                return false;
            }
        } else {
            if (tob._Content!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Locale!= null)?_Locale.hashCode(): 0));
        h = ((127 *h)+((_Content!= null)?_Content.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<description");
        sb.append(" locale=");
        sb.append(getLocale().toString());
        if (_Content!= null) {
            sb.append(" content=");
            sb.append(_Content.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Config.newDispatcher();
    }

}
