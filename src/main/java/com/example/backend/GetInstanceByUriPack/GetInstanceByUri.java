package com.example.backend.GetInstanceByUriPack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.FaultsCollectionsHandler.FCController;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import com.example.backend.User.User;
import com.example.backend.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path="/getInstanceByUri")
public class GetInstanceByUri implements GetCourseName {
    static String id = null;
    @GetMapping(path="")
    public @ResponseBody JSONObject GetInstance (HttpServletRequest req, @RequestParam String uri) {
        JSONObject returnValue = new JSONObject();
        String course = GetCourseNameInUri(uri);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/getKnowledgeCard";
        if (id == null) id = BackendLogin.getOpeneduID();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("course", course, "uri", uri, "id", id), headers);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
        JSONObject data = JSON.parseObject(response.getBody().toString());
        returnValue.put("status", true);
        try {
            returnValue.put("data", data.get("data"));
        } catch (Exception e) {
            returnValue.put("data", response.getBody().toString());
            returnValue.put("error", "EduApiError");
        }
        return returnValue;
    }
}
