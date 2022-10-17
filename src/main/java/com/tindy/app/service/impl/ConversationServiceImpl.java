package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ConversationRequest;

import com.tindy.app.dto.respone.ConversationResponse;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.enums.ConversationStatus;
import com.tindy.app.model.enums.ConversationType;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    @Override
    public ConversationResponse createConversationSingle(ConversationRequest conversationRequest) {
        Conversation conversation = MapData.mapOne(conversationRequest,Conversation.class);
        conversation.setCreator(userRepository.findByPhone(conversationRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        conversation.setType(ConversationType.SINGLE);
        ConversationResponse conversationResponse = MapData.mapOne(conversationRepository.save(conversation),ConversationResponse.class);
        return conversationResponse;
    }

    @Override
    public ConversationResponse createConversationGroup(ConversationRequest conversationRequest) {
        Conversation conversation = MapData.mapOne(conversationRequest,Conversation.class);
        conversation.setCreator(userRepository.findByPhone(conversationRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        conversation.setType(ConversationType.GROUP);
        ConversationResponse conversationRespone = MapData.mapOne(conversationRepository.save(conversation),ConversationResponse.class);
        return conversationRespone;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(String userId) {

        return MapData.mapList(conversationRepository.findConversationByCreatorId(Integer.parseInt(userId)),ConversationResponse.class);
    }

//    @Override
//    public List<ConversationResponse> getConversationByU(String userId) {
//        return null;
//    }
}
