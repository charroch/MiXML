
package novoda.mixml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
//    @Test
//    public void testNullValue() throws Exception {
//        File file = new File("src/test/resources/video.xml");
//        xmlnode.parse(new FileInputStream(file));
//        List<String> l = xmlnode.path("result").path("vVideoname").getAsList();
//        assertEquals(xmlnode.path("result").path("vVideourl").size(), 3);
//        assertEquals(l.get(1), "M&#246;torhead&#13;Interview");
//    }

}
