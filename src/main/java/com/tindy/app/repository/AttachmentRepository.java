package com.tindy.app.repository;

import com.tindy.app.model.entity.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachments,Integer> {
    Optional<Attachments> findAttachmentsByFileName(String fileName);
}
