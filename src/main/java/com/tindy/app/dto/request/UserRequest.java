package com.tindy.app.dto.request;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String phone;
    private String email;
    private String password;
    private String fullName;
    private Date createdAt;
    private Date updatedAt;
    private String role;
    private String status;

}
