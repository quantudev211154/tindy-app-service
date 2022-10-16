package com.tindy.app.dto.request;

import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.ParticipantSatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequest {

    private Conversation conversation;

    private User user;

    private String status;
    private Date createdAt;
    private Date updateAt;
    private String nickName;
}
