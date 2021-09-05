package com.example.backend.PersonalInterface;

import com.alibaba.fastjson.JSONArray;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public interface GetRandomSubArray {
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
        System.out.println(array.subList(0, number));
        return (JSONArray) JSONArray.parse(array.subList(0, number).toString());
    }
}
