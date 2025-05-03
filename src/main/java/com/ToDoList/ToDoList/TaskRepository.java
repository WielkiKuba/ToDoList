package com.ToDoList.ToDoList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
//    List<Task> findById(long id);
    Optional<Task> findByTitle(String title);
    List<Task> findByOwner(User owner);
    List<Task> findByDone(Boolean done);
    List<Task> findByOwnerAndDone(User user, Boolean done);
    List<Task> findByOwnerAndTitle(User user, String title);
}
