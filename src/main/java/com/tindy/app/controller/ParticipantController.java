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

    @PostMapping(value = "/single",consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addParticipant(@RequestBody ParticipantRequest participantRequest){
        return ResponseEntity.ok().body(participantService.addParticipantSingle(participantRequest));
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getParticipants(@PathVariable String conversationId){
        return ResponseEntity.ok().body(participantService.getParticipant(Integer.parseInt(conversationId)));
    }
}
