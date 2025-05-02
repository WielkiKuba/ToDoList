package com.ToDoList.ToDoList;

public class TaskBasicParam {
    private String title;
    private String description;
    private Long ownerId;
    private Boolean done;

    public String getTitle(){return title;}
    public String getDescription(){return description;}
    public Long getOwnerId(){return ownerId;}
    public Boolean getDone(){return done;}

    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setOwnerId(Long ownerId){this.ownerId = ownerId;}
    public void setDone(Boolean done){this.done = done;}
}
