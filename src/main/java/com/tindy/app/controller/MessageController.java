package com.tindy.app.controller;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> saveMessage(@RequestBody MessageRequest messageRequest){
        return ResponseEntity.ok().body(messageService.saveMessage(messageRequest));
    };
    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable String conversationId){
        List<MessageResponse> messageResponses = messageService.getMessages(Integer.parseInt(conversationId));
        return ResponseEntity.ok().body(messageResponses);
    }


}
