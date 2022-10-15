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
        Contact contactSaved = contactRepository.save(contact);
        ContactRespone contactRespone = MapData.mapOne(contactSaved, ContactRespone.class);
        return contactRespone;
    }

    @Override
    public List<ContactRespone> getContactsByPhone(String phone) {
//        for(Contact contact : contactRepository.findContactsByUserPhone(phone)){
//            log.info("HieuLog: "+contact.toString());
//        }
        log.info("Hieu log"+ phone);
        User user = userRepository.findByPhone(phone).orElseThrow(()-> new UsernameNotFoundException("Not found user"));
        log.info("Hieu Log: "+user.getId());
        return MapData.mapList(contactRepository.findContactsByUserId(user.getId()),ContactRespone.class);
    }
}
