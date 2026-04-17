package com.italo.catalogy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.italo.catalogy.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.jwt.secret}")
    private String secret;

    private Instant getExpiresAt(){return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String genereateToken(UserModel user){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try{
            return JWT.create()
                    .withIssuer("catalogly")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(this.getExpiresAt())
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String authenticateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try{
            return JWT.require(algorithm)
                    .withIssuer("catalogly")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
