package com.example.backend.ExercisesSearchPack;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.PersonalInterface.StringAndQueue;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping(path="/exercisessearch")
public class ExercisesController implements QuickMap, BackendLogin {
    static String id = null;
    @Autowired
    private UserDao userDao;

    @GetMapping(path="")
    public @ResponseBody JSONObject ExercisesSearch (HttpServletRequest req, @RequestParam String uriName) {
        String name = (String) req.getAttribute("userName");
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname(name).get(0);
        user.setExerciseSHistory(StringAndQueue.appendFromString(uriName, user.getExerciseSHistory()));
        userDao.save(user);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName={uriName}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("uriName", uriName, "id", id));
        returnValue.put("status", true);
        returnValue.put("data", response.get("data"));
        return returnValue;
    }
    @GetMapping(path="/history")
    public @ResponseBody JSONObject SearchHistory (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname((String) req.getAttribute("userName")).get(0);
        returnValue.put("status", true);
        returnValue.put("data", StringAndQueue.getArrFromString(user.getExerciseSHistory()));
        return returnValue;
    }
}
