package com.tindy.app.model.entity;

import com.tindy.app.model.enums.ConversationStatus;
import com.tindy.app.model.enums.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    private Date createdAt;
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private ConversationStatus status;
    @Enumerated(EnumType.STRING)
    private ConversationType type;
}
