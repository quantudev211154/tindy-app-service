package com.tindy.app.service;

import com.tindy.app.dto.respone.UserRespone;

public interface UserService {

    UserRespone getUserInfo(String phone);
}
