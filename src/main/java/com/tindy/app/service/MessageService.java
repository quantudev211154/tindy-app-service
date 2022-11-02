package com.tindy.app.service;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse saveMessage(MessageRequest messageRequest);
    List<MessageResponse> getMessages(Integer conversationId);

    MessageResponse deleteMessage(Integer messageId);
}
