package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository,PasswordUtils passwordUtils,JwtUtils jwtUtils){
        this.userRepository=userRepository;
        this.passwordUtils = passwordUtils;
        this.jwtUtils = jwtUtils;
    }

    public List<User> findAll(){return userRepository.findAll();}

    public Optional<User> findById(Long id){return userRepository.findById(id);}

    public Optional<User> findByLogin(String login){return userRepository.findByLogin(login);}

    public void modifyLogin(User user,String login){
        user.setLogin(login);userRepository.save(user);
    }

    public void modifyPassword(User user,String password){
        user.setPassword(passwordUtils.hashPassword(password));userRepository.save(user);
    }

    public User userFromToken(String token){
        return findById(jwtUtils.tokenUserId(token)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
    }

    public void createUser(String login,String password){
        boolean next = true;
        if(login==null){
            next = false;
            throw new IllegalArgumentException("Login cannot be empty");
        }
        if(!(passwordUtils.passwordValidator(password))){
            next = false;
            throw new IllegalArgumentException("Password not valid basic param");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordUtils.hashPassword(password));
        userRepository.save(user);
    }
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
