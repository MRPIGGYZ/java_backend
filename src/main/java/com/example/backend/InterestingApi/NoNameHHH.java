package com.example.backend.InterestingApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.JwtUtils.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(path="/extraapi")
public class NoNameHHH {
    @GetMapping(path="")
    public @ResponseBody
    JSONObject getSomething (String index) {
        JSONObject returnValue = new JSONObject();
        RestTemplate restTemplate = new RestTemplate();
        String url = getURL(Integer.parseInt(index));
        JSONObject response = restTemplate.getForObject(url, JSONObject.class);
        try {
            JSONArray ja = response.getJSONArray("newslist");
            returnValue.put("status", true);
            returnValue.put("data", ja);
            return returnValue;
        } catch (Exception e) {
            returnValue.put("status", true);
            returnValue.put("data", "古人名言接口坏了");
            return returnValue;
        }
    }
    @PassToken
    private String getURL (Integer index) {
        switch (index) {
            case 0:
                return "http://api.tianapi.com/txapi/lzmy/index?key=1fd1627cad27c04ebb59994ff47b7797";
            case 1:
                return "http://api.tianapi.com/txapi/everyday/index?key=1fd1627cad27c04ebb59994ff47b7797";
            case 2:
                return "http://api.tianapi.com/txapi/pitlishi/index?key=1fd1627cad27c04ebb59994ff47b7797";
            case 3:
                return "http://api.tianapi.com/txapi/cityriddle/index?key=1fd1627cad27c04ebb59994ff47b7797";
            case 4:
                return "http://api.tianapi.com/world/index?key=1fd1627cad27c04ebb59994ff47b7797";
            case 5:
                return "http://api.tianapi.com/txapi/lishi/index?key=1fd1627cad27c04ebb59994ff47b7797";
            default:
                return "http://api.tianapi.com/txapi/lzmy/index?key=1fd1627cad27c04ebb59994ff47b7797";
        }
    }
}
