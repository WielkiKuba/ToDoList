package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    private final TaskService taskService;
    private final UserService userService;
    private final SecurityVar securityVar;

    @Autowired
    public TaskRestController(TaskService taskService,UserService userService,SecurityVar securityVar){
        this.taskService = taskService;
        this.userService = userService;
        this.securityVar=securityVar;
    }

    @GetMapping("/")
    public List<Task> getTask(@RequestHeader("X-API-KEY") String apiKey){
        if(securityVar.validApiKey(apiKey)){
            return taskService.findAll();
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @GetMapping("/admin/search/id/{id}")
    public Optional<Task> getTask(@RequestHeader("X-API-KEY") String apiKey,@PathVariable Long id){
        if(securityVar.validApiKey(apiKey)){
            return taskService.findById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @PostMapping("/admin/search/title")
    public Optional<Task> getTask(@RequestHeader("X-API-KEY") String apiKey, @RequestBody TaskBasicParam taskBasicParam){
        if(securityVar.validApiKey(apiKey)){
            return taskService.findByTitle(taskBasicParam.getTitle());
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @GetMapping("/search/user")
    public List<Task> getUserTask(@RequestHeader("Authorization") String header){
        return taskService.findByOwner(userService.userFromToken(header.replace("bearer ","")));
    }

    @GetMapping("/admin/search/status/{done}")
    public List<Task> adminGetTask(@RequestHeader("X-API-KEY") String apiKey, @PathVariable Boolean done){
        if(securityVar.validApiKey(apiKey)){
            return taskService.findByDone(done);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @GetMapping("/search/status/{done}")
    public List<Task> getTask(@RequestHeader("Authorization") String header, @PathVariable Boolean done){
        return taskService.findByUserDone(userService.userFromToken(header.replace("bearer ","")),done);
    }

    @PostMapping("/add")
    public void addTask(@RequestHeader("Authorization") String header,@RequestBody TaskBasicParam taskBasicParam){
        taskService.addTask(userService.userFromToken(header.replace("bearer ","")),taskBasicParam.getTitle(),taskBasicParam.getDescription());
    }

    @PostMapping("/modify/{taskId}")
    public void modifyTask(@RequestHeader("Authorization") String header,@PathVariable Long taskId,@RequestBody TaskBasicParam taskBasicParam){
        User owner = userService.userFromToken(header.replace("bearer ",""));
        Task task = taskService.findById(taskId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not found"));
        if(task.getOwner().getId().equals(owner.getId())){
            taskService.modifyTask(task,taskBasicParam.getTitle(),taskBasicParam.getDescription(),taskBasicParam.getDone(),owner);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Access denied");
        }
    }

    @GetMapping("/delete/{id}")
    public HttpStatus deleteTask(@RequestHeader("Authorization") String token,@PathVariable Long id){
        Task task = taskService.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not found"));
        User user = userService.userFromToken(token);
        if(task.getOwner().getId().equals(user.getId())){
            taskService.deleteTask(task);
            return HttpStatus.ACCEPTED;
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied");
        }
    }
}
