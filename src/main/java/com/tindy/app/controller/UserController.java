package com.tindy.app.controller;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.dto.respone.ContactRespone;
import com.tindy.app.dto.respone.UserRespone;
import com.tindy.app.service.ContactService;
import com.tindy.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final ContactService contactService;
    private final UserService userService;
    @PostMapping("/contact")
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
}
