package com.example.backend.PersonalInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface StringSplit {
    static JSONArray EntitySplit (String string) {
        JSONArray data = new JSONArray();
        String[] collections = null;
        try {
            collections = string.split("##");
        } catch (Exception e) {
            return null;
        }
        for (String i : collections) {
            System.out.println();
            try {
                JSONObject obj = new JSONObject();
                String[] tmp = i.split("%%");
                obj.put("course", tmp[0]);
                obj.put("label", tmp[1]);
                obj.put("category", tmp[2]);
                obj.put("uri", tmp[3]);
                data.add(obj);
            } catch (Exception e) {
                continue;
            }
        }
        return data;
    }
}
