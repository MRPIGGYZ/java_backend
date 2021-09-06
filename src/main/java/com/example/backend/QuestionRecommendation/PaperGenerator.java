package com.example.backend.QuestionRecommendation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.LinkInstancePack.linkInstanceController;
import com.example.backend.PersonalInterface.GetSubArray;
import com.example.backend.QuestionSearchPack.QuestionSearchController;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/papergenerator")
public class PaperGenerator {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/get")
    public @ResponseBody
    JSONObject getPaper (HttpServletRequest req, @RequestParam String context, @RequestParam String course) {
        JSONArray results;
        JSONObject returnValue = new JSONObject();
        try {
            results = linkInstanceController.getLinkedInfo(context, course);
        } catch (Exception e) {
            returnValue.put("status", false);
            returnValue.put("data", "openedu break down");
            return returnValue;
        }
        Integer length = results.size();
        JSONArray qlist = new JSONArray();
//        Integer[] split = GetRandomSubArray.getIntegerArray(length);
//        for (int i=0; i<length; i++) {
//            System.out.println(split[i]);
//        }
        for (int i=0; i<length; i++) {
            JSONObject data = results.getJSONObject(i);
            qlist.addAll(QuestionSearchController.getQuestionList(data.getString("entity")));
        }
        if (qlist.size() < 5) {
            returnValue.put("status", false);
            returnValue.put("data", "您搜索的知识点似乎有点冷门，尝试再加一些关键词或换一个科目搜索吧");
        } else if (qlist.size() < 10) {
            returnValue.put("status", true);
            returnValue.put("data", GetSubArray.getRandomArray(qlist, qlist.size()));
            returnValue.put("info", "额外算一下分数，该情况下单个题目并非十分");
        } else {
            returnValue.put("status", true);
            returnValue.put("data", GetSubArray.getRandomArray(qlist, 10));
        }
        return returnValue;
    }

}
