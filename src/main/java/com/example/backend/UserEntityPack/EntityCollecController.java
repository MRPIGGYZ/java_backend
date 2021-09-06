package com.example.backend.UserEntityPack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.StringSplit;
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
    JSONObject Collect (HttpServletRequest req, @RequestParam String course, @RequestParam String label, @RequestParam String category, @RequestParam String uri) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String collections = user.getEntityCollection();
        if (collections == null || collections.equals("")) {
            collections = course + "%%" + label + "%%" + category + "%%" + uri;
        } else {
            collections = course + "%%" + label + "%%" + category + "%%" + uri + "##" + collections;
        }
        user.setEntityCollection(collections);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/remove")
    public @ResponseBody JSONObject CancelCollect (HttpServletRequest req, @RequestParam String label) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String[] collections = user.getEntityCollection().split("##");
        String afterDelete = "";
        for (String i : collections) {
            if (i.equals(label)) {
                continue;
            }
            afterDelete = afterDelete + i + "##";
        }
        if (!afterDelete.equals("")) afterDelete.substring(0, afterDelete.length()-2);
        user.setEntityCollection(afterDelete);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/getall")
    public @ResponseBody JSONObject GetCollectionList (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        JSONArray data = StringSplit.EntitySplit(user.getEntityCollection());
        returnValue.put("data", data);
        returnValue.put("status", true);
        return returnValue;
    }
}
