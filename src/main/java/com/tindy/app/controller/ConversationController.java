package com.tindy.app.controller;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin
public class ConversationController {

    private final ConversationService conversationService;
    @PostMapping("/single")
    public ResponseEntity<?> addConversationSingle(@RequestBody ConversationRequest conversationRequest){
        return ResponseEntity.ok().body(conversationService.createConversationSingle(conversationRequest));
    }
    @GetMapping("/{phone}")
    public ResponseEntity<?> getConversations(@PathVariable String phone){
        return ResponseEntity.ok().body(conversationService.getConversationsByPhone(phone));
    }
}
