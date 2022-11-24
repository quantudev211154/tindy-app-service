package com.tindy.app.service;


import com.tindy.app.dto.request.DeleteMessageRequest;
import com.tindy.app.dto.respone.DeleteMessageResponse;
import com.tindy.app.model.entity.DeleteMessage;

public interface DeleteMessageService {
    DeleteMessageResponse deleteMessage(DeleteMessageRequest deleteMessageRequest);
}
