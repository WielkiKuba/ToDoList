package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository){this.taskRepository = taskRepository;}

    public List<Task> findAll(){return taskRepository.findAll();}

    public Optional<Task> findById(Long id){return taskRepository.findById(id);}

    public Optional<Task> findByTitle(String title){return taskRepository.findByTitle(title);}

    public List<Task> findByOwner(User user){return taskRepository.findByOwner(user);}

    public List<Task> findByDone(Boolean done){return taskRepository.findByDone(done);}

    public List<Task> findByUserDone(User user,Boolean done){return taskRepository.findByOwnerAndDone(user,done);}

    public void addTask(Task task){taskRepository.save(task);}

    public void addTask(User owner,String title,String description){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setOwner(owner);
        task.setDone(false);
        addTask(task);
    }

    public void modifyTask(Task task,String title,String description,Boolean done,User owner){
        if(title!=null){
            task.setTitle(title);
        }
        if(description!=null){
            task.setDescription(description);
        }
        if(done!=null){
            task.setDone(done);
        }
        if(owner!=null){
            task.setOwner(owner);
        }
        taskRepository.save(task);
    }
    public void deleteTask(Task task){
        taskRepository.delete(task);
    }
}
