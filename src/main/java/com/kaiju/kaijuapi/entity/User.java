//package com.kaiju.kaijuapi.entity;
//
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String email;
//    private String username;
//    private String password;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public List<Task> getTaskList() {
//        return taskList;
//    }
//
//    public void setTaskList(List<Task> taskList) {
//        this.taskList = taskList;
//    }
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Task> taskList = new ArrayList<>();
//}