package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ConversationRequest;

import com.tindy.app.dto.respone.ConversationResponse;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.Participant;
import com.tindy.app.model.enums.ConversationStatus;
import com.tindy.app.model.enums.ConversationType;
import com.tindy.app.model.enums.ParticipantRole;
import com.tindy.app.model.enums.ParticipantType;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.ParticipantRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final ConversationRepository conversationRepository;
    @Override
    public ConversationResponse createConversation(ConversationRequest conversationRequest) {
        Conversation conversation = new Conversation();
        conversation.setCreator(userRepository.findById(conversationRequest.getUser().getId()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        conversation.setType(ConversationType.SINGLE);
        ConversationResponse conversationResponse = MapData.mapOne(conversationRepository.save(conversation),ConversationResponse.class);
        for(Integer id : conversationRequest.getUsersId()){
            System.out.println(id);
            Participant participant = new Participant();
            participant.setConversation(MapData.mapOne(conversationResponse,Conversation.class));
            participant.setUser(userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Not found user")));
            System.out.println(conversationRequest.getUser().getId() == id);
            if(conversationRequest.getUser().getId()  == id){
                participant.setRole(ParticipantRole.ADMIN);
            }else {
                participant.setRole(ParticipantRole.MEM);
            }
            participant.setCreatedAt(new Date(System.currentTimeMillis()));
            if(conversationRequest.getUsersId().size() > 2){
                participant.setType(ParticipantType.GROUP);
            }else {
                participant.setType(ParticipantType.SINGLE);
            }
            participantRepository.save(participant);
        }

        return conversationResponse;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(String userId) {

        return MapData.mapList(conversationRepository.findConversationByCreatorId(Integer.parseInt(userId)),ConversationResponse.class);
    }

}
