package com.tindy.app.service;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.dto.respone.ConversationRespone;

import java.util.List;

public interface ConversationService {

    ConversationRespone createConversationSingle(ConversationRequest conversationRequest);
    ConversationRespone createConversationGroup(ConversationRequest conversationRequest);
    List<ConversationRespone> getConversationsByPhone(String phone);
}
