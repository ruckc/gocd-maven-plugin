package io.ruck.maven.gocd.plugin;

/**
 *
 * @author ruckc
 */
public class StringUtils {
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }
    
    public static boolean equals(String a, String b) {
        if(a == null && b == null) {
            return true;
        }
        if(a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }
}
