package com.example.backend.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
//    @Query("select u.id, u.name from User u where u.id = :id")
    List<User> getUserById(Integer id);
    List<User> getUserByemail(String email);
    List<User> getUserBypassword(String password);
    List<User> getUserByname(String name);

}
