package com.tindy.app.model.entity;

import com.tindy.app.model.enums.RoleName;
import com.tindy.app.model.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String phone;
    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String tokenVersion;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @OneToMany(mappedBy = "creator")
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "user")
    private List<Participant> participants;
    @OneToMany(mappedBy = "sender")
    private List<Message> messages;
    @OneToMany(mappedBy = "user")
    private List<UserContact> userContacts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getTokenVersion() {
        return tokenVersion;
    }

    public void setTokenVersion(String tokenVersion) {
        this.tokenVersion = tokenVersion;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
