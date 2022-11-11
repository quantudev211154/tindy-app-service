package com.tindy.app.controller;

import com.tindy.app.dto.request.ParticipantRequest;
import com.tindy.app.dto.respone.ParticipantRespone;
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

    @PostMapping("/group")
    public ResponseEntity<?> removeParticipant(@RequestParam Integer adminId, @RequestParam Integer participantId){
        try {
            if (participantService.removeParticipant(adminId,participantId)){
                return ResponseEntity.ok().body("Participant is removed");
            }else {
                return ResponseEntity.badRequest().body("Something is wrong");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something is wrong");
        }
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> outGroupConversation(@PathVariable Integer id){
        try {
            if(participantService.outGroupConversation(id)){
                return ResponseEntity.ok().body("You outed of this group");
            }
            return ResponseEntity.badRequest().body("Some thing is wrong");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Some thing is wrong");
        }
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grantPermission(@RequestParam Integer adminId, @RequestParam Integer participantId, @RequestParam String role){
        ParticipantRespone participantRespone = participantService.grantPermission(adminId,participantId,role);
        if(participantRespone != null){
            return ResponseEntity.ok().body(participantRespone);
        }else {
            return ResponseEntity.badRequest().body("Something is wrong");
        }
    }
}
