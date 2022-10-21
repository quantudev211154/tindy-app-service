package com.tindy.app.dto.respone;

import lombok.*;

import java.util.Date;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRespone {

    private Integer id;
    private String phone;
    private String email;
    private String fullName;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    private String role;
    private int tokenVersion;
    private  String avatar;
}
