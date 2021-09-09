package com.example.backend.QuestionRecommendation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.JwtUtils.PassToken;
import com.example.backend.PersonalInterface.GetSubArray;
import com.example.backend.QuestionSearchPack.QuestionFilter;
import com.example.backend.QuestionSearchPack.QuestionSearchController;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(path="/question/recommandation")
public class QuestionRecommandation {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/get")
    public @ResponseBody JSONObject getSomeQuestions (HttpServletRequest req, @RequestParam String number) { //不能少于8个，建议8-10
        Integer num = Integer.parseInt(number);
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        JSONArray data = getQuestion(user, num);
        returnValue.put("data", data);
        returnValue.put("status", true);
        return returnValue;
    }
    @PassToken
    public JSONArray getQuestion (User user, Integer num) {
        JSONArray data = new JSONArray();
//        String entitycollection = user.getEntityCollection();
        String entitysearch= user.getEntitySearchHistory();
        String questionsearch= user.getQuestionSearchHistory();
        String faults = user.getQuestionFaults();
        String collection = user.getQuestionCollection();
        String history = user.getQuestionHistory();
        Integer[] numsplit = new Integer[5];
//        if (entitycollection!=null && entitycollection!="") {
//            int length = entitycollection.split("##").length;
//            if (length < 4) {
//                numsplit[0] = length;
//            }
//            System.out.println("numsplits0:" + numsplit[0]);
//            data.addAll(convertEntityToArray(entitycollection, numsplit[0], collection));
//            numsplit[0] = data.size();
//        } else {
//            numsplit[0] = 0;
//        }
        if (entitysearch!=null && entitysearch!="") {
            int length = entitysearch.split("\\|").length;
            if (length < 3) {
                numsplit[0] = length;
            } else if (length < 8) {
                numsplit[0] = 2;
            }
            data.addAll(convertSearchToArray(entitysearch, numsplit[0], collection));
        } else {
            numsplit[0] = 0;
        }
        if (questionsearch!=null && questionsearch!="") {
            int length = questionsearch.split("\\|").length;
            if (length < 3) {
                numsplit[1] = length;
            } else if (length < 8) {
                numsplit[1] = 2;
            }
            data.addAll(convertSearchToArray(questionsearch, numsplit[1], collection));
        } else {
            numsplit[1] = 0;
        }
        if (faults!=null && faults!="") {
            int length = faults.split("##").length;
            if (length < 3) {
                if (Math.random() < 0.3) numsplit[2] = 0;
                else numsplit[2] = 1;
            } else if (length < 5) {
                numsplit[2] = 1;
            } else {
                if (Math.random() < 0.2) numsplit[2] = 2;
                else numsplit[2] = 1;
            }
            System.out.println("numsplits2:" + numsplit[2]);
            data.addAll(GetSubArray.getRandomArray(GetSubArray.convertToArray(faults, collection), numsplit[2]));
        } else {
            numsplit[2] = 0;
        }
        if (collection!=null && collection!="") {
            int length = collection.split("##").length;
            if (length < 3) {
                if (Math.random() < 0.3) numsplit[3] = 0;
                else numsplit[3] = 1;
            } else {
                numsplit[3] = 1;
            }
            System.out.println("numsplits3:" + numsplit[3]);
            data.addAll(GetSubArray.getRandomArray(GetSubArray.convertToArray(collection, collection), numsplit[3]));
        } else {
            numsplit[3] = 0;
        }
        if (history!=null && history!="") {
            int length = history.split("##").length;
            if (length < 3) {
                if (Math.random() < 0.3) numsplit[4] = 0;
                else numsplit[4] = 1;
            } else {
                numsplit[4] = 1;
            }
            System.out.println("numsplits4:" + numsplit[4]);
            data.addAll(GetSubArray.getRandomArray(GetSubArray.convertToArray(history, collection), numsplit[4]));
        } else {
            numsplit[4] = 0;
        }
        int random = num - numsplit[0] - numsplit[1] - numsplit[2] - numsplit[3] - numsplit[4];
        System.out.println(data);
        for (int i=0; i<5; i++) System.out.println(numsplit[i]);
        System.out.println(random);
        User datauser = userDao.getUserByname("data").get(0);
        while (true) {
            data.addAll(convertSearchToArray(datauser.getQuestionSearchHistory(), random, collection));
            if (data.size()>=num) {
                data = GetSubArray.getRandomArray(data, num);
                break;
            }
        }
        return data;
    }
    @PassToken
    public JSONArray convertEntityToArray (String content, Integer num, String collection) {
        String[] contents = content.split("##");
        List<Comparable> list = Arrays.asList(contents);
        Collections.shuffle(list);
        list.toArray(contents);
        JSONArray data = new JSONArray();
        for (int i=0; i<contents.length; i++) {
            if (data.size() >= num) break;
            try {
                String[] tmp = contents[i].split("%%");
                JSONArray thisone = QuestionSearchController.getQuestionList(tmp[1], collection);
                System.out.println("thisone:" + tmp[1]);
                data.addAll(GetSubArray.getRandomArray(thisone, 1));
            } catch (Exception e) {
                System.out.println("无");
                continue;
            }
        }
        return data;
    }
    @PassToken
    public JSONArray convertSearchToArray (String content, Integer num, String collection) {
        String[] contents = content.split("\\|");
        List<Comparable> list = Arrays.asList(contents);
        Collections.shuffle(list);
        list.toArray(contents);
        JSONArray data = new JSONArray();
        for (int i=0; i<contents.length; i++) {
            if (data.size() >= num) break;
            try {
                JSONArray thisone = QuestionSearchController.getQuestionList(contents[i], collection);
                System.out.println("thisone:" + contents[i]);
                data.addAll(GetSubArray.getRandomArray(thisone, 1));
            } catch (Exception e) {
                System.out.println("无");
                continue;
            }
        }
        return data;
    }
}
