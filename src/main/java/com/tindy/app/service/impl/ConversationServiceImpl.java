package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.dto.respone.ConversationResponse;
import com.tindy.app.dto.respone.ParticipantRespone;
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

import java.util.ArrayList;
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
        conversation.setTitle(conversationRequest.getTitle());
        conversation.setCreator(userRepository.findById(conversationRequest.getUser().getId()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        if(conversationRequest.getUsersId().size() <= 2){
            conversation.setType(ConversationType.SINGLE);
        }else {
            conversation.setType(ConversationType.GROUP);
            conversation.setAvatar(conversationRequest.getAvatar());
        }
        ConversationResponse conversationResponse = MapData.mapOne(conversationRepository.save(conversation),ConversationResponse.class);
        List<ParticipantRespone> participantRespones = new ArrayList<>();
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
                if(conversationRequest.getUsersId().size() <= 2){
                    conversationResponse.setAvatar(participant.getUser().getAvatar());
                }
            }
            participant.setCreatedAt(new Date(System.currentTimeMillis()));
            if(conversationRequest.getUsersId().size() > 2){
                participant.setType(ParticipantType.GROUP);
            }else {
                participant.setType(ParticipantType.SINGLE);
            }
            participantRespones.add(MapData.mapOne(participantRepository.save(participant),ParticipantRespone.class));
        }
        conversationResponse.setParticipantResponse(participantRespones);
        return conversationResponse;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(String userId) {
        List<Conversation> conversationsTemp = new ArrayList<>();
        for (Participant participant: participantRepository.getParticipantByUserId(Integer.parseInt(userId)) ){
            conversationsTemp.add(participant.getConversation());
        }
        List<ConversationResponse> conversationResponses = MapData.mapList(conversationsTemp,ConversationResponse.class);
        for(ConversationResponse conversations : conversationResponses){
            List<Participant> participants = participantRepository.getParticipantByConversationId(conversations.getId());
            conversations.setParticipantResponse(MapData.mapList(participants, ParticipantRespone.class));
        }
        return conversationResponses;
    }

}
