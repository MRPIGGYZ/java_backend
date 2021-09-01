package com.example.backend.UserEntityPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.QuestionSearchPack.QuestionFilter;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/entity/collection")
public class EntityCollecController {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/save")
    public @ResponseBody
    JSONObject Collect (HttpServletRequest req, @RequestParam String qAnswer, @RequestParam String id, @RequestParam String qBody) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String collections = user.getQuestionCollection();
        if (collections == null || collections.equals("")) {
            collections = qAnswer + "%%" + id + "%%" + qBody;
        } else {
            collections = qAnswer + "%%" + id + "%%" + qBody + "##" + collections;
        }
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
            if (i.contains(id)) {
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
        String[] collections = user.getQuestionCollection().split("##");
        JSONArray data = new JSONArray();
        for (String i : collections) {
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
        returnValue.put("data", data);
        returnValue.put("status", true);
        return returnValue;
    }
}
