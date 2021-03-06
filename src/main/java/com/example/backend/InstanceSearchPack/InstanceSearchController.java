package com.example.backend.InstanceSearchPack;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(path="/instanceList")
public class InstanceSearchController implements QuickMap, BackendLogin {
    static String id = null;
    @Autowired
    private UserDao userDao;

    @GetMapping(path="")
    public @ResponseBody JSONObject EntitySearch (HttpServletRequest req, @RequestParam String course
            , @RequestParam String searchKey, @RequestParam Integer sort, @RequestParam String dropstring) {
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
            JSONArray data = response.getJSONArray("data");
            switch (sort) {
                case 0:
                    returnValue.put("data", sortByLength(data, 1));
                    break;
                case 1:
                    returnValue.put("data", sortByLength(data, 0));
                    break;
                case 2:
                    returnValue.put("data", sortIfContains(data, dropstring));
                    break;
                default:
                    returnValue.put("data", "sort?????????1-3??????");
            }
            returnValue.put("status", true);
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

    public JSONArray sortByLength (JSONArray originArray, Integer num) throws Exception {
        List<JSONObject> list = JSONObject.parseArray(originArray.toJSONString(), JSONObject.class);
        if (num == 1) Collections.sort(list, (o1, o2) -> o1.getString("label").length()-o2.getString("label").length());
        else Collections.sort(list, (o1, o2) -> o2.getString("label").length()-o1.getString("label").length());
        for (JSONObject i : list) {
            System.out.println(i);
        }
        return JSONArray.parseArray(JSONArray.toJSONString(list));
    }

    public JSONArray sortIfContains (JSONArray originArray, String dropString) throws Exception {
        JSONArray returnValue = new JSONArray();
        for (int i=0; i<originArray.size(); i++) {
            if (!originArray.getJSONObject(i).getString("label").contains(dropString))
            returnValue.add(originArray.getJSONObject(i));
        }
        return returnValue;
    }
}
