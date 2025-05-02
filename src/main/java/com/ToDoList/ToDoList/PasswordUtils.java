package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordUtils(PasswordEncoder passwordEncoder){this.passwordEncoder = passwordEncoder;}

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean passwordMatches(String rawPassword,String hashedPassword){
        return passwordEncoder.matches(rawPassword,hashedPassword);
    }

     public boolean passwordValidator(String password){
        char[] passwordArray = password.toCharArray();
        if(password.length()<8) {
            return false;
        }else{
            boolean upperCase = false;
            boolean number = false;
            for(char letter:passwordArray){
                if(Character.isUpperCase(letter)){
                    upperCase=true;
                }
                if(Character.isDigit(letter)){
                    number=true;
                }
            }
            return upperCase&&number;
        }
     }
}
