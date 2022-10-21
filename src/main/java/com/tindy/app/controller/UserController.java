package com.tindy.app.controller;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.dto.respone.ContactRespone;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.service.ContactService;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> getContactsByPhone(@RequestParam String phone){

        return ResponseEntity.ok().body(contactService.getContactsByPhone(phone));
    }

    @GetMapping("/{phone}")
    public UserRespone getUserInfo(@PathVariable String phone){
        try {
            log.info(phone);
            return userService.getUserInfo(phone);
        }catch (Exception e){
            new UsernameNotFoundException(e.getMessage());
            return null;
        }
    }
    @PostMapping(value = "/profile/pic", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @RequestParam Integer userId){
        log.info("upload image:  File Name : {}", file.getOriginalFilename());
        return ResponseEntity.ok().body(userService.uploadFile(file, userId));
    }

//    @PostMapping("/profile/pic/{fileName}")
//    public Object download(@PathVariable String fileName) throws IOException {
//        logger.info("HIT -/download | File Name : {}", fileName);
//        return fileService.download(fileName);
//    }
}
