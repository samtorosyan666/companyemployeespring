package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Message;
import com.example.companyemployeespring.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findAllMessagesByToId(int id) {
        return messageRepository.findAllByToEmployee_Id(id);
    }
}
