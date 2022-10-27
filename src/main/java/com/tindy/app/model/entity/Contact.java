package com.tindy.app.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "contacts")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String phone;
    private String email;
    private Date createdAt;
    private boolean isBlocked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String avatar;
}
