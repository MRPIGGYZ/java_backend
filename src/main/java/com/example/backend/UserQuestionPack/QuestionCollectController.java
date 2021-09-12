package com.example.backend.UserQuestionPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.StringSplit;
import com.example.backend.QuestionSearchPack.QuestionFilter;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/question/collection")
public class QuestionCollectController {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/save")
    public @ResponseBody JSONObject Collect (HttpServletRequest req, @RequestParam String qAnswer, @RequestParam String id, @RequestParam String qBody) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String collections = StringSplit.UpdateQuestionList(user.getQuestionCollection(), qAnswer, id, qBody);
        user.setQuestionCollection(collections);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/remove")
    public @ResponseBody JSONObject CancelCollect (HttpServletRequest req, @RequestParam String id) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String[] collections = user.getQuestionCollection().split("##");
        String afterDelete = "";
        for (String i : collections) {
            String splitid = i.split("%%")[1];
            if (splitid.equals(id)) {
                continue;
            }
            afterDelete = afterDelete + i + "##";
        }
        if (!afterDelete.equals("")) afterDelete.substring(0, afterDelete.length()-2);
        user.setQuestionCollection(afterDelete);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/getall")
    public @ResponseBody JSONObject GetCollectionList (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String[] collections;
        try {
            collections = user.getQuestionCollection().split("##");
        } catch (Exception e) {
            returnValue.put("data", null);
            returnValue.put("status", false);
            return returnValue;
        }
        JSONArray data = new JSONArray();
        for (String i : collections) {
            try {
                JSONObject obj = new JSONObject();
                String[] tmp = i.split("%%");
                obj.put("qAnswer", tmp[0]);
                obj.put("id", tmp[1]);
                obj.put("qBody", tmp[2]);
                JSONObject thisone = QuestionFilter.QuestDivision(obj);
                thisone.put("star", "1");
                data.add(thisone);
            } catch (Exception e) {
                continue;
            }
        }
        returnValue.put("data", data);
        returnValue.put("status", true);
        return returnValue;
    }
}
