package com.tindy.app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindy.app.dto.request.UserRequest;

import com.tindy.app.exceptions.ForbiddenException;
import com.tindy.app.model.entity.User;
import com.tindy.app.service.AuthService;
import com.tindy.app.service.UserService;
import com.tindy.app.service.impl.UploadService;
import com.tindy.app.utils.JWTTokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(authService.getUser());
    }

    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/register").toUriString());
        try {
            return ResponseEntity.created(uri).body(authService.register(userRequest));
        }catch (Exception e){
            Map<String,String> error = new HashMap<>();
            error.put("error_message",e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @CrossOrigin("http://127.0.0.1:5173")
    @PostMapping(value = "/refresh_token", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public void refreshToken(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> body) throws IOException {
        String refreshTokenFromRequest = body.get("refreshToken").toString();

        if (refreshTokenFromRequest == null)
            throw new ForbiddenException("Can not get your refresh Token");

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshTokenFromRequest);

        Integer userId = decodedJWT.getClaim("userId").asInt();
        Integer tokenVersion = decodedJWT.getClaim("tokenVersion").asInt();

        User existingUser = authService.getUserById(userId);

        if (!Objects.equals(existingUser.getTokenVersion(), tokenVersion))
            throw new ForbiddenException("Your token Version is invalid");

        JWTTokenCreator tokenCreator = new JWTTokenCreator(existingUser);
        String newAccessToken = tokenCreator.createToken(JWTTokenCreator.TokenType.ACCESS_TOKEN);

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("user", existingUser);
        tokens.put("accessToken", newAccessToken);
        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
    }
    @GetMapping("/exist")
    public ResponseEntity<?> checkPhoneExist(@RequestParam String phone){
        if(authService.checkPhoneExist(phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone is exist");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("Phone is not exist");
        }
    }
    @PostMapping("/forgot/password/{phone}")
    public ResponseEntity<?> changeForgetPassword(@PathVariable String phone){
        if(userService.changeForgetPassword(phone)){
            return ResponseEntity.ok().body("Password is changed!");
        }
        return ResponseEntity.badRequest().body("Something is wrong!");
    }
}
