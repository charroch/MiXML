
package novoda.mixml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLNode implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    Document doc;

    Node current;

    public XMLNode() {
    }

    public void parse(InputStream in) throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in, "UTF-8");
        current = doc.getDocumentElement();
    }

    public XMLNode get(int index) {
        if (current.hasChildNodes()) {
            return recurse(current.getChildNodes().item(index * 2 + 1));
        }
        return new EmptyNode();
    }

    public List<String> getAsList() {
        List<String> ret = new ArrayList<String>(size());
        if (current.hasChildNodes()) {
            for (int i = 0; i < size(); i++) {
                Node node = current.getChildNodes().item(i * 2 + 1).getFirstChild();
                if (node != null) {
                    ret.add(node.getNodeValue());
                } else {
                    ret.add("");
                }
            }
        }
        return ret;
    }

    public XMLNode path(String name) {
        // take care of root
        if (current.getNodeName().equals(name)) {
            return this;
        }

        NodeList list = current.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(name)) {
                return recurse(list.item(i));
            }
        }
        return new EmptyNode();
    }

    public String getAsString() {
        if (current.hasChildNodes()) {
            NodeList list = current.getChildNodes();
            StringBuffer b = new StringBuffer();
            for (int i = 0; i <  list.getLength(); i++) {
                Node l = list.item(i);
                b.append(l.getNodeValue());
            }
            return b.toString();
        }
        return current.getFirstChild().getNodeValue();
    }

    public int getAsInt() {
        return Integer.parseInt(current.getFirstChild().getNodeValue());
    }

    public double getAsDouble() {
        return Double.parseDouble(current.getFirstChild().getNodeValue());
    }

    public int size() {
        return (current.getChildNodes().getLength() / 2);
    }

    private XMLNode recurse(Node current) {
        XMLNode clone = new XMLNode();
        clone.current = current;
        return clone;
    }

    public Map<String, String> getAsMap() {
        Map<String, String> ret = new HashMap<String, String>(size());
        if (current.hasChildNodes()) {
            for (int i = 0; i < size(); i++) {
                Node node = current.getChildNodes().item(i * 2 + 1).getFirstChild();
                if (node != null) {
                    ret.put(node.getParentNode().getNodeName(), node.getNodeValue());
                } else {
                }
            }
        }
        return ret;
    }

    public List<XMLNode> getAsList(String string) {
        List<XMLNode> ret = new ArrayList<XMLNode>(size());
        if (current.hasChildNodes()) {
            for (int i = 0; i < current.getChildNodes().getLength() / 2; i++) {
                Node t = current.getChildNodes().item(i * 2 + 1);
                ret.add(recurse(t));
            }
        }
        return ret;
    }

    public float getAsFloat() {
        return Float.parseFloat(current.getFirstChild().getNodeValue());
    }

    public long getAsLong() {
        return Long.parseLong(current.getFirstChild().getNodeValue());
    }

    public short getAsShort() {
        return Short.parseShort(current.getFirstChild().getNodeValue());
    }

    public boolean isArray() {
        if (current != null && current.hasChildNodes()) {
            Node previous = null;
            Node next = null;
            for (int i = 1; i < current.getChildNodes().getLength() / 2; i++) {
                if (current.getChildNodes().getLength() / 2 < 2) {
                    return false;
                }
                previous = current.getChildNodes().item(i * 2 + 1);
                next = current.getChildNodes().item((i - 1) * 2 + 1);
                if (!next.getNodeName().equals(previous.getNodeName())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
