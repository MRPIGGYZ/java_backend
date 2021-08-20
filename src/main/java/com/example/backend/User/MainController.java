package com.example.backend.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/user")
public class MainController {
    @Autowired
    private UserDao userDao;

    @PostMapping(path="/register") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String password, @RequestParam String email) {
        List<User> list = userDao.getUserByname(name);
        if (list.isEmpty()) {
            User n = new User();
            n.init(name, password, email);
            userDao.save(n);
            return "注册成功";
        } else {
            return "用户名已存在";
        }
    }
    @DeleteMapping(path="/deleteuser")
    public @ResponseBody String deleteUser (@RequestParam String name) {
        if (!userDao.getUserByname(name).isEmpty()) {
            userDao.delete(userDao.getUserByname(name).get(0));
            return "删除成功";
        } else {
            return "无此用户";
        }
    }
    @PostMapping(path="/login")
    public @ResponseBody String userLogin (@RequestParam String name
            , @RequestParam String password) {
        List<User> list = userDao.getUserByname(name);
        if (list.isEmpty()) {
            return "未找到用户名";
        } else if (!list.get(0).getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "登录成功";
        }

    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }
    @GetMapping(path="/findbyid")
    public @ResponseBody Iterable<User> getUsersbyId(@RequestParam Integer id) {
        List<User> list = userDao.getUserById(id);
        return list;
    }
    @GetMapping(path="/findbyemail")
    public @ResponseBody Iterable<User> getUsersbyEmail(@RequestParam String email) {
        List<User> list = userDao.getUserByemail(email);
        return list;
    }
    @GetMapping(path="/findbypassword")
    public @ResponseBody Iterable<User> getUsersbyPassword(@RequestParam String password) {
        List<User> list = userDao.getUserBypassword(password);
        return list;
    }
}