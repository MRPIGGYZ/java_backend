package com.example.backend.InstanceSearchPack;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.PersonalInterface.StringAndQueue;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/instanceList")
public class InstanceSearchController implements QuickMap, BackendLogin {
    static String id = null;
    @Autowired
    private UserDao userDao;

    @GetMapping(path="")
    public @ResponseBody JSONObject EntitySearch (HttpServletRequest req, @RequestParam String course
            , @RequestParam String searchKey) {
        String name = (String) req.getAttribute("userName");
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname(name).get(0);
        user.setEntitySearchHistory(StringAndQueue.appendFromString(searchKey, user.getEntitySearchHistory()));
        userDao.save(user);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course={course}&searchKey={searchKey}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("course", course, "searchKey", searchKey, "id", id));
        try{
            returnValue.put("status", true);
            returnValue.put("data", response.get("data"));
        } catch (Exception e) {
            returnValue.put("status", false);
            returnValue.put("data", "openedu break down");
        }
        return returnValue;
    }

    @GetMapping(path="/history")
    public @ResponseBody JSONObject SearchHistory (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname((String) req.getAttribute("userName")).get(0);
        returnValue.put("status", true);
        returnValue.put("data", StringAndQueue.getArrFromString(user.getEntitySearchHistory()));
        return returnValue;
    }
}
