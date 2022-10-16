package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.dto.respone.ConversationRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.enums.ConversationStatus;
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
    public ConversationRespone createConversation(ConversationRequest conversationRequest) {
        Conversation conversation = MapData.mapOne(conversationRequest,Conversation.class);
        conversation.setCreator(userRepository.findByPhone(conversationRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        ConversationRespone conversationRespone = MapData.mapOne(conversationRepository.save(conversation),ConversationRespone.class);
        return conversationRespone;
    }

    @Override
    public List<ConversationRespone> getConversationsByPhone(String phone) {

        return MapData.mapList(conversationRepository.findConversationByCreatorId(userRepository.findByPhone(phone).orElseThrow(()-> new UsernameNotFoundException("Not found")).getId()),ConversationRespone.class);
    }
}
