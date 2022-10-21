package com.tindy.app.model.entity;

import com.tindy.app.model.enums.ConversationType;
import com.tindy.app.model.enums.ParticipantRole;
import com.tindy.app.model.enums.ParticipantSatus;
import com.tindy.app.model.enums.ParticipantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    private Date createdAt;
    private Date updatedAt;
    private String nickName;
    @Enumerated(EnumType.STRING)
    private ParticipantType type;
    @Enumerated(EnumType.STRING)
    private ParticipantRole role;
}
