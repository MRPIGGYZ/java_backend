package com.example.backend.PassWordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class PasswordEncoder{
    public static String encode(String rawPassword) {
        System.out.println(rawPassword);
        System.out.println(DigestUtils.md5DigestAsHex(rawPassword.getBytes()));
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes());
    }
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.getBytes()));
    }
}