package io.ruck.maven.gocd.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 *
 * @author ruckc
 */
public class IOUtils {

    public static String toString(InputStream is) throws UnsupportedEncodingException, IOException {
        return toString(is, 16384);
    }

    static String toString(InputStream is, int bufsize) throws UnsupportedEncodingException, IOException {
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        StringWriter sw = new StringWriter();
        copy(isr, sw, bufsize);
        return sw.toString();
    }

    public static void copy(Reader r, Writer w) throws IOException {
        copy(r, w, 16384);
    }

    static void copy(Reader r, Writer w, int bufsize) throws IOException {
        char[] buf = new char[bufsize];
        int read;
        while ((read = r.read(buf)) > 0) {
            w.write(buf, 0, read);
        }
    }
}
