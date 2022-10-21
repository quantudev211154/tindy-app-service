package com.tindy.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tindy.app.model.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class JWTTokenCreator {
    public enum TokenType {
        ACCESS_TOKEN, REFRESH_TOKEN
    }
    private User user = null;

    public JWTTokenCreator(User user) {
        this.user = user;
    }

    public String createToken(TokenType tokenType){
        Collection<String> rolesOfUser = new ArrayList<>();
        rolesOfUser.add(user.getRole().name());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String returnToken = null;

        switch (tokenType){
            case ACCESS_TOKEN: {
                returnToken = JWT.create()
                        .withClaim("userId", user.getId())
                        .withClaim("name", user.getFullName())
                        .withClaim("phone", user.getPhone())
                        .withIssuedAt(new Date(System.currentTimeMillis()))
                        .withExpiresAt(new Date(System.currentTimeMillis() +100*60*1000)) //changed it to make lifetime of access token is longer
                        .withClaim("roles", new ArrayList<>(rolesOfUser))
                        .sign(algorithm);
                break;
            }

            case REFRESH_TOKEN: {
                returnToken = JWT.create()
                        .withClaim("userId", user.getId())
                        .withClaim("name", user.getFullName())
                        .withClaim("phone", user.getPhone())
                        .withClaim("tokenVersion", user.getTokenVersion())
                        .withClaim("roles", new ArrayList<>(rolesOfUser))
                        .withIssuedAt(new Date(System.currentTimeMillis()))
                        .withExpiresAt(new Date(System.currentTimeMillis() +3000*60*1000))
                        .sign(algorithm);
                break;
            }
        }
        
        return returnToken;
    }
}
