package com.tindy.app.repository;

import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.model.entity.DeleteMessage;
import com.tindy.app.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface DeleteMessageRepository extends JpaRepository<DeleteMessage, Integer> {
    List<DeleteMessage> findDeleteMessagesByMessageId(Integer messageId);
}
