package com.tindy.app.service.impl;

import com.tindy.app.dto.request.ParticipantRequest;
import com.tindy.app.dto.respone.ConversationRespone;
import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.exceptions.ResourceNotFoundException;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Participant;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.ParticipantRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    @Override
    public ParticipantRespone addParticipant(ParticipantRequest participantRequest) {
        Participant participant = MapData.mapOne(participantRequest, Participant.class);
        participant.setConversation(conversationRepository.findById(participantRequest.getConversation().getId()).get());
        participant.setUser(userRepository.findByPhone(participantRequest.getUser().getPhone()).orElseThrow(()-> new UsernameNotFoundException("Not found user!")));

        return MapData.mapOne(participantRepository.save(participant), ParticipantRespone.class);
    }

    @Override
    public List<ParticipantRespone> getParticipant(Integer conversationId) {
        return MapData.mapList(participantRepository.getParticipantByConversationId(conversationId), ParticipantRespone.class);
    }
}
