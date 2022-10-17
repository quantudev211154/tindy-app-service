package com.tindy.app.service;

import com.tindy.app.dto.request.ConversationRequest;

import com.tindy.app.dto.respone.ConversationResponse;

import java.util.List;

public interface ConversationService {

    ConversationResponse createConversationSingle(ConversationRequest conversationRequest);
    ConversationResponse createConversationGroup(ConversationRequest conversationRequest);
    List<ConversationResponse> getConversationsByUserId(String userId);
//    List<ConversationResponse> getConversationByUserId(String userId);
}
