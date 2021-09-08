package com.example.backend.User;

import javax.persistence.*;
import java.io.Serializable;

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
    @Column(name="questionSearchHistory")
    private String questionSearchHistory;
    @Column(name="questionFaults", columnDefinition = "TEXT")
    private String questionFaults;
    @Column(name="questionCollection", columnDefinition = "TEXT")
    private String questionCollection;
    @Column(name="questionHistory", columnDefinition = "TEXT")
    private String questionHistory;
    @Column(name="entitySearchHistory")
    private String entitySearchHistory;
    @Column(name="entityCollection", columnDefinition = "TEXT")
    private String entityCollection;
    @Column(name="entityHistory", columnDefinition = "TEXT")
    private String entityHistory;
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
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestionSearchHistory () {
        return this.questionSearchHistory;
    }
    public void setQuestionSearchHistory (String questionSearchHistory) {
        this.questionSearchHistory = questionSearchHistory;
    }
    public String getQuestionCollection () {
        return this.questionCollection;
    }
    public void setQuestionCollection (String questionCollection) {
        this.questionCollection = questionCollection;
    }
    public String getQuestionFaults () {
        return this.questionFaults;
    }
    public void setQuestionFaults (String questionFaults) {
        this.questionFaults = questionFaults;
    }
    public String getQuestionHistory () {
        return this.questionHistory;
    }
    public void setQuestionHistory (String questionHistory) {
        this.questionHistory = questionHistory;
    }

    public String getEntitySearchHistory () {
        return this.entitySearchHistory;
    }
    public void setEntitySearchHistory (String entitySearchHistory) {
        this.entitySearchHistory = entitySearchHistory;
    }
    public String getEntityCollection () {
        return this.entityCollection;
    }
    public void setEntityCollection (String entityCollection) {
        this.entityCollection = entityCollection;
    }
    public String getEntityHistory () {
        return this.entityHistory;
    }
    public void setEntityHistory (String entityHistory) {
        this.entityHistory = entityHistory;
    }

    @Override //这个基本只有调试时使用
    public String toString() {
        return "Users [userid=" + id + ", username=" + name + ", email=" + email + "]";
    }
}
