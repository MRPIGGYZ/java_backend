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
    static JSONArray QuestionSplit (String string, String collection) {
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
    static String UpdateEntityHistory (String historystring, String course, String label, String category, String uri) {
        String[] history = null;
        try {
            history = historystring.split("##");
        } catch (Exception e) {
            return course + "%%" + label + "%%" + category + "%%" + uri;
        }
        String result = "";
        Integer time = 1;
        for (String i : history) {
            if (i.contains(uri)) {
                String[] thisone = i.split("%%");
                if (thisone.length>4) time = Integer.parseInt(thisone[4]);
                time++;
                continue;
            }
            result = result + "##" + i;
        }
        result = course + "%%" + label + "%%" + category + "%%" + uri + "%%" + time + result;
        return result;
    }
    static String UpdateQuestionList (String historystring, String qAnswer, String id, String qBody) {
        String[] history = null;
        try {
            history = historystring.split("##");
            if (history[0].equals("")) throw new Exception();
        } catch (Exception e) {
            return qAnswer + "%%" + id + "%%" + qBody;
        }
        String result = "";
        for (String i : history) {
            String splitid = i.split("%%")[1];
            if (splitid.equals(id)) {
                continue;
            }
            result = result + "##" + i;
        }
        result = qAnswer + "%%" + id + "%%" + qBody + result;
        System.out.println(result);
        return result;
    }
}
