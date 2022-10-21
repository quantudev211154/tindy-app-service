package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ContactRequest;
import com.tindy.app.dto.respone.ContactRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Contact;
import com.tindy.app.model.entity.User;
import com.tindy.app.repository.ContactRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    @Override
    public ContactRespone addContact(ContactRequest contactRequest) {
        Contact contact = MapData.mapOne(contactRequest,Contact.class);
        contact.setUser(userRepository.findByPhone(contactRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("User not found!")));
        contact.setBlocked(false);
        Contact contactSaved = contactRepository.save(contact);
        ContactRespone contactRespone = MapData.mapOne(contactSaved, ContactRespone.class);
        return contactRespone;
    }

    @Override
    public List<ContactRespone> getContactsByUser(Integer userId) {
        return MapData.mapList(contactRepository.findContactsByUserId(userId),ContactRespone.class);
    }

    @Override
    public ContactRespone updateContact(ContactRequest contactRequest, Integer contactId) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new UsernameNotFoundException("Not found contact"));
        if(contactRequest.getFullName() != null){
            contact.setFullName(contactRequest.getFullName());
        }
        return MapData.mapOne(contactRepository.save(contact), ContactRespone.class);
    }

    @Override
    public void deleteContact(Integer contactId) {
        contactRepository.delete(contactRepository.findById(contactId).orElseThrow(()-> new UsernameNotFoundException("Contact not found")));
    }

    @Override
    public ContactRespone blockContact(Integer contactId) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(()-> new UsernameNotFoundException("Contact not found"));
        contact.setBlocked(true);
        return MapData.mapOne(contactRepository.save(contact),ContactRespone.class);
    }
}
