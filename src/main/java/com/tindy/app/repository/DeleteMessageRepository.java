package com.tindy.app.repository;

import com.tindy.app.model.entity.DeleteMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface DeleteMessageRepository extends JpaRepository<DeleteMessage, Integer> {
}
