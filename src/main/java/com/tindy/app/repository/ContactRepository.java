package com.tindy.app.repository;

import com.tindy.app.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

    List<Contact> findContactsByUserId(Integer id);
    Contact findContactsByPhoneAndUserId(String phone, Integer userId);
}
