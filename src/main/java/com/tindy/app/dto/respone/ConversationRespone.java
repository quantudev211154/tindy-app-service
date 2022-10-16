package com.tindy.app.dto.respone;

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
public class ConversationRespone {
    private String title;
    private User creator;
    private Date createdAt;
    private Date updatedAt;
    private String status;

}
