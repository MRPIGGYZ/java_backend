package com.example.backend.PersonalInterface;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

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
        return (JSONArray) JSONArray.parse(array.subList(0, number).toString());
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
