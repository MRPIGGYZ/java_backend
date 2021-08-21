package com.example.backend.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity // This tells Hibernate to make a table out of this class
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
        this.password = password; // 之后会增加密码加密，暂时直接存储
        this.email = email;
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
    @Override
    public String toString() {
        return "Users [userid=" + id + ", username=" + name + ", email=" + email + "]";
    }
}
