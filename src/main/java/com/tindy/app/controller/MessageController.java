package com.tindy.app.controller;

import com.tindy.app.dto.request.AttachmentRequest;
import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.model.entity.Attachments;
import com.tindy.app.service.AttachmentService;
import com.tindy.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;
    private final AttachmentService attachmentService;
    @PostMapping
    public ResponseEntity<MessageResponse> saveMessage(@RequestBody MessageRequest messageRequest){
        return ResponseEntity.ok().body(messageService.saveMessage(messageRequest));
    };
    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable String conversationId){
        List<MessageResponse> messageResponses = messageService.getMessages(Integer.parseInt(conversationId));
        return ResponseEntity.ok().body(messageResponses);
    }

    @PostMapping("/attachments")
    public ResponseEntity<?> saveAttachment(@RequestParam("file")MultipartFile file, @RequestParam Integer messageId ){
        return ResponseEntity.ok().body(attachmentService.saveAttachment(messageId,file));
    }


    @PostMapping("/attachments/{messageId}")
    public ResponseEntity<?> downloadAttachment(@PathVariable String messageId, @RequestParam String fileName) throws IOException {
        return ResponseEntity.ok().body(attachmentService.downloadAttachment(Integer.parseInt(messageId),fileName));
    }

    @PostMapping("/recall/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer id){
        return ResponseEntity.ok().body(messageService.deleteMessage(id));
    }
}
