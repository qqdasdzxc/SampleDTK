package com.dmitrijkuzmin.sampledtk.utils;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isStringContainPattern(String stringToSearch, String patternToSearchFor) {
        return Pattern.compile(patternToSearchFor, Pattern.CASE_INSENSITIVE)
                .matcher(stringToSearch)
                .find();
    }
}
