package com.example.backend.GetInstanceByUriPack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.JwtUtils.PassToken;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

// 这个接口大概率不直接使用
@Controller
@RequestMapping(path="/getInstanceByUri")
public class GetInstanceByUri implements GetCourseName {
    static String id = null;
    @GetMapping(path="")
    public @ResponseBody JSONObject GetInstance (HttpServletRequest req, @RequestParam String uri) {
        JSONObject returnValue = new JSONObject();
        JSONObject data = GetRawData(uri);
        returnValue.put("status", true);
        returnValue.put("data", data);
        return returnValue;
    }
    @PassToken
    public static JSONObject GetRawData(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String course = GetCourseName.GetCourseNameInUri(uri);
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/getKnowledgeCard";
        if (id == null) id = BackendLogin.getOpeneduID();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("course", course, "uri", uri, "id", id), headers);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
        JSONObject data = JSON.parseObject(response.getBody().toString());
        try {
            return data.getJSONObject("data");
        } catch (Exception e) {
            return data;
        }
    }
}
