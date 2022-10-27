package com.tindy.app.controller;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@RequestBody ContactRequest contactRequest, @PathVariable String id){
        return ResponseEntity.ok().body(contactService.updateContact(contactRequest, Integer.parseInt(id)));
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<?> blockContact(@PathVariable String id){
        return ResponseEntity.ok().body(contactService.blockContact(Integer.parseInt(id)));
    }
    @PostMapping("/unblock/{id}")
    public ResponseEntity<?> unBlockContact(@PathVariable String id){
        return ResponseEntity.ok().body(contactService.blockContact(Integer.parseInt(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable String id){
        try {
            contactService.deleteContact(Integer.parseInt(id));
            return ResponseEntity.ok().body("Delete Success!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something is wrong!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContactDetail(@PathVariable String id){
        return ResponseEntity.ok().body(contactService.getContactDetail(Integer.parseInt(id)));
    }
}
