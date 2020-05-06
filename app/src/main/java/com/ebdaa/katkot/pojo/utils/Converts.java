package com.ebdaa.katkot.pojo.utils;

public class Converts {

    public static String convertToJson(String objectJson) {

        return "\"" + objectJson.replaceAll("\"", "\\\\\"") + "\"";
    }
}
