package com.tindy.app.model.entity;

import com.tindy.app.model.enums.MessageStatus;
import com.tindy.app.model.enums.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    private String message;
    private Date createdAt;
    private boolean isDelete;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    private Integer replyTo;
}
