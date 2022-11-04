package com.tindy.app.service.impl;

import com.tindy.app.dto.request.MessageRequest;
import com.tindy.app.dto.respone.AttachmentResponse;
import com.tindy.app.dto.respone.MessageResponse;
import com.tindy.app.mapper.MapData;
import com.tindy.app.model.entity.Attachments;
import com.tindy.app.model.entity.Conversation;
import com.tindy.app.model.entity.Message;
import com.tindy.app.model.entity.User;
import com.tindy.app.model.enums.MessageStatus;
import com.tindy.app.model.enums.MessageType;
import com.tindy.app.repository.AttachmentRepository;
import com.tindy.app.repository.ConversationRepository;
import com.tindy.app.repository.MessageRepository;
import com.tindy.app.repository.UserRepository;
import com.tindy.app.service.MessageService;
import lombok.RequiredArgsConstructor;
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
    @Override
    public MessageResponse saveMessage(String conversationId, String senderId, String messageType, String message, List<MultipartFile> files) throws IOException {
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
        Message messageSaved = messageRepository.save(messageSave);
        MessageResponse messageResponse = MapData.mapOne(messageSaved,MessageResponse.class);

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
        List<MessageResponse> messageResponses = MapData.mapList(messages,MessageResponse.class);
        for(MessageResponse message: messageResponses){
            if(message.getType().equals("FILE")||message.getType().equals("IMAGE")||message.getType().equals("AUDIO")){
                List<AttachmentResponse> attachmentResponseList = MapData.mapList(attachmentRepository.findAttachmentsByMessageId(message.getId()), AttachmentResponse.class);
                message.setAttachmentResponseList(attachmentResponseList);
            }
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
}
