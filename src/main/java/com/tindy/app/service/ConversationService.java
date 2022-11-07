package com.tindy.app.service;

import com.tindy.app.dto.request.ConversationRequest;

import com.tindy.app.dto.respone.ConversationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ConversationService {

    ConversationResponse createConversation(ConversationRequest conversationRequest);

    List<ConversationResponse> getConversationsByUserId(String userId);

    ConversationResponse modifiedAvatarImageGroup(Integer conversationId, MultipartFile file, String name) throws IOException;

    Boolean deleteConversation(Integer conversationId);
}
