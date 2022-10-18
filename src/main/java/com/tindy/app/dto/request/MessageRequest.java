package com.tindy.app.dto.request;

import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageRequest {
    private Conversation conversation;
    private User sender;
    private String messageType;
    private String message;
    private Date createdAt;
    private boolean isDelete;
    private String status;
}
