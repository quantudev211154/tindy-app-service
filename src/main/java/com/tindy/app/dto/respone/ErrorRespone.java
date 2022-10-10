package com.tindy.app.dto.respone;

import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
public class ErrorRespone {
    private Date timeStamp;

    private String message;

    private String error;
}
