package com.example.backend.EntityDetailsPack;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.example.backend.GetInstanceByUriPack.GetCourseName;
import com.example.backend.GetInstanceByUriPack.GetInstanceByUri;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.PersonalInterface.StringSplit;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/infoByInstanceName")
public class EntityDetailsController implements SimplifyData {
    static String id = null;
    @Autowired
    private UserDao userDao;
    @GetMapping(path="")
    public @ResponseBody JSONObject DirectForEntityDetails (HttpServletRequest req, @RequestParam String course
            , @RequestParam String label, @RequestParam String uri, @RequestParam String category) { //如果没有course或category，请在相应位置传入空字符串。
        RestTemplate restTemplate = new RestTemplate();
        JSONObject returnValue = new JSONObject();
        if (course.equals("")) course = GetCourseName.GetCourseNameInUri(uri);
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course={course}&name={name}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("course", course, "name", label, "id", id));
        try {
            JSONObject gooddata = GetInstanceByUri.GetRawData(uri);
            JSONObject data = (JSONObject) JSON.toJSON(response.get("data"));
            data = Simplify(data, gooddata);
            if (category.equals("")) {
                category = getCategory(data.getJSONArray("property"), label);
            }
            data.put("category", category);
            data.put("course", course);
            data.put("uri", uri);

            String username = (String) req.getAttribute("userName");
            User user = userDao.getUserByname(username).get(0);
            String history = StringSplit.UpdateEntityHistory(user.getEntityHistory(), course, label, category, uri);
            user.setEntityHistory(history);
            userDao.save(user);
            if (user.getEntityCollection() == null || !user.getEntityCollection().contains(uri)) {
                returnValue.put("star", "0");
            } else {
                returnValue.put("star", "1");
            }
            returnValue.put("status", true);
            returnValue.put("data", data);
        } catch (Exception e) {
            returnValue.put("status", false);
            returnValue.put("data", "openedu break down");
        }
        return returnValue;
    }

    private String getCategory (JSONArray property, String label) {
        for (int i=0; i<property.size(); i++) {
            JSONObject data = property.getJSONObject(i);
            if (data.getString("feature_key").equals("分类")) {
                return data.getString("feature_value");
            }
        }
        return label;
    }
}
