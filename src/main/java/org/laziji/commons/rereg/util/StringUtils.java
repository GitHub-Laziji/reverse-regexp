package org.laziji.commons.rereg.util;

public class StringUtils {

    public static String charsToString(char[] chars, int l, int r) {
        return new String(chars, l, r - l);
    }
}
