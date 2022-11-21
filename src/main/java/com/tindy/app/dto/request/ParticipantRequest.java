package com.tindy.app.dto.request;

import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.ParticipantSatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequest {

    private Integer conversationId;

    private List<String> phones;

    private String status;
    private Date createdAt;
    private Date updateAt;
    private String nickName;
}
