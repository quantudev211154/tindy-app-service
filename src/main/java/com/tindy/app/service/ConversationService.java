package com.tindy.app.service;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.dto.respone.ConversationRespone;

import java.util.List;

public interface ConversationService {

    ConversationRespone createConversation(ConversationRequest conversationRequest);
    List<ConversationRespone> getConversationsByPhone(String phone);
}
