package com.tindy.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMessageRequest {
    private Integer messageId;
    private Integer participantId;
    private Date createdAt;
}
