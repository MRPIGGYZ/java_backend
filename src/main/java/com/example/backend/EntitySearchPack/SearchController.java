package com.example.backend.EntitySearchPack;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="/search")
public class SearchController implements QuickMap, BackendLogin {
    static String id = null;

    @GetMapping(path="/entity")
    public @ResponseBody ArrayList EntitySearch (@RequestHeader Map<String, String> headers, @RequestParam String course
            , @RequestParam String searchKey) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course={course}&searchKey={searchKey}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        HashMap response = restTemplate.getForObject(url, HashMap.class, QuickMap.createMap("course", course, "searchKey", searchKey, "id", id));
        ArrayList data = (ArrayList) response.get("data");
        return data;
    }
}
