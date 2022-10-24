package com.tindy.app.dto.respone;

import com.tindy.app.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private Integer id;
    private Message message;
    private String thumbnail;
    private String fileUrl;
    private Date createdAt;
    private Date updatedAt;
}
