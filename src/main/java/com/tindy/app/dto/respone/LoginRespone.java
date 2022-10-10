package com.tindy.app.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRespone {
    private String username;

    private String password;

    private String role;

    private String token;
}
