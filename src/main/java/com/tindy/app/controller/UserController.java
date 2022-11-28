package com.tindy.app.controller;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.dto.request.UserRequest;
import com.tindy.app.dto.respone.ContactRespone;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.service.ContactService;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class UserController {
    private final ContactService contactService;
    private final UserService userService;
    @PostMapping(value = "/contacts", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addContact(@RequestBody ContactRequest contactRequest){
        return ResponseEntity.ok().body(contactService.addContact(contactRequest));
    }

    @GetMapping("/contacts")
    public ResponseEntity<?> getContactsByPhone(@RequestParam String userId){

        return ResponseEntity.ok().body(contactService.getContactsByUser(Integer.parseInt(userId)));
    }

    @PutMapping( value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> modifyUser(@RequestParam(required = false) String fullName, @RequestParam(value = "file", required = false) MultipartFile file , @PathVariable String id) throws IOException {
        return ResponseEntity.ok().body(userService.updateUser(fullName, Integer.parseInt(id),file));
    }
    @GetMapping("/{id}")
    public UserRespone getUserInfo(@PathVariable String id){
        try {
            return userService.getUserInfo(Integer.parseInt(id));
        }catch (Exception e){
            new UsernameNotFoundException(e.getMessage());
            return null;
        }
    }
    @GetMapping()
    public UserRespone getUserInfoByPhone(@RequestParam String phone){
        return userService.getUserInfoByPhone(phone);
    }
    @PostMapping(value = "/profile/pic", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @RequestParam Integer userId){
        log.info("upload image:  File Name : {}", file.getOriginalFilename());
        return ResponseEntity.ok().body(userService.uploadFile(file, userId));
    }

    @PostMapping("/change/password/{phone}")
    public ResponseEntity<?> changePassword(@PathVariable String phone, @RequestParam String oldPassword, @RequestParam String newPassword){
        try {
            UserRespone userRespone = MapData.mapOne(userService.changePassword(phone,oldPassword,newPassword), UserRespone.class);
            if(userRespone != null){
                return ResponseEntity.ok().body(userRespone);
            }else {
                return ResponseEntity.badRequest().body("Something is wrong");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something is wrong");

        }
    }
    @GetMapping(value = "/password/{phone}")
    public ResponseEntity<?> checkPasswordModify(@PathVariable String phone, @RequestParam String password){
        if(userService.checkPasswordModify(password,phone)){
            return ResponseEntity.ok().body("Success");
        }else {
            return ResponseEntity.badRequest().body("Password is wrong");
        }
    }
//    @PostMapping("/profile/pic/{fileName}")
//    public Object download(@PathVariable String fileName) throws IOException {
//        logger.info("HIT -/download | File Name : {}", fileName);
//        return fileService.download(fileName);
//    }
}
