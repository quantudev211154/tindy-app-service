package com.tindy.app.service;

import com.tindy.app.dto.request.AttachmentRequest;
import com.tindy.app.dto.respone.AttachmentResponse;

public interface AttachmentService {
    AttachmentResponse saveAttachment(AttachmentRequest attachmentRequest);
}
