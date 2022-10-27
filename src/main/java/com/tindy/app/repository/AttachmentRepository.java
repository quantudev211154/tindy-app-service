package com.tindy.app.repository;

import com.tindy.app.model.entity.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachments,Integer> {
}
