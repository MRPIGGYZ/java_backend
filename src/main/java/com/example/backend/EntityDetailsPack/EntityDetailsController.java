package com.example.backend.EntityDetailsPack;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.example.backend.GetInstanceByUriPack.GetInstanceByUri;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path="/infoByInstanceName")
public class EntityDetailsController implements SimplifyData {
    static String id = null;
    @Autowired
    private UserDao userDao;
    @GetMapping(path="")
    public @ResponseBody JSONObject EntityDetails (HttpServletRequest req, @RequestParam String course
            , @RequestParam String name, @RequestParam String uri) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject returnValue = new JSONObject();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course={course}&name={name}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("course", course, "name", name, "id", id));
        try {
            JSONObject gooddata = GetInstanceByUri.GetRawData(uri);
            JSONObject data = (JSONObject) JSON.toJSON(response.get("data"));
//            System.out.println(gooddata);
            data = Simplify(data, gooddata);
            System.out.println(data);
            String username = (String) req.getAttribute("userName");
            User user = userDao.getUserByname(username).get(0);
            String history = user.getEntityHistory();
            if (history == null || history.equals("")) {
                history = course + "%%" + name + "%%" + data.get("entity_type") + "%%" + uri;
            } else {
                history = course + "%%" + name + "%%" + data.get("entity_type") + "%%" + uri + "##" + history;
            }
            user.setEntityHistory(history);
            userDao.save(user);

            returnValue.put("status", true);
            returnValue.put("data", data);
        } catch (Exception e) {
            returnValue.put("status", false);
            returnValue.put("data", "openedu break down");
        }
        return returnValue;
    }

}
