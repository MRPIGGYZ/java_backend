package com.example.backend.PersonalInterface;

public interface StringAndQueue {
    static String[] getArrFromString (String History) {
        if (History == null) return null;
        String[] hisArr = History.split("\\|");
        return hisArr;
    }
    static String appendFromString (String tmpName, String History) {
        if (History == null) {
            return tmpName;
        }
        String result = "";
        String []history = History.split("\\|");
        for (String i : history) {
            if (i.contains(tmpName)) {
                continue;
            }
            result = result + "|" + i;
        }
        result = tmpName + result;
        if (result.split("\\|").length >= 30) {
            result = result.substring(0 , result.lastIndexOf("|"));
        }
        return result;
    }
}
