
package novoda.mixml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class XMLNodeTest {

    private static FileInputStream in;

    private XMLNode xmlnode;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        xmlnode = new XMLNode();

        File file = new File("src/test/resources/artist.xml");
        in = new FileInputStream(file);
    }

    @After
    public void tearDown() throws Exception {
        in.close();
    }

    @Test
    public void testSimpleParsing() throws Exception {
        xmlnode.parse(in);
        assertEquals("BROTHER ALI", xmlnode.path("result").path("bName").getAsString());
    }

    @Test
    public void testTableIndex() throws Exception {
        File file = new File("src/test/resources/bands2.xml");
        xmlnode.parse(new FileInputStream(file));
        assertEquals(201, xmlnode.path("result").path("bIDs").get(2).getAsInt());
    }

    @Test
    public void testSizeOfArray() throws Exception {
        File file = new File("src/test/resources/bands2.xml");
        xmlnode.parse(new FileInputStream(file));
        assertEquals(6, xmlnode.path("result").path("bIDs").size());
    }

    @Test
    public void testDoubleQuery() throws Exception {
        xmlnode.parse(in);
        assertEquals("BROTHER ALI", xmlnode.path("result").path("bName").getAsString());
        assertEquals("BROTHER ALI", xmlnode.path("result").path("bName").getAsString());
    }

    @Test
    public void testgetaslist() throws Exception {
        File file = new File("src/test/resources/bandspage.xml");
        xmlnode.parse(new FileInputStream(file));
        List<String> l = xmlnode.path("result").path("bLetters").getAsList();
        assertEquals(l.get(2), "C");
    }

    @Test
    public void testGetObjectListName() throws Exception {
        File file = new File("src/test/resources/list.xml");
        xmlnode.parse(new FileInputStream(file));
        Map<String, String> names = xmlnode.path("response").path("optionsList").path(
                "optionsList_item").getAsMap();
        assertEquals(names.get("action"), "3");
    }

    @Test
    public void testSizeChildren() throws Exception {
        File file = new File("src/test/resources/list.xml");
        xmlnode.parse(new FileInputStream(file));
        int size = xmlnode.path("response").path("optionsList").size();
        assertEquals(size, 3);
    }

    @Test
    public void testGetListOfObjects() throws Exception {
        File file = new File("src/test/resources/list.xml");
        xmlnode.parse(new FileInputStream(file));
        List<XMLNode> names = xmlnode.path("response").path("optionsList").getAsList(
                "optionsList_item");
        assertEquals(names.get(0).path("action").getAsString(), "3");
        assertEquals(names.get(1).path("headline").getAsString(), "EVENT SCHEDULE");
    }

    // @Test
    public void testSimpleXPath() throws Exception {
        File file = new File("src/test/resources/list.xml");
        xmlnode.parse(new FileInputStream(file));
        List<XMLNode> names = xmlnode.path("response/optionsList").getAsList("optionsList_item");
        assertEquals(names.get(0).path("action").getAsString(), "3");
        assertEquals(names.get(1).path("headline").getAsString(), "EVENT SCHEDULE");
    }

    @Test
    public void testIsArray() throws Exception {
        File file = new File("src/test/resources/bands2.xml");
        xmlnode.parse(new FileInputStream(file));
        assertTrue(xmlnode.path("result").path("bIDs").isArray());
        assertFalse(xmlnode.path("result").isArray());
    }

    @Test
    public void testURLEscape() throws Exception {
        File file = new File("src/test/resources/url.xml");
        xmlnode.parse(new FileInputStream(file));
        assertEquals("http://media.tellus.no/images/?d=382&p=1433&t=2", xmlnode.path("response").path("largeImageUrl").getAsString().trim());
    }
}
