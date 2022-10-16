package com.tindy.app.repository;

import com.tindy.app.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> getParticipantByConversationId(Integer conversationId);
}
