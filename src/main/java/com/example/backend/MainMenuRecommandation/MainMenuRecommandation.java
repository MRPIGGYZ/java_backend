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
//        entityList.addAll(getFakeData(course)); //由于数据可能不足，所以我们先加上一些假数据
        JSONArray returnArray = new JSONArray();
        try {
            returnArray = GetSubArray.getRandomArray(entityList, 25);
        } catch (Exception e) {
            returnArray = entityList;
        }
        returnArray = GetSubArray.balancing(returnArray, 10);
        returnValue.put("status", true);
        returnValue.put("data", returnArray);
        return returnValue;
    }

    private JSONArray getFakeData (String course) { //返回二十五个虚假的数据
        if (course.equals("chinese")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("math")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("english")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("physics")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("chemistry")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("biology")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("history")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("geo")) {
            return StringSplit.EntitySplit("");
        }
        if (course.equals("politics")) {
            return StringSplit.EntitySplit("");
        }
        return null;
    }

}
