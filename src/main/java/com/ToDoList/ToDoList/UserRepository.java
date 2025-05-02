package com.ToDoList.ToDoList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//    List<User> findById(long id);
    Optional<User> findByLogin(String login);
}
