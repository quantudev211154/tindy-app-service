package com.tindy.app.service.impl;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.AttachmentResponse;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.dto.respone.ParticipantRespone;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.*;
import com.tindy.app.model.enums.MessageStatus;
import com.tindy.app.model.enums.MessageType;
import com.tindy.app.repository.*;
import com.tindy.app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final AttachmentRepository attachmentRepository;
    private final UploadService uploadService;
    private final DeleteMessageRepository deleteMessageRepository;
    @Override
    public MessageResponse saveMessage(String conversationId, String senderId, String messageType, String message, List<MultipartFile> files, Integer replyTo) throws IOException {
        Message messageSave = new Message();
        Conversation conversation = conversationRepository.findById(Integer.valueOf(conversationId)).orElse(null);
        User sender = userRepository.findById(Integer.valueOf(senderId)).orElse(null);
        messageSave.setConversation(conversation);
        messageSave.setSender(sender);
        messageSave.setCreatedAt(new Date(System.currentTimeMillis()));
        messageSave.setStatus(MessageStatus.SENT);
        messageSave.setDelete(false);
        messageSave.setMessage(message);
        messageSave.setMessageType(MessageType.valueOf(messageType));
        messageSave.setReplyTo(replyTo);
        Message messageSaved = messageRepository.save(messageSave);
        MessageResponse messageResponse = MapData.mapOne(messageSaved,MessageResponse.class);
        if(replyTo != null){
            messageResponse.setReplyTo(MapData.mapOne(messageRepository.findById(replyTo).orElse(null),MessageResponse.class));
        }
        List<AttachmentResponse> attachmentResponses = new ArrayList<>();
        if(files != null){
            for(MultipartFile multipartFile : files){
                Attachments attachments = new Attachments();
                attachments.setMessage(messageSaved);
                String fileName = multipartFile.getOriginalFilename();
                attachments.setThumbnail(fileName);
                assert fileName != null;
                fileName = UUID.randomUUID().toString().concat(uploadService.getExtension(fileName));
                attachments.setFileName(fileName);
                File file = uploadService.convertToFile(multipartFile,fileName);

                String url = uploadService.uploadFile(file,fileName);
                attachments.setFileUrl(url);
                attachments.setCreatedAt(new Date(System.currentTimeMillis()));
                file.delete();
                Attachments attachmentSaved = attachmentRepository.save(attachments);
                attachmentSaved.setFileUrl(url);
                attachmentSaved.setThumbnail(fileName);
                AttachmentResponse attachmentResponse = MapData.mapOne(attachmentSaved, AttachmentResponse.class);
                attachmentResponses.add(attachmentResponse);
            }
            messageResponse.setAttachmentResponseList(attachmentResponses);

        }
        return messageResponse;
    }

    @Override
    public List<MessageResponse> getMessages(Integer conversationId) {
        List<Message> messages = messageRepository.findMessagesByConversationId(conversationId);
//        List<MessageResponse> messageResponses = MapData.mapList(messages,MessageResponse.class);
        List<MessageResponse> messageResponses = new ArrayList<>();

        for(Message message : messages){
            MessageResponse messageResponse = MapData.mapOne(message,MessageResponse.class);
            if(message.getReplyTo() != null){
                messageResponse.setReplyTo(MapData.mapOne(messageRepository.findById(message.getReplyTo()).orElseThrow(()-> new UsernameNotFoundException("Not found Message")), MessageResponse.class));
            }
            messageResponses.add(messageResponse);
        }
        for(MessageResponse message: messageResponses){
            if(message.getType().equals("FILE")||message.getType().equals("IMAGE")||message.getType().equals("AUDIO")){
                List<AttachmentResponse> attachmentResponseList = MapData.mapList(attachmentRepository.findAttachmentsByMessageId(message.getId()), AttachmentResponse.class);
                message.setAttachmentResponseList(attachmentResponseList);
            }
            List<DeleteMessage> deleteMessages = deleteMessageRepository.findDeleteMessagesByMessageId(message.getId());
            List<ParticipantRespone> participantDelete = new ArrayList<>();
            for(DeleteMessage deleteMessage : deleteMessages){
                participantDelete.add(MapData.mapOne(deleteMessage.getParticipant(), ParticipantRespone.class));
            }
            message.setParticipantDeleted(participantDelete);
        }
        return messageResponses;
    }

    @Override
    public MessageResponse deleteMessage(Integer messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new NullPointerException());
        message.setDelete(true);
        MessageResponse messageResponse = MapData.mapOne(messageRepository.save(message), MessageResponse.class);
        return messageResponse;
    }

    @Override
    public MessageResponse forwardMessage(MessageRequest messageRequest, Integer conversationId) {
        Message message = MapData.mapOne(messageRequest, Message.class);
        List<Attachments> attachments = MapData.mapList(messageRequest.getAttachments(), Attachments.class);
        List<AttachmentResponse> attachmentResponses = new ArrayList<>();
        message.setConversation(conversationRepository.findById(conversationId).orElseThrow(()-> new UsernameNotFoundException("")));
        message.setSender(userRepository.findById(messageRequest.getSender().getId()).orElseThrow(()-> new UsernameNotFoundException("")));
        message.setCreatedAt(new Date(System.currentTimeMillis()));
        message.setStatus(MessageStatus.SENT);
        message.setDelete(false);
        Message messageSaved = messageRepository.save(message);
        if(attachments.size() >= 1){
            for(Attachments attachmentsTemp : attachments){
                attachmentsTemp.setMessage(messageSaved);
                attachmentsTemp.setCreatedAt(new Date(System.currentTimeMillis()));
                AttachmentResponse attachmentResponse = MapData.mapOne(attachmentRepository.save(attachmentsTemp), AttachmentResponse.class);
                attachmentResponses.add(attachmentResponse);
            }
        }
        MessageResponse messageResponse = MapData.mapOne(messageSaved, MessageResponse.class);
        messageResponse.setAttachmentResponseList(attachmentResponses);
        return messageResponse;
    }

    @Override
    public List<MessageResponse> findMessageByKeyword(String keyword, Integer conversationId) {
        return MapData.mapList(messageRepository.findMessagesByMessageContainingAndConversationId(keyword, conversationId), MessageResponse.class);
    }
}
