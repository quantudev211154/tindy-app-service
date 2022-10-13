package com.tindy.app.service;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.model.entity.User;

import java.util.List;

public interface AuthService {

    UserRespone register(UserRequest user);
    User getUser(String userName);
    List<User> getUser();




}
