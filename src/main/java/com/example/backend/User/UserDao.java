package com.example.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    List<User> getUserById(Integer id);
    List<User> getUserByemail(String email);
    List<User> getUserBypassword(String password);
    List<User> getUserByname(String name);
}
