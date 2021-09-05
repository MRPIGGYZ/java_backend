package com.example.backend.QuestionSearchPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface QuestionFilter {
    static JSONArray DropNChoice(JSONArray originList) {
        JSONArray returnValue = new JSONArray();
        for (int i=0; i<originList.size(); i++) {
            try {
                returnValue.add(QuestDivision((JSONObject)originList.get(i)));
            } catch (Exception e) {
                continue;
            }
        }
        return returnValue;
    }
    static JSONObject QuestDivision(JSONObject thisone) throws Exception {
        String qAnswer = thisone.get("qAnswer").toString();
        String qBody = thisone.get("qBody").toString();
        if (qAnswer.contains("D")||qAnswer.contains("C")||qAnswer.contains("B")||qAnswer.contains("A")) {
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
        }
        return thisone;
    }
}
