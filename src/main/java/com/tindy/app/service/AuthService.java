package com.tindy.app.service;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.LoginRespone;
import com.tindy.app.dto.respone.UserRespone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginRespone login(String phone, String password);
    UserRespone register(UserRequest userRequest);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
