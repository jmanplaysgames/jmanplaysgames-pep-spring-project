package com.example.service;


import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        if(message.getMessageText().length() <= 255 && !message.getMessageText().equals("")){
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }

    public Message getMessageById(Integer messageId){
        Message obtainedMessage = messageRepository.findById(messageId).get();
        return obtainedMessage;
    }
    public void deleteMessageById(Integer messageId){
       messageRepository.deleteById(messageId);
    }
    public void updateMessageById(Integer messageId, Message message){
        messageRepository.getById(messageId).setMessageText(message.getMessageText());
    }
}
