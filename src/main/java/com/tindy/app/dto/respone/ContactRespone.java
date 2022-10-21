package com.tindy.app.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContactRespone {
    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private boolean isBlocked;
    private String createdAt;
}
