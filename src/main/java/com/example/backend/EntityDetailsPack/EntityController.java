package com.example.backend.EntityDetailsPack;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

@Controller
@RequestMapping(path="/entity")
public class EntityController implements QuickMap, BackendLogin {
    static String id = null;
    @GetMapping(path="/course")
    public @ResponseBody HashMap EntityDetails (@RequestParam String course
            , @RequestParam String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?course={course}&name={name}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        HashMap response = restTemplate.getForObject(url, HashMap.class, QuickMap.createMap("course", course, "name", name, "id", id));
        HashMap data = (HashMap) response.get("data");
        return data;
    }
    @GetMapping(path="/ncourse")
    public @ResponseBody HashMap EntityDetails (@RequestParam String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/infoByInstanceName?name={name}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        HashMap response = restTemplate.getForObject(url, HashMap.class, QuickMap.createMap("name", name, "id", id));
        HashMap data = (HashMap) response.get("data");
        return data;
    }
}
