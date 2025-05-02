package com.ToDoList.ToDoList;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "done",nullable = false)
    private Boolean done = false;
    @ManyToOne
    @JoinColumn(name = "owner",nullable = false)
    private User owner;

    public Long getId(){return id;}
    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public Boolean getDone(){return done;}
    public User getOwner(){return owner;}

    public void setId(Long id){this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setDone(Boolean done){this.done = done;}
    public void setOwner(User owner){this.owner = owner;}
}
