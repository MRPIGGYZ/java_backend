package com.example.backend.EntityDetailsPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface SimplifyData {
    default JSONObject Simplify(JSONObject data, JSONObject gooddata) {
        JSONArray content = data.getJSONArray("content");
        JSONArray afterDrop = new JSONArray();
        for (int i=0; i<content.size(); i++) {
            JSONObject tmp = content.getJSONObject(i);
            if (tmp.containsKey("object")) {
                if (isvalid(tmp.get("object").toString())) afterDrop.add(tmp);
            }
            if (tmp.containsKey("subject")) {
                if (isvalid(tmp.get("subject").toString())) afterDrop.add(tmp);
            }
        }
        data.put("property", gooddata.getJSONArray("entity_features"));
        data.put("content", afterDrop);
        return data;
    }

    default boolean isvalid(String s) {
        boolean result = false;
        if (s.contains("chinese") || s.contains("math") || s.contains("english") ||
                s.contains("physics") || s.contains("chemistry") || s.contains("biology") ||
                s.contains("history") || s.contains("geo") || s.contains("politics")) {
            result = true;
        }
        return result;
    }
}
