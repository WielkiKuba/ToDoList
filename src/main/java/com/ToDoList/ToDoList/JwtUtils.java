package com.ToDoList.ToDoList;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Component
public class JwtUtils {

    private final SecurityVar securityVar;
    private final String SECRET_KEY;
    private final long EXPIRATION_TIME = 1000*60*60;;

    @Autowired
    public JwtUtils(SecurityVar securityVar){this.securityVar = securityVar;this.SECRET_KEY=securityVar.getJwtSecret();}

    public String generateToken(Long userId){
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
//                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

    }
    public Claims validateToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token");
        }
    }
    public Long tokenUserId(String token){
        try{
            Claims claims =  validateToken(token);
            return Long.parseLong(claims.getSubject());
        }catch (JwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid token");
        }
    }
}
