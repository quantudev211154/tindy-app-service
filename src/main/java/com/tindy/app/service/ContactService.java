package com.tindy.app.service;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.dto.respone.ContactRespone;

import java.util.List;

public interface ContactService {
    ContactRespone addContact(ContactRequest contactRequest);
    List<ContactRespone> getContactsByUser(Integer userId );
    ContactRespone updateContact(ContactRequest contactRequest, Integer contactId);
    void deleteContact(Integer contactId);
    ContactRespone blockContact(Integer contactId);
}
