package com.tindy.app.dto.respone;

import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.ParticipantSatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ParticipantRespone {

    private Integer id;
    private Conversation conversation;
    private UserRespone user;
    private Date createdAt;
    private Date updateAt;
    private String nickName;
    private String role;
}
