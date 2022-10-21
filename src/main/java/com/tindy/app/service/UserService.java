package com.tindy.app.service;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserRespone getUserInfo(String phone);
    UserRespone uploadFile(MultipartFile multipartFile, Integer userId);
    UserRespone updateUser(UserRequest userRequest, Integer userId);
}
