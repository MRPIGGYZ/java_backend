package com.example.backend.User;
import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="entitySHistory")
    private String entitySHistory;
    @Column(name="exerciseSHistory")
    private String exerciseSHistory;
    @Column(name="token")
    private String token;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void init(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override //这个基本只有调试时使用
    public String toString() {
        return "Users [userid=" + id + ", username=" + name + ", email=" + email + "]";
    }
}
