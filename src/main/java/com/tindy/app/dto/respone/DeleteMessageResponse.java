package com.tindy.app.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMessageResponse {

    private Integer id;
    private MessageResponse messageResponse;
    private ParticipantRespone participantResponse;
    private Date createdAt;
}
