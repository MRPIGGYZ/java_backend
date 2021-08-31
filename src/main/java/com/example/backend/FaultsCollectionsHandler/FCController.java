package com.example.backend.FaultsCollectionsHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.QuestionSearchPack.QuestionSearchController;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(path="/collect")
public class FCController {
    static String id = null;
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/save")
    public @ResponseBody JSONObject Collect (HttpServletRequest req, @RequestParam String searchkey, @RequestParam String id) {
//        System.out.println(question);
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String collections = user.getCollection();
        if (collections == null || collections.equals("")) {
            collections = searchkey + "%%" + id;
        } else {
            collections = searchkey + "%%" + id + "##" + collections;
        }
        user.setCollection(collections);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/removecollection")
    public @ResponseBody JSONObject CancelCollect (HttpServletRequest req, @RequestParam String id) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        String[] collections = user.getCollection().split("##");
        String afterDelete = "";
        for (String i : collections) {
            if (i.contains(id)) {
                continue;
            }
            afterDelete = afterDelete + i + "##";
        }
        if (!afterDelete.equals("")) afterDelete.substring(0, afterDelete.length()-2);
        user.setCollection(afterDelete);
        userDao.save(user);
        returnValue.put("status", true);
        return returnValue;
    }
    @GetMapping(path="/getcollections")
    public @ResponseBody JSONObject GetCollectionList (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        try {
            String[] collections = user.getCollection().split("##");
            JSONArray data = new JSONArray();
            for (String i : collections) {
                String[] tmp = i.split("%%");
                System.out.println(i);
                data.add(QuestionSearchController.GetSperQuestion(tmp[0], tmp[1]));
            }
            returnValue.put("data", data);
        } catch (Exception e) {
            returnValue.put("data", "[]");
        }
        returnValue.put("status", true);
        return returnValue;
    }
}
