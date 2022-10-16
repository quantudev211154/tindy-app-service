package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ParticipantRequest;
import com.tindy.app.dto.respone.ConversationRespone;
import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.exceptions.ResourceNotFoundException;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.Participant;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.ConversationType;
import com.tindy.app.model.enums.ParticipantRole;
import com.tindy.app.model.enums.ParticipantSatus;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.ParticipantRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
//    @Override
//    public ParticipantRespone addParticipantSingle(ParticipantRequest participantRequest) {
//        Participant participant = MapData.mapOne(participantRequest, Participant.class);
//        Conversation conversation = conversationRepository.findById(participantRequest.getConversation().getId()).get();
//        User user = userRepository.findByPhone(participantRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("Not found user!"));
//        conversation.setTitle(user.getFullName());
//        participant.setConversation(conversation);
//        participant.setUser(user);
//        participant.setType(ConversationType.SINGLE);
//        participant.setCreatedAt(new Date(System.currentTimeMillis()));
//        if(conversation.getCreator().getId().equals(user.getId())){
//            participant.setRole(ParticipantRole.ADMIN);
//        }else{
//            participant.setRole(ParticipantRole.MEM);
//        }
//        return MapData.mapOne(participantRepository.save(participant), ParticipantRespone.class);
//    }

    @Override
    public Object addParticipantGroup(ParticipantRequest participantRequest) {
        Participant participant = MapData.mapOne(participantRequest, Participant.class);
        Conversation conversation = conversationRepository.findById(participantRequest.getConversation().getId()).get();

        if(participantRequest.getUsers().size() > 2){
            for(User user: participantRequest.getUsers()){
                participant.setUser(user);
                if(conversation.getCreator().getId() == user.getId()){
                    participant.setRole(ParticipantRole.ADMIN);
                }else{
                    participant.setRole(ParticipantRole.MEM);
                }
                participant.setCreatedAt(new Date(System.currentTimeMillis()));
                participant.setType(ConversationType.GROUP);
                participantRepository.save(participant);
            }

        }

        return null;
    }

    @Override
    public List<ParticipantRespone> getParticipant(Integer conversationId) {
        return MapData.mapList(participantRepository.getParticipantByConversationId(conversationId), ParticipantRespone.class);
    }
}
