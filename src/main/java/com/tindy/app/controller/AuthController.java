package com.tindy.app.controller;

import com.tindy.app.dto.request.LoginRequest;
import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.LoginRespone;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody  LoginRequest loginRequest){
        LoginRespone loginResponse = authService.login(loginRequest.getPhone(), loginRequest.getPassword());
        return ResponseEntity.accepted().body(loginResponse);
    }

    public ResponseEntity<Object> register(@RequestBody UserRequest userRequest){
        UserRespone userRespone = authService.register(userRequest);
        return ResponseEntity.ok().body(userRespone);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        authService.logout(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok("Logout user successfully!");
    }
}
