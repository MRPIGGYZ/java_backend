package com.example.backend.EntityDetailsPack;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(path="/infoByInstanceName")
public class EntityDetailsController implements QuickMap, BackendLogin {
    static String id = null;
    @GetMapping(path="")
    public @ResponseBody JSONObject EntityDetails (@RequestParam String course
            , @RequestParam String name) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject returnValue = new JSONObject();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course={course}&name={name}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        JSONObject response = restTemplate.getForObject(url, JSONObject.class, QuickMap.createMap("course", course, "name", name, "id", id));
        returnValue.put("status", true);
        returnValue.put("data", response.get("data"));
        return returnValue;
    }
}
