package com.example.backend.QuestionSearchPack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
@RequestMapping(path="/questionListByUriName")
public class QuestionSearchController implements QuickMap, BackendLogin, QuestionFilter {
    static String id = null;
    @Autowired
    private UserDao userDao;

    @GetMapping(path="")
    public @ResponseBody JSONObject ExercisesSearch (HttpServletRequest req, @RequestParam String uriName) {
        String name = (String) req.getAttribute("userName");
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname(name).get(0);
        user.setQuestionSearchHistory(StringAndQueue.appendFromString(uriName, user.getQuestionSearchHistory()));
        userDao.save(user);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName={uriName}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("uriName", uriName, "id", id));
        JSONArray ja = (JSONArray) JSON.toJSON(response.get("data"));
        JSONArray afterDrop = DropNChoice(ja);
        returnValue.put("status", true);
        returnValue.put("data", afterDrop);
        return returnValue;
    }
    @GetMapping(path="/history")
    public @ResponseBody JSONObject SearchHistory (HttpServletRequest req) {
        JSONObject returnValue = new JSONObject();
        User user = userDao.getUserByname((String) req.getAttribute("userName")).get(0);
        returnValue.put("status", true);
        returnValue.put("data", StringAndQueue.getArrFromString(user.getQuestionSearchHistory()));
        return returnValue;
    }
    public static JSONObject GetSperQuestion (String searchkey, String ID) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName={uriName}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("uriName", searchkey, "id", id));
        JSONArray ja = (JSONArray) JSON.toJSON(response.get("data"));
        System.out.println("received");
        for (int i=0; i<ja.size(); i++) {
            JSONObject thisone = (JSONObject) ja.get(i);
            if (thisone.get("id").toString().equals(ID)) {
                try {
                    return QuestionFilter.QuestDivision(thisone);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }
}
