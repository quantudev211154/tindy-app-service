package com.tindy.app.service.impl;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Message;
import com.tindy.app.model.enums.MessageStatus;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.MessageRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    @Override
    public MessageResponse saveMessage(MessageRequest messageRequest) {
        Message message = MapData.mapOne(messageRequest, Message.class);
        message.setCreatedAt(new Date(System.currentTimeMillis()));
        message.setStatus(MessageStatus.SENT);
        message.setConversation(conversationRepository.findById(messageRequest.getConversation().getId()).orElseThrow(()-> new UsernameNotFoundException("Not found")));
        message.setSender(userRepository.findById(messageRequest.getSender().getId()).orElseThrow(()-> new UsernameNotFoundException("Not found")));
        message.setDelete(false);
        Message messageSave = messageRepository.save(message);
        MessageResponse messageResponse = MapData.mapOne(messageSave,MessageResponse.class);

        return messageResponse;
    }

    @Override
    public List<MessageResponse> getMessages(Integer conversationId) {
        List<Message> messages = messageRepository.findMessagesByConversationId(conversationId);

        return MapData.mapList(messages,MessageResponse.class);
    }

    @Override
    public MessageResponse deleteMessage(Integer messageId) {
        MessageResponse messageResponse = MapData.mapOne(messageRepository.findById(messageId).orElse(null), MessageResponse.class);
        messageResponse.setDelete(true);
        return messageResponse;
    }
}
