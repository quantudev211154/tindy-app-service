package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ConversationRequest;
import com.tindy.app.dto.respone.ConversationResponse;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.Message;
import com.tindy.app.model.entity.Participant;
import com.tindy.app.model.enums.*;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.MessageRepository;
import com.tindy.app.repository.ParticipantRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UploadService uploadService;
    @Override
    public ConversationResponse createConversation(ConversationRequest conversationRequest) {
        Conversation conversation = new Conversation();
        conversation.setTitle(conversationRequest.getTitle());
        conversation.setCreator(userRepository.findById(conversationRequest.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found!")));
        conversation.setCreatedAt(new Date(System.currentTimeMillis()));
        conversation.setStatus(ConversationStatus.ACTIVE);
        if (conversationRequest.getPhones().size() <= 2) {
            conversation.setType(ConversationType.SINGLE);
        } else {
            conversation.setType(ConversationType.GROUP);
            conversation.setAvatar(conversationRequest.getAvatar());
        }
        ConversationResponse conversationResponse = MapData.mapOne(conversationRepository.save(conversation), ConversationResponse.class);
        List<ParticipantRespone> participantRespones = new ArrayList<>();
        for (String phone : conversationRequest.getPhones()) {
            Participant participant = new Participant();
            participant.setConversation(MapData.mapOne(conversationResponse, Conversation.class));
            participant.setUser(userRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("Not found user")));
            if (conversationRequest.getUser().getId() == participant.getUser().getId()) {
                participant.setRole(ParticipantRole.ADMIN);
            } else {
                participant.setRole(ParticipantRole.MEM);
                if (conversationRequest.getPhones().size() <= 2) {
                    conversationResponse.setAvatar(participant.getUser().getAvatar());
                }
            }
            participant.setCreatedAt(new Date(System.currentTimeMillis()));
            participant.setStatus(ParticipantSatus.STABLE);
            if (conversationRequest.getPhones().size() > 2) {
                participant.setType(ParticipantType.GROUP);
            } else {
                participant.setType(ParticipantType.SINGLE);
            }
            participantRespones.add(MapData.mapOne(participantRepository.save(participant), ParticipantRespone.class));
        }
        conversationResponse.setParticipantResponse(participantRespones);
        return conversationResponse;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(String userId) {
        List<Conversation> conversationsTemp = new ArrayList<>();
        for (Participant participant : participantRepository.getParticipantByUserId(Integer.parseInt(userId))) {
            conversationsTemp.add(participant.getConversation());
        }
        List<ConversationResponse> conversationResponses = MapData.mapList(conversationsTemp, ConversationResponse.class);
        for (ConversationResponse conversations : conversationResponses) {
            Message message = messageRepository.findTopByConversationIdOrderByCreatedAtDesc(conversations.getId()).orElse(null);
            if(message == null){
                conversations.setMessageLatest(null);
            }else {
                conversations.setMessageLatest(MapData.mapOne(message, MessageResponse.class));

            }
            List<Participant> participants = participantRepository.getParticipantByConversationId(conversations.getId());
            conversations.setParticipantResponse(MapData.mapList(participants, ParticipantRespone.class));
        }

        return conversationResponses;
    }

    @Override
    public ConversationResponse modifiedAvatarImageGroup(Integer conversationId, MultipartFile multipartFile, String name) throws IOException {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new NullPointerException());
        if(conversation.getType().equals(ConversationType.GROUP)){
            if(multipartFile != null){
                String fileName = multipartFile.getOriginalFilename();
                assert fileName != null;
                fileName = UUID.randomUUID().toString().concat(uploadService.getExtension(fileName));
                File file = uploadService.convertToFile(multipartFile,fileName);
                String url = uploadService.uploadFile(file,fileName);
                file.delete();

                conversation.setAvatar(url);
            }
            if(!name.equals("")){
                conversation.setTitle(name);
            }
        }
        return MapData.mapOne(conversationRepository.save(conversation), ConversationResponse.class);
    }

    @Override
    public Boolean deleteConversation(Integer conversationId) {
        try {
            conversationRepository.delete(conversationRepository.findById(conversationId).orElse(null));
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
