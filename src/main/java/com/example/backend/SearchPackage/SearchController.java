package com.example.backend.SearchPackage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path="")
public class SearchController {

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    @GetMapping(path="/search") // Map ONLY POST Requests
    public @ResponseBody String mySearch (@RequestParam String course
            , @RequestParam String searchKey, @RequestParam String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/instanceList?course={course}&searchKey={searchKey}&id={id}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        Map<String, String> tmp = new HashMap<String, String>();
        tmp.put("course", course);
        tmp.put("searchKey", searchKey);
        tmp.put("id", id);
        String response = restTemplate.getForObject(url, String.class, tmp);
        return response;
    }
//    @PostMapping(path="/login")
//    public @ResponseBody String userLogin (@RequestParam String name
//            , @RequestParam String password) {
//
//    }
//    @GetMapping(path="/all")
//    public @ResponseBody Iterable<User> getAllUsers() {
//        return userDao.findAll();
//    }
//    @GetMapping(path="/findbyid")
//    public @ResponseBody Iterable<User> getUsersbyId(@RequestParam Integer id) {
//        List<User> list = userDao.getUserById(id);
//        return list;
//    }
}
