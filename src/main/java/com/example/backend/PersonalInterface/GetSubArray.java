package com.example.backend.PersonalInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.QuestionSearchPack.QuestionFilter;

import java.util.*;

public interface GetSubArray {
    static Integer[] getIntegerArray (Integer size) {
        Integer[] returnvalue = new Integer[size];
        if (size > 10) {
            for (int i=0; i<10; i++) {
                returnvalue[i] = 1;
            }
        } else {
            Integer per = Math.round(10.0f / size.floatValue());
            for (int i=0; i<size-1; i++) {
                returnvalue[i] = per;
            }
            returnvalue[size-1] = 10 - per * (size - 1);
        }
        return returnvalue;
    }

    static JSONArray getRandomArray (JSONArray array, int number) {
        long t = System.currentTimeMillis();
        Random rd = new Random(t);
        Collections.shuffle(array, rd);
        return (JSONArray) JSONArray.parse(array.subList(0, Math.min(number, array.size())).toString());
    }

    static JSONArray convertToArray (String content, String collection) {
        String[] contents = content.split("##");
        JSONArray data = new JSONArray();
        for (String i : contents) {
            try {
                JSONObject obj = new JSONObject();
                String[] tmp = i.split("%%");
                obj.put("qAnswer", tmp[0]);
                obj.put("id", tmp[1]);
                obj.put("qBody", tmp[2]);
                JSONObject thisone = QuestionFilter.QuestDivision(obj);
                String flag = "1";
                if (collection==null||!collection.contains(tmp[1])) flag = "0";
                thisone.put("star", flag);
                data.add(thisone);
            } catch (Exception e) {
                continue;
            }
        }
        return data;
    }

    static JSONArray getSperCourseArray (JSONArray array, String course) {
        JSONArray returnValue = new JSONArray();
        for (int i=0; i<array.size(); i++) {
            if (course.contains(array.getJSONObject(i).getString("course"))) {
                returnValue.add(array.getJSONObject(i));
            }
        }
        return returnValue;
    }

    static JSONArray balancing (JSONArray origin, Integer num) {
        List list = JSONArray.parseArray(origin.toJSONString());
        HashSet hs = new HashSet(list);
        JSONArray newjsonarray= (JSONArray) JSONArray.parse(hs.toString());
        return (JSONArray) JSONArray.parse(newjsonarray.subList(0, Math.min(num, newjsonarray.size())).toString());
    }
}
