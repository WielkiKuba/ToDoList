package com.ToDoList.ToDoList;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login",nullable = false)
    private String login;

    @Column(name = "password",nullable = false)
    private String password;

    public Long getId(){return id;}
    public String getLogin(){return login;}
    public String getPassword(){return password;}

    public void setId(Long id){this.id = id;}
    public void setLogin(String login){this.login = login;}
    public void setPassword(String password){this.password = password;}

}
