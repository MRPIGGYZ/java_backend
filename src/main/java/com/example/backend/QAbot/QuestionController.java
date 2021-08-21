package com.example.backend.QAbot;

import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Controller
@RequestMapping(path="")
public class QuestionController implements QuickMap, BackendLogin {
    static String id = null;

    @GetMapping(path="/clientquestion")
    public @ResponseBody String mySearch (@RequestParam String course
            , @RequestParam String inputQuestion) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion";
        HttpHeaders headers = new HttpHeaders();
        if (id == null) id = BackendLogin.getOpeneduID();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("course", course, "inputQuestion", inputQuestion, "id", id), headers);
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, HashMap.class);
            if (response.getStatusCodeValue()!=200) return "发生未知错误，请稍后重试";
        } catch (Exception e) {
            return "发生未知错误，请稍后重试";
        }
        return response.getBody().toString();
    }
}
