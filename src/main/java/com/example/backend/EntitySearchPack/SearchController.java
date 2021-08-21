package com.example.backend.EntitySearchPack;
import com.example.backend.PersonalFuncs.QuickMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.IOException;

@Controller
@RequestMapping(path="/search")
public class SearchController implements QuickMap {
    @GetMapping(path="/entity") // Map ONLY POST Requests
    public @ResponseBody String mySearch (@RequestParam String course
            , @RequestParam String searchKey, @RequestParam String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course={course}&searchKey={searchKey}&id={id}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        String response = restTemplate.getForObject(url, String.class, QuickMap.createMap("course", course, "searchKey", searchKey, "id", id));
        return response;
    }
}
