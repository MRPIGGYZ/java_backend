package com.example.backend.PersonalInterface;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public interface StringAndQueue {
    static String[] getHistory (String History) {
        String[] hisArr = History.split("\\|");
        return hisArr;
    }
    static String HistoryAppend (String tmpName, String History) {
        if (History.split("\\|").length >= 10) {
            History = History.substring(0 , History.lastIndexOf("\\|"));
        }
        return tmpName + "|" + History;
    }
}
