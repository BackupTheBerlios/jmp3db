package de.mp3db.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.ConversionException;
import javax.xml.bind.Dispatcher;
import javax.xml.bind.DuplicateAttributeException;
import javax.xml.bind.Element;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.Marshaller;
import javax.xml.bind.MissingContentException;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class Property
    extends MarshallableObject
    implements Element
{

    private String _Name;
    private List _Description = PredicatedLists.createInvalidating(this, new DescriptionPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Description = new DescriptionPredicate();
    private String _Value;
    private String _Type;
    private boolean isDefaulted_Type = true;
    private final static String DEFAULT_TYPE = String.valueOf("String");

    public String getName() {
        return _Name;
    }

    public void setName(String _Name) {
        this._Name = _Name;
        if (_Name == null) {
            invalidate();
        }
    }

    public List getDescription() {
        return _Description;
    }

    public void deleteDescription() {
        _Description = null;
        invalidate();
    }

    public void emptyDescription() {
        _Description = PredicatedLists.createInvalidating(this, pred_Description, new ArrayList());
    }

    public String getValue() {
        return _Value;
    }

    public void setValue(String _Value) {
        this._Value = _Value;
        if (_Value == null) {
            invalidate();
        }
    }

    public boolean defaultedType() {
        return (_Type!= null);
    }

    public String getType() {
        if (_Type == null) {
            return DEFAULT_TYPE;
        }
        return _Type;
    }

    public void setType(String _Type) {
        this._Type = _Type;
        if (_Type == null) {
            invalidate();
        }
    }

    public void validateThis()
        throws LocalValidationException
    {
        if (_Name == null) {
            throw new MissingContentException("name");
        }
        if (_Value == null) {
            throw new MissingContentException("value");
        }
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _Description.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("property");
        if (_Type!= null) {
            w.attribute("type", _Type.toString());
        }
        w.leaf("name", _Name.toString());
        if (_Description.size()> 0) {
            for (Iterator i = _Description.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.leaf("value", _Value.toString());
        w.end("property");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("property");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            if (an.equals("type")) {
                if (_Type!= null) {
                    throw new DuplicateAttributeException(an);
                }
                _Type = xs.takeAttributeValue();
                continue;
            }
            throw new InvalidAttributeException(an);
        }
        if (xs.atStart("name")) {
            xs.takeStart("name");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Name = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("name", x);
            }
            xs.takeEnd("name");
        }
        {
            List l = PredicatedLists.create(this, pred_Description, new ArrayList());
            while (xs.atStart("description")) {
                l.add(((Description) u.unmarshal()));
            }
            _Description = PredicatedLists.createInvalidating(this, pred_Description, l);
        }
        if (xs.atStart("value")) {
            xs.takeStart("value");
            String s;
            if (xs.atChars(XMLScanner.WS_COLLAPSE)) {
                s = xs.takeChars(XMLScanner.WS_COLLAPSE);
            } else {
                s = "";
            }
            try {
                _Value = String.valueOf(s);
            } catch (Exception x) {
                throw new ConversionException("value", x);
            }
            xs.takeEnd("value");
        }
        xs.takeEnd("property");
    }

    public static Property unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static Property unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static Property unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((Property) d.unmarshal(xs, (Property.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Property)) {
            return false;
        }
        Property tob = ((Property) ob);
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
        if (_Description!= null) {
            if (tob._Description == null) {
                return false;
            }
            if (!_Description.equals(tob._Description)) {
                return false;
            }
        } else {
            if (tob._Description!= null) {
                return false;
            }
        }
        if (_Value!= null) {
            if (tob._Value == null) {
                return false;
            }
            if (!_Value.equals(tob._Value)) {
                return false;
            }
        } else {
            if (tob._Value!= null) {
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
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_Name!= null)?_Name.hashCode(): 0));
        h = ((127 *h)+((_Description!= null)?_Description.hashCode(): 0));
        h = ((127 *h)+((_Value!= null)?_Value.hashCode(): 0));
        h = ((127 *h)+((_Type!= null)?_Type.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<property");
        if (_Name!= null) {
            sb.append(" name=");
            sb.append(_Name.toString());
        }
        if (_Description!= null) {
            sb.append(" description=");
            sb.append(_Description.toString());
        }
        if (_Value!= null) {
            sb.append(" value=");
            sb.append(_Value.toString());
        }
        sb.append(" type=");
        sb.append(getType().toString());
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        return Config.newDispatcher();
    }


    private static class DescriptionPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof Description)) {
                throw new InvalidContentObjectException(ob, (Description.class));
            }
        }

    }

}
