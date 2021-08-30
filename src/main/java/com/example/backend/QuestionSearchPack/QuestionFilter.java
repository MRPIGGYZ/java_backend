package com.example.backend.QuestionSearchPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface QuestionFilter {
    default JSONArray DropNChoice(JSONArray originList) {
        JSONArray returnValue = new JSONArray();
        for (int i=0; i<originList.size(); i++) {
            JSONObject thisone = ((JSONObject)originList.get(i));
            String str = thisone.get("qAnswer").toString();
            if (str.equals("D")||str.equals("C")||str.equals("B")||str.equals("A")) {
                returnValue.add(thisone);
            }
        }
        return returnValue;
    }
}
