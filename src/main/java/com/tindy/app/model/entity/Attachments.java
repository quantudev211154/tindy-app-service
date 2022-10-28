package com.tindy.app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Table(name = "attachments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "messages_id")
    private Message message;
    private String thumbnail;
    private String fileUrl;
    private Date createdAt;
    private Date updatedAt;
    private String fileName;
}
