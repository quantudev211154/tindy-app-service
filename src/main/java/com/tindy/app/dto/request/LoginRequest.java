package com.tindy.app.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String phone;
    private String password;
}
