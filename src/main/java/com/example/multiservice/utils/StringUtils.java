package com.example.multiservice.utils;

public  class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static String[] splitStringBySpace(String str) {
        if (str == null || str.trim().isEmpty()) {
            // Avoid NullPointerException
            return new String[0];
        }
        // spit string by " "
        return str.trim().split("\\s+");
    }

}
