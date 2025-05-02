package com.ToDoList.ToDoList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityVar {
    @Value("${admin.api.key}")
    private String apikey;

    @Value("${admin.api.jwt.secret}")
    private String jwtSecret;

    public String getApiKey(){return apikey;}
    public String getJwtSecret(){return jwtSecret;}

    public Boolean validApiKey(String rawApiKey){
        if(rawApiKey.equals(apikey)){
            return true;
        }else{
            return false;
        }
    }

}
