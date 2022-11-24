package com.tindy.app.service.impl;

import com.tindy.app.dto.request.DeleteMessageRequest;
import com.tindy.app.dto.respone.DeleteMessageResponse;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.DeleteMessage;
import com.tindy.app.repository.DeleteMessageRepository;
import com.tindy.app.repository.MessageRepository;
import com.tindy.app.repository.ParticipantRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.DeleteMessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class DeleteMessageServiceImpl implements DeleteMessageService {

    private final MessageRepository messageRepository;
    private final DeleteMessageRepository deleteMessageRepository;
    private final ParticipantRepository participantRepository;
    @Override
    public DeleteMessageResponse deleteMessage(DeleteMessageRequest deleteMessageRequest) {
        DeleteMessage message = new DeleteMessage();
        message.setMessage(messageRepository.findById(deleteMessageRequest.getMessageId()).orElse(null));
        message.setParticipant(participantRepository.findById(deleteMessageRequest.getParticipantId()).orElse(null));
        message.setCreatedAt(new Date(System.currentTimeMillis()));
        DeleteMessageResponse deleteMessageResponse = MapData.mapOne(deleteMessageRepository.save(message), DeleteMessageResponse.class);
        deleteMessageResponse.setMessageResponse(MapData.mapOne(messageRepository.findById(message.getMessage().getId()).orElse(null), MessageResponse.class));
        deleteMessageResponse.setParticipantResponse(MapData.mapOne(participantRepository.findById(message.getParticipant().getId()).orElse(null), ParticipantRespone.class));
        return deleteMessageResponse;
    }
}
