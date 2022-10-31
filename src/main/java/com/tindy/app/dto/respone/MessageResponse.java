package com.tindy.app.dto.respone;

import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageResponse {
    private Integer id;
    private Conversation conversation;
    private UserRespone sender;
    private String type;
    private String message;
    private Date createdAt;
    private boolean isDelete;
    private String status;
}
