package de.mp3db.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.Dispatcher;
import javax.xml.bind.InvalidAttributeException;
import javax.xml.bind.InvalidContentObjectException;
import javax.xml.bind.LocalValidationException;
import javax.xml.bind.MarshallableObject;
import javax.xml.bind.MarshallableRootElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PredicatedLists;
import javax.xml.bind.RootElement;
import javax.xml.bind.StructureValidationException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidatableObject;
import javax.xml.bind.Validator;
import javax.xml.marshal.XMLScanner;
import javax.xml.marshal.XMLWriter;


public class Config
    extends MarshallableRootElement
    implements RootElement
{

    private List _JdbcConfig = PredicatedLists.createInvalidating(this, new JdbcConfigPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_JdbcConfig = new JdbcConfigPredicate();
    private List _Property = PredicatedLists.createInvalidating(this, new PropertyPredicate(), new ArrayList());
    private PredicatedLists.Predicate pred_Property = new PropertyPredicate();

    public List getJdbcConfig() {
        return _JdbcConfig;
    }

    public void deleteJdbcConfig() {
        _JdbcConfig = null;
        invalidate();
    }

    public void emptyJdbcConfig() {
        _JdbcConfig = PredicatedLists.createInvalidating(this, pred_JdbcConfig, new ArrayList());
    }

    public List getProperty() {
        return _Property;
    }

    public void deleteProperty() {
        _Property = null;
        invalidate();
    }

    public void emptyProperty() {
        _Property = PredicatedLists.createInvalidating(this, pred_Property, new ArrayList());
    }

    public void validateThis()
        throws LocalValidationException
    {
    }

    public void validate(Validator v)
        throws StructureValidationException
    {
        for (Iterator i = _JdbcConfig.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
        for (Iterator i = _Property.iterator(); i.hasNext(); ) {
            v.validate(((ValidatableObject) i.next()));
        }
    }

    public void marshal(Marshaller m)
        throws IOException
    {
        XMLWriter w = m.writer();
        w.start("config");
        if (_JdbcConfig.size()> 0) {
            for (Iterator i = _JdbcConfig.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        if (_Property.size()> 0) {
            for (Iterator i = _Property.iterator(); i.hasNext(); ) {
                m.marshal(((MarshallableObject) i.next()));
            }
        }
        w.end("config");
    }

    public void unmarshal(Unmarshaller u)
        throws UnmarshalException
    {
        XMLScanner xs = u.scanner();
        Validator v = u.validator();
        xs.takeStart("config");
        while (xs.atAttribute()) {
            String an = xs.takeAttributeName();
            throw new InvalidAttributeException(an);
        }
        {
            List l = PredicatedLists.create(this, pred_JdbcConfig, new ArrayList());
            while (xs.atStart("jdbcConfig")) {
                l.add(((JdbcConfig) u.unmarshal()));
            }
            _JdbcConfig = PredicatedLists.createInvalidating(this, pred_JdbcConfig, l);
        }
        {
            List l = PredicatedLists.create(this, pred_Property, new ArrayList());
            while (xs.atStart("property")) {
                l.add(((Property) u.unmarshal()));
            }
            _Property = PredicatedLists.createInvalidating(this, pred_Property, l);
        }
        xs.takeEnd("config");
    }

    public static Config unmarshal(InputStream in)
        throws UnmarshalException
    {
        return unmarshal(XMLScanner.open(in));
    }

    public static Config unmarshal(XMLScanner xs)
        throws UnmarshalException
    {
        return unmarshal(xs, newDispatcher());
    }

    public static Config unmarshal(XMLScanner xs, Dispatcher d)
        throws UnmarshalException
    {
        return ((Config) d.unmarshal(xs, (Config.class)));
    }

    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (!(ob instanceof Config)) {
            return false;
        }
        Config tob = ((Config) ob);
        if (_JdbcConfig!= null) {
            if (tob._JdbcConfig == null) {
                return false;
            }
            if (!_JdbcConfig.equals(tob._JdbcConfig)) {
                return false;
            }
        } else {
            if (tob._JdbcConfig!= null) {
                return false;
            }
        }
        if (_Property!= null) {
            if (tob._Property == null) {
                return false;
            }
            if (!_Property.equals(tob._Property)) {
                return false;
            }
        } else {
            if (tob._Property!= null) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        h = ((127 *h)+((_JdbcConfig!= null)?_JdbcConfig.hashCode(): 0));
        h = ((127 *h)+((_Property!= null)?_Property.hashCode(): 0));
        return h;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<<config");
        if (_JdbcConfig!= null) {
            sb.append(" jdbcConfig=");
            sb.append(_JdbcConfig.toString());
        }
        if (_Property!= null) {
            sb.append(" property=");
            sb.append(_Property.toString());
        }
        sb.append(">>");
        return sb.toString();
    }

    public static Dispatcher newDispatcher() {
        Dispatcher d = new Dispatcher();
        d.register("config", (Config.class));
        d.register("description", (Description.class));
        d.register("jdbcConfig", (JdbcConfig.class));
        d.register("property", (Property.class));
        d.freezeElementNameMap();
        return d;
    }


    private static class JdbcConfigPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof JdbcConfig)) {
                throw new InvalidContentObjectException(ob, (JdbcConfig.class));
            }
        }

    }


    private static class PropertyPredicate
        implements PredicatedLists.Predicate
    {


        public void check(Object ob) {
            if (!(ob instanceof Property)) {
                throw new InvalidContentObjectException(ob, (Property.class));
            }
        }

    }

}
