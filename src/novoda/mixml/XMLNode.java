
package novoda.mixml;

import java.io.IOException;
import java.io.InputStream;

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

    void parse(InputStream in) throws SAXException, IOException, ParserConfigurationException,
            FactoryConfigurationError {
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
        current = doc.getDocumentElement();
    }

    public XMLNode get(int index) {
        if (current.hasChildNodes()) {
            current = current.getChildNodes().item(index * 2 + 1);
            return this;
        }
        return new EmptyNode();
    }

    public XMLNode path(String name) {
        // take care of root
        if (current.getNodeName().equals(name)) {
            return this;
        }

        NodeList list = current.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(name)) {
                current = list.item(i);
                return this;
            }
        }
        return new EmptyNode();
    }

    public String getAsString() {
        return current.getTextContent();
    }

    public int getAsInt() {
        return Integer.parseInt(current.getTextContent());
    }

    public int size() {
        return (current.getChildNodes().getLength() / 2);
    }
}
