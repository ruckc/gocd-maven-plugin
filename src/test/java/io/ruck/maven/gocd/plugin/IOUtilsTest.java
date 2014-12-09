package io.ruck.maven.gocd.plugin;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author ruckc
 */
public class IOUtilsTest {
    @Test
    public void testToString() throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream("initial".getBytes("UTF-8"));
        assertEquals("initial",IOUtils.toString(bais,1));
    }

    @Test
    public void testCopy() throws Exception {
        StringReader sr = new StringReader("initial");
        StringWriter sw = new StringWriter();
        IOUtils.copy(sr, sw, 1);
        assertEquals("initial",sw.toString());
    }
}
