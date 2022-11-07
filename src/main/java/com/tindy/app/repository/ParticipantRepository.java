package com.tindy.app.repository;

import com.tindy.app.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    List<Participant> getParticipantByConversationId(Integer conversationId);
    List<Participant> getParticipantByUserId(Integer userId);
    Optional<Participant> findParticipantByUserId(Integer userId);
}
