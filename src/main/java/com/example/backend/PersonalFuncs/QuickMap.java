package com.example.backend.PersonalFuncs;

import java.util.HashMap;

public interface QuickMap {
    static HashMap<String, String> createMap(String a1, String a2, String b1, String b2, String c1, String c2) {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put(a1, a2);
        ret.put(b1, b2);
        ret.put(c1, c2);
        return ret;
    }
}
