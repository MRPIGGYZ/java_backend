package com.example.backend.LinkInstancePack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

@Controller
@RequestMapping(path="/linkInstance")
public class linkInstanceController {
    static String id = null;
    @GetMapping(path="")
    public @ResponseBody
    JSONObject linkInstance (HttpServletRequest req, @RequestParam String context
            , @RequestParam String course) {
        JSONObject returnValue = new JSONObject();
        try {
            JSONArray data = getLinkedInfo(context, course);
            returnValue.put("status", true);
            returnValue.put("data", data);
        } catch (Exception e) {
            returnValue.put("status", false);
            returnValue.put("data", "openedu break down");
        }
        return returnValue;
    }
    @PassToken
    public static JSONArray getLinkedInfo (String context, String course) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/linkInstance";
        HttpHeaders headers = new HttpHeaders();
        if (id == null) id = BackendLogin.getOpeneduID();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("context", context, "course", course, "id", id), headers);
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
            JSONObject data = (JSONObject) JSON.parseObject(response.getBody().toString()).get("data");
            return data.getJSONArray("results");
        } catch (Exception e) {
            throw e;
        }
    }
}