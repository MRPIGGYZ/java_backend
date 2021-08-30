package com.example.backend.User;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.JwtUtils.JwtUtils;
import com.example.backend.JwtUtils.PassToken;
import com.example.backend.PassWordEncoder.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path="/user")
public class MainController {
    @Autowired
    private UserDao userDao;
    public UserDao getUserList () {
        return userDao;
    }
    @PassToken
    @PostMapping(path="/register")
    public @ResponseBody JSONObject addNewUser (@RequestParam String name
            , @RequestParam String password, @RequestParam String email) {
        List<User> list = userDao.getUserByname(name);
        JSONObject returnValue = new JSONObject();
        if (list.isEmpty()) {
            User n = new User();
            password = PasswordEncoder.encode(password);
            n.init(name, password, email);
            userDao.save(n);
            returnValue.put("status", true);
            returnValue.put("data", "注册成功");
        } else {
            returnValue.put("status", false);
            returnValue.put("data", "用户名已存在");
        }
        return returnValue;
    }
    @PostMapping(path="/editpassword")
    public @ResponseBody JSONObject editInfo (HttpServletRequest req, @RequestParam String oldpassword
            , @RequestParam String newpassword) {
        System.out.println("未登录");
        JSONObject returnValue = new JSONObject();
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        if (!PasswordEncoder.matches(oldpassword, user.getPassword())) {
            returnValue.put("status", false);
            returnValue.put("data", "密码错误");
        } else {
            user.setPassword(PasswordEncoder.encode(newpassword));
            userDao.save(user);
            returnValue.put("status", true);
            returnValue.put("data", "修改成功");
        }
        return returnValue;
    }
    @DeleteMapping(path="/deleteuser")
    public @ResponseBody JSONObject deleteUser (HttpServletRequest req, @RequestParam String password) {
        String name = (String) req.getAttribute("userName");
        User user = userDao.getUserByname(name).get(0);
        JSONObject returnValue = new JSONObject();
        if (PasswordEncoder.matches(password, user.getPassword())) {
            userDao.delete(user);
            returnValue.put("status", true);
            returnValue.put("data", "删除成功");
        } else {
            returnValue.put("status", false);
            returnValue.put("data", "密码错误");
        }
        return returnValue;
    }
    @PassToken
    @PostMapping(path="/login")
    public @ResponseBody JSONObject userLogin (@RequestParam String name
            , @RequestParam String password) {
        List<User> list = userDao.getUserByname(name);
        JSONObject returnValue = new JSONObject();
        if (list.isEmpty()) {
            returnValue.put("status", false);
            returnValue.put("data", "未找到用户");
        } else {
            User user = list.get(0);
            if (!PasswordEncoder.matches(password, user.getPassword())) {
                returnValue.put("status", false);
                returnValue.put("data", "密码错误");
            } else {
                String token = JwtUtils.createToken(user.getId().toString(), user.getName(), user.getPassword());
                user.setToken(token);
                returnValue.put("status", true);
                returnValue.put("data", "登录成功");
                returnValue.put("token", token);
            }
        }
        return returnValue;
    }
    @PassToken
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }
    @PassToken
    @GetMapping(path="/findbyid")
    public @ResponseBody Iterable<User> getUsersbyId(@RequestParam Integer id) {
        return userDao.getUserById(id);
    }
    @PassToken
    @GetMapping(path="/findbyemail")
    public @ResponseBody Iterable<User> getUsersbyEmail(@RequestParam String email) {
        return userDao.getUserByemail(email);
    }
    @PassToken
    @GetMapping(path="/findbypassword")
    public @ResponseBody Iterable<User> getUsersbyPassword(@RequestParam String password) {
        return userDao.getUserBypassword(password);
    }
}