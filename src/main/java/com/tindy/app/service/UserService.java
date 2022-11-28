package com.tindy.app.service;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserRespone getUserInfo(Integer id);
    UserRespone uploadFile(MultipartFile multipartFile, Integer userId);
    UserRespone updateUser(String fullName, Integer userId, MultipartFile multipartFile) throws IOException;
    UserRespone getUserInfoByPhone(String phone);
    Boolean changeForgetPassword(String phone);
    UserRespone changePassword(String phone, String oldPassword,String newPassword);
    Boolean checkPasswordModify(String password, String phone);
}
