package com.example.backend.ExercisesSearchPack;
import com.example.backend.PersonalInterface.BackendLogin;
import com.example.backend.PersonalInterface.QuickMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(path="/search")
public class ExercisesController implements QuickMap, BackendLogin {
    static String id = null;


    @GetMapping(path="/exercises")
    public @ResponseBody ArrayList ExercisesSearch (@RequestParam String uriName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://open.edukg.cn/opedukg/api/typeOpen/open/questionListByUriName?uriName={uriName}&id={id}";
        if (id == null) id = BackendLogin.getOpeneduID();
        HashMap response = restTemplate.getForObject(url, HashMap.class, QuickMap.createMap("uriName", uriName, "id", id));
        ArrayList data = (ArrayList) response.get("data");
//        for (int i=0; i<data.size(); i++) {
//            System.out.println(data.get(i));
//        }
        return data;
    }
}
