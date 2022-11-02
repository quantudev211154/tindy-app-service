package com.tindy.app.service.impl;

import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.User;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserRespone getUserInfo(Integer id) {
        return MapData.mapOne(userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User is not found!")),UserRespone.class);
    }

    @Override
    public UserRespone uploadFile(MultipartFile multipartFile, Integer userId) {
        try {
            String fileName = multipartFile.getOriginalFilename();
//            log.info("upload image:  File Name : {}", multipartFile.getOriginalFilename());
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(uploadService.getExtension(fileName));
            File file = uploadService.convertToFile(multipartFile,fileName);

            String url = uploadService.uploadFile(file,fileName);

            file.delete();

            User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("Not found user"));
            user.setAvatar(url);

            return MapData.mapOne(userRepository.save(user), UserRespone.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRespone updateUser(UserRequest userRequest, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("Not found"));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        log.info("HieuLog: "+userRequest.toString());

        if(userRequest.getEmail() != null){
            user.setEmail(userRequest.getEmail());
            log.info("HieuLog: "+userRequest.toString());

        }
        if(userRequest.getFullName() != null){
            user.setFullName(userRequest.getFullName());
            log.info(user.toString());

        }
        User userUpdated = userRepository.save(user);

        return MapData.mapOne(userUpdated, UserRespone.class);
    }

    @Override
    public UserRespone getUserInfoByPhone(String phone) {
        return MapData.mapOne(userRepository.findByPhone(phone).orElseThrow(()-> new UsernameNotFoundException("Not found user")), UserRespone.class);
    }

    @Override
    public Boolean changeForgetPassword(String phone) {
        try {
            User user = userRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not exist"));
            user.setPassword(passwordEncoder.encode(phone));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    @Override
    public UserRespone changePassword(String phone, String oldPassword,String newPassword) {

        User user = userRepository.findByPhone(phone).orElseThrow(()-> new UsernameNotFoundException("User not exist"));
        String passwordEncode = passwordEncoder.encode(oldPassword);
        if(passwordEncoder.matches(oldPassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            return MapData.mapOne(userRepository.save(user), UserRespone.class);
        }
        return null;
    }
}
