package com.tindy.app.service;

import com.tindy.app.dto.request.AttachmentRequest;
import com.tindy.app.dto.respone.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentResponse saveAttachment(Integer messageId, MultipartFile file);
}
