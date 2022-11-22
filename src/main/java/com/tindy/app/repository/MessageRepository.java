package com.tindy.app.repository;

import com.tindy.app.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findMessagesByConversationId(Integer id);
    Optional<Message> findTopByConversationIdOrderByCreatedAtDesc(Integer id);

    List<Message> findMessagesByMessageContainingAndConversationId(String message, Integer conversationId);
}
