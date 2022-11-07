package com.tindy.app.controller;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<?> addConversation(@RequestBody ConversationRequest conversationRequest){
        return ResponseEntity.ok().body(conversationService.createConversation(conversationRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getConversations(@PathVariable String userId){
        return ResponseEntity.ok().body(conversationService.getConversationsByUserId(userId));
    }

    @PostMapping(value = "/avatar/{conversationId}", consumes = "multipart/form-data")
    public ResponseEntity<?> modifiedConversation(@PathVariable String conversationId, @RequestParam(value = "file", required = false )MultipartFile file, @RequestParam(required = false) String name) throws IOException {
        return ResponseEntity.ok().body(conversationService.modifiedAvatarImageGroup(Integer.valueOf(conversationId),file, name));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteConversation(@PathVariable Integer id){
        try {
            conversationService.deleteConversation(id);
            return ResponseEntity.ok().body("Delete Success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something is wrong");
        }
    }
}
