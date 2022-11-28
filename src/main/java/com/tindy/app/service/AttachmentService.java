package com.tindy.app.service;

import com.tindy.app.dto.request.AttachmentRequest;
import com.tindy.app.dto.respone.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    AttachmentResponse saveAttachment(Integer messageId, MultipartFile file);
    AttachmentResponse downloadAttachment(Integer messageId, String fileName, String location) throws IOException;

    List<AttachmentResponse> getAttachmentsOnConversation(Integer conversationId);
}
