package com.example.backend.MainMenuRecommandation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.GetSubArray;
import com.example.backend.PersonalInterface.StringSplit;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/mainmenu")
public class MainMenuRecommandation {
    @Autowired
    private UserDao userDao;
    @GetMapping(path="/refresh")
    public @ResponseBody JSONObject getTenEntity (HttpServletRequest req, @RequestParam String course) {
        JSONObject returnValue = new JSONObject();
        List<User> alldata = userDao.findAll();
        JSONArray entityList = new JSONArray();
        String history = "";
        for (User user : alldata) {
            history = user.getEntityHistory();
            try {
                entityList.addAll(GetSubArray.getSperCourseArray(StringSplit.EntitySplit(history), course));
            } catch (Exception e) {
                continue;
            }
        }
        JSONArray returnArray = GetSubArray.getRandomArray(entityList, 12);
        returnValue.put("status", true);
        returnValue.put("data", GetSubArray.getRandomArray(returnArray, 6));
        return returnValue;
    }

    private JSONArray getFakeData (String course) { //返回一些假数据
        User user = userDao.getUserByname("data").get(0);
        return GetSubArray.getSperCourseArray(StringSplit.EntitySplit(user.getEntityHistory()), course);
    }

}
