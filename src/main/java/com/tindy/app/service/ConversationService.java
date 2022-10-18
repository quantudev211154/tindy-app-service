package com.tindy.app.service;

import com.tindy.app.dto.request.ConversationRequest;

import com.tindy.app.dto.respone.ConversationResponse;

import java.util.List;

public interface ConversationService {

    ConversationResponse createConversation(ConversationRequest conversationRequest);

    List<ConversationResponse> getConversationsByUserId(String userId);
}
