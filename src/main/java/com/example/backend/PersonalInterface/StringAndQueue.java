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
        if (History.split("\\|").length >= 10) {
            System.out.println(History);
            History = History.substring(0 , History.lastIndexOf("|"));
        }
        return tmpName + "|" + History;
    }
}
