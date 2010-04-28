
package novoda.mixml;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

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
        File file = new File("/tmp/artist.xml");
        in = new FileInputStream(file);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        in.close();
    }

    @Before
    public void setUp() throws Exception {
        xmlnode = new XMLNode();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSimpleParsing() throws Exception {
        xmlnode.parse(in);
        assertEquals("BROTHER ALI", xmlnode.path("result").path("bName").getAsString());
    }

    @Test
    public void testTableIndex() throws Exception {
        File file = new File("/tmp/bands2.xml");
        xmlnode.parse(new FileInputStream(file));
        assertEquals(201, xmlnode.path("result").path("bIDs").get(2).getAsInt());
    }
    
    @Test
    public void testSizeOfArray() throws Exception {
        File file = new File("/tmp/bands2.xml");
        xmlnode.parse(new FileInputStream(file));
        assertEquals(6, xmlnode.path("result").path("bIDs").size());
    }

}
