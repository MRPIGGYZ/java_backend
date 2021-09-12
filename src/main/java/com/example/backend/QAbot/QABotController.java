package com.example.backend.QAbot;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping(path="/clientquestion")
public class QABotController implements QuickMap, BackendLogin {
    static String id = null;

    @GetMapping(path="")
    public @ResponseBody JSONObject mySearch (@RequestParam String course
            , @RequestParam String inputQuestion) {
        RestTemplate restTemplate = new RestTemplate();
        JSONObject returnValue = new JSONObject();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/inputQuestion";
        HttpHeaders headers = new HttpHeaders();
        if (id == null) id = BackendLogin.getOpeneduID();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("course", course, "inputQuestion", inputQuestion, "id", id), headers);
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
            if (response.getStatusCodeValue()!=200) {
                returnValue.put("status", false);
                returnValue.put("data", "openedu break down!");
                return returnValue;
            }
            JSONObject data = JSON.parseObject(response.getBody().toString());
            JSONArray res = data.getJSONArray("data");
            returnValue.put("data", res);
            returnValue.put("status", true);
        } catch (Exception e) {
            returnValue.put("status", false);
            return returnValue;
        }
        return returnValue;
    }
}
