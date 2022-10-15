package com.tindy.app.dto.request;

import com.tindy.app.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContactRequest {
    private String fullName;
    private String phone;
    private String email;
    private String createdAt;
    private User user;
}
