package com.tindy.app.dto.request;

import com.tindy.app.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentRequest {

    private Message message;
    private String thumbnail;
//    private File file;
    private Date createdAt;
    private Date updatedAt;
}
