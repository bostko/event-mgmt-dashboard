package com.valentin.mgmt.event.domain;

public class JoinUtils {
    public static String join(String[] source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length; ++i) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(source[i]);
        }

        return result.toString();
    }
}
