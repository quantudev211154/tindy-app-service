package com.tindy.app.controller;

import com.tindy.app.dto.request.ParticipantRequest;
import com.tindy.app.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants/")
@CrossOrigin
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getParticipants(@PathVariable String conversationId){
        return ResponseEntity.ok().body(participantService.getParticipant(Integer.parseInt(conversationId)));
    }

    @PostMapping("/group")
    public ResponseEntity<?> addParticipantToGroup(@RequestBody ParticipantRequest participantRequest){
        return ResponseEntity.ok().body(participantService.addParticipantGroup(participantRequest));
    }

}
