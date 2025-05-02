package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final PasswordUtils passwordUtils;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final SecurityVar securityVar;

    @Autowired
    public UserRestController(UserService userService,JwtUtils jwtUtils, PasswordUtils passwordUtils,SecurityVar securityVar){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordUtils = passwordUtils;
        this.securityVar = securityVar;
    }

    @PostMapping("/modify/login")
    public HttpStatus changeLogin(@RequestHeader("Authorization") String header,@RequestBody UserBasicParam userBasicParam){
        userService.modifyLogin(userService.userFromToken(header.replace("bearer ","")),userBasicParam.getLogin());
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/modify/password")
    public HttpStatus changePassword(@RequestHeader("Authorization") String header,@RequestBody UserBasicParam userBasicParam){
        userService.modifyPassword(userService.userFromToken(header.replace("bearer ","")),userBasicParam.getPassword());
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/")
    public void createUser(@RequestBody UserBasicParam userBasicParam){
        try{
            userService.createUser(userBasicParam.getLogin(),userBasicParam.getPassword());
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid login or password");
        }
    }

    @GetMapping("/")
    public List<User> getUser(@RequestHeader("X-API-KEY") String apiKey){
        if(securityVar.validApiKey(apiKey)){
            return userService.findAll();
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @GetMapping("/admin/search/login/{login}")
    public Optional<User> getUser(@RequestHeader("X-API-KEY") String apiKey,@PathVariable String login){
        if(securityVar.validApiKey(apiKey)){
            return userService.findByLogin(login);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @GetMapping("/admin/search/id/{id}")
    public Optional<User> getUser(@RequestHeader("X-API-KEY") String apiKey,@PathVariable Long id){
        if(securityVar.validApiKey(apiKey)){
            return userService.findById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Invalid API key");
        }
    }

    @PostMapping("/auth")
    public String login(@RequestBody UserBasicParam userBasicParam){
        User user = userService.findByLogin(userBasicParam.getLogin()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Login not found"));
        String realPassword = user.getPassword();
        if(passwordUtils.passwordMatches(userBasicParam.getPassword(),realPassword)){
            return jwtUtils.generateToken(user.getId());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }
    }
}
