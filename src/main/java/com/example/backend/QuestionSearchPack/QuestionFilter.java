package com.example.backend.QuestionSearchPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface QuestionFilter {
    static JSONArray DropNChoice(JSONArray originList, String collection) {
        JSONArray returnValue = new JSONArray();
        for (int i=0; i<originList.size(); i++) {
            try {
                JSONObject thisone = originList.getJSONObject(i);
                String flag = "0";
                if (collection!=null&&collection.contains(thisone.getString("id"))) {
                    flag = "1";
                }
                JSONObject data = QuestDivision(thisone);
                data.put("star", flag);
                returnValue.add(data);
            } catch (Exception e) {
                continue;
            }
        }
        return returnValue;
    }
    static JSONObject QuestDivision(JSONObject thisone) throws Exception {
        String qAnswer = thisone.get("qAnswer").toString();
        String qBody = thisone.get("qBody").toString();
        if (qAnswer.equals("D")||qAnswer.equals("C")||qAnswer.equals("B")||qAnswer.equals("A")) {
            Integer indexofA, indexofB, indexofC, indexofD;
            indexofA = qBody.indexOf("A．");
            if (indexofA == -1) {
                indexofA = qBody.indexOf("A.");
                indexofB = qBody.indexOf("B.");
                indexofC = qBody.indexOf("C.");
                indexofD = qBody.indexOf("D.");
            } else {
                indexofB = qBody.indexOf("B．");
                indexofC = qBody.indexOf("C．");
                indexofD = qBody.indexOf("D．");
            }
            thisone.put("A", qBody.substring(indexofA+2, indexofB));
            thisone.put("B", qBody.substring(indexofB+2, indexofC));
            thisone.put("C", qBody.substring(indexofC+2, indexofD));
            thisone.put("D", qBody.substring(indexofD+2));
            thisone.put("qBody", qBody.substring(0, indexofA));
        } else {
            throw new Exception();
        }
//        System.out.println(thisone);
        return thisone;
    }
}
