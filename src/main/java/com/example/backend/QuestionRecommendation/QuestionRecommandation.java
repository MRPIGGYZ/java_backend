package com.example.backend.QuestionRecommendation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.QuestionSearchPack.QuestionFilter;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/question/recommandation")
public class QuestionRecommandation {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/get")
    public @ResponseBody JSONObject getFiveQuestions (HttpServletRequest req) {
        
        return null;
    }

    public JSONObject getQuestionsByString (String body) {
        String[] cotents = body.split("##");
        JSONArray data = new JSONArray();
        for (String i : cotents) {
            try {
                JSONObject obj = new JSONObject();
                String[] tmp = i.split("%%");
                obj.put("qAnswer", tmp[0]);
                obj.put("id", tmp[1]);
                obj.put("qBody", tmp[2]);
                data.add(QuestionFilter.QuestDivision(obj));
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }
}
