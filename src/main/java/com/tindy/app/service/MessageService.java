package com.tindy.app.service;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.MessageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    MessageResponse saveMessage(String conversationId, String senderId, String messageType, String message, List<MultipartFile> files) throws IOException;
    List<MessageResponse> getMessages(Integer conversationId);
    MessageResponse deleteMessage(Integer messageId);

    MessageResponse forwardMessage(MessageRequest messageRequest, Integer conversationId);

    List<MessageResponse> findMessageByKeyword(String keyword, Integer conversationId);
}
