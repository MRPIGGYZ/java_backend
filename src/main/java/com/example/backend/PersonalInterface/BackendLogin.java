package com.example.backend.PersonalInterface;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

public interface BackendLogin {
    static String getOpeneduID () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeAuth/user/login";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(QuickMap.createMap("password", "zhe4gemima", "phone", "18839551859"), headers);
        ResponseEntity response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, HashMap.class);
        } catch (Exception e) {
            return "OpenEdu又挂了！";
        }
        HashMap<String, String> data = (HashMap<String, String>) response.getBody();
        return data.get("id");
    }
}
