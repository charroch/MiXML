
package novoda.mixml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLNode {

    Document doc;

    Node current;

    public XMLNode() {
    }

    public void parse(InputStream in) throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
        current = doc.getDocumentElement();
    }

    public XMLNode get(int index) {
        if (current.hasChildNodes()) {
            return recurse(current.getChildNodes().item(index * 2 + 1));
        }
        return new EmptyNode();
    }

    public List<String> getAsList() {
        if (current.hasChildNodes()) {
            List<String> ret = new ArrayList<String>(size());
            for (int i = 0; i < size(); i++) {
                Node node = current.getChildNodes().item(i * 2 + 1).getFirstChild();
                if (node != null) {
                    ret.add(node.getNodeValue());
                } else {
                    ret.add("");
                }
            }
            return ret;
        }
        return null;
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
        return current.getFirstChild().getNodeValue();
    }

    public int getAsInt() {
        return Integer.parseInt(current.getFirstChild().getNodeValue());
    }

    public int size() {
        return (current.getChildNodes().getLength() / 2);
    }

    private XMLNode recurse(Node current) {
        XMLNode clone = new XMLNode();
        clone.current = current;
        return clone;
    }
}
