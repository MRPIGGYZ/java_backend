package com.example.backend.EntitySearchPack;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(path="/search")
public class SearchController implements QuickMap {
    @GetMapping(path="/entity") // Map ONLY POST Requests
    public @ResponseBody ArrayList mySearch (@RequestParam String course
            , @RequestParam String searchKey, @RequestParam String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course={course}&searchKey={searchKey}&id={id}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        HashMap response = restTemplate.getForObject(url, HashMap.class, QuickMap.createMap("course", course, "searchKey", searchKey, "id", id));
        ArrayList data = (ArrayList) response.get("data");
        return data;
    }
}
