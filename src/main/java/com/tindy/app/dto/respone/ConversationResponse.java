package com.tindy.app.dto.respone;

import com.tindy.app.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConversationResponse {
    private Integer id;
    private String title;
    private UserRespone creator;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    private String type;
    private String avatar;
    private List<ParticipantRespone> participantResponse;
    private String messageLatest;
}
