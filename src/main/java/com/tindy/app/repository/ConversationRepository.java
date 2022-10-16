package com.tindy.app.repository;

import com.tindy.app.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findConversationByCreatorId(Integer id);
}
